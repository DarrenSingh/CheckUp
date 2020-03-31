package com.Group6.checkup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.Group6.checkup.Entities.DistanceMatrixRequest;
import com.Group6.checkup.Entities.DistanceMatrixResponse;
import com.Group6.checkup.Entities.Doctor;
import com.Group6.checkup.Utils.Dao.DoctorDao;
import com.Group6.checkup.Utils.GsonDistanceMatrixRequest;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapquest.android.commoncore.log.L;
import com.mapquest.android.commoncore.model.LatLng;
import com.mapquest.android.searchahead.IllegalQueryParameterException;
import com.mapquest.android.searchahead.SearchAheadService;
import com.mapquest.android.searchahead.model.SearchAheadQuery;
import com.mapquest.android.searchahead.model.SearchCollection;
import com.mapquest.android.searchahead.model.response.AddressProperties;
import com.mapquest.android.searchahead.model.response.SearchAheadResponse;
import com.mapquest.android.searchahead.model.response.SearchAheadResult;
import com.mapquest.mapping.MapQuest;
import com.mapquest.mapping.maps.MapView;
import com.mapquest.mapping.styles.MapStyles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.Group6.checkup.BuildConfig.API_KEY;

public class MapQuestActivity extends Activity {
    private EditText mEditSearch;
    private ImageView mImageSearch;
    private ListView mListViewSearch;
    private ListView mListViewDoctors;
    private MapView mMapView;
    private MapboxMap mMapboxMap;
    private SearchAheadService mSearchAheadServiceV3;
    private List<HashMap<String, String>> searchResultsData = new ArrayList<>();;
    private List<HashMap<String,String>> nearbyDoctorsData = new ArrayList<>();
    private SimpleAdapter searchAdapter;
    private SimpleAdapter doctorsAdapter;

    private boolean locationFound = false;
    private String selectedLat;
    private String selectedLong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapQuest.start(getApplicationContext());
        setContentView(R.layout.activity_map_quest);

        //UI Components
        mMapView = findViewById(R.id.mapquestMapView);
        mListViewSearch = findViewById(R.id.list_search_location);
        mListViewDoctors = findViewById(R.id.list_nearby_doctors);
        mEditSearch = findViewById(R.id.edit_search_location);

        mEditSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if( count == 0 || s.toString().trim().length() == 0){
                    locationFound = false;
                    resetSearchAheadList();
                    resetDoctorsList();
                }

                if(!locationFound && s.length() > 2){
                    searchQuery(mEditSearch.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                mMapboxMap = mapboxMap;
                mMapView.setStreetMode();
                mMapboxMap.addOnMapClickListener(new MapboxMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull com.mapbox.mapboxsdk.geometry.LatLng point) {
                        if(locationFound && mListViewDoctors.getVisibility() == View.GONE){
                            mListViewSearch.setVisibility(View.VISIBLE);
                            mListViewDoctors.setVisibility(View.VISIBLE);
                        } else if (locationFound) {
                            mListViewSearch.setVisibility(View.GONE);
                            mListViewDoctors.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        mEditSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListViewDoctors.setVisibility(View.GONE);
            }
        });

        mListViewSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView displayAddress = view.findViewById(R.id.text_result_address);
                TextView latitude = view.findViewById(R.id.hidden_location_lat);
                TextView longitude = view.findViewById(R.id.hidden_location_long);

                //hide keyboard
                hideKeyboard(mEditSearch);

                selectedLat = latitude.getText().toString();
                selectedLong = longitude.getText().toString();
                locationFound = true;

                mEditSearch.setText(displayAddress.getText().toString());
                mEditSearch.setSelection(displayAddress.getText().length());

                resetSearchAheadList();
                mMapboxMap.clear();
                mMapboxMap.setCameraPosition(new CameraPosition.Builder()
                        .target(new com.mapbox.mapboxsdk.geometry.LatLng(Double.parseDouble(selectedLat)-0.08,Double.parseDouble(selectedLong))) // Sets the new camera position
                        .zoom(10)
                        .tilt(20)
                        .build());
                addMarker(mMapboxMap);
                //make api call
                 distanceMatrixNetworkCall();
            }
        });

    }

    @Override
    public void onResume()
    { super.onResume(); mMapView.onResume(); }

    @Override
    public void onPause()
    { super.onPause(); mMapView.onPause(); }

    @Override
    protected void onDestroy()
    { super.onDestroy(); mMapView.onDestroy(); }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    { super.onSaveInstanceState(outState); mMapView.onSaveInstanceState(outState); }

    private void searchQuery(String query){
        mSearchAheadServiceV3 = new SearchAheadService(this, API_KEY);

        String queryString = query;

        LatLng surreyBC = new LatLng(49.104599F,-122.823509F);

        List searchCollections = Arrays.asList(SearchCollection.AIRPORT, SearchCollection.ADMINAREA,
                SearchCollection.ADDRESS,SearchCollection.FRANCHISE,SearchCollection.POI);
        try {
            SearchAheadQuery searchAheadQuery = new SearchAheadQuery
                    .Builder(queryString, searchCollections)
                    .location(surreyBC)
                    .limit(5)
                    .build();

            mSearchAheadServiceV3.predictResultsFromQuery(searchAheadQuery,
                    new SearchAheadService.SearchAheadResponseCallback() {

                        @Override
                        public void onSuccess(@NonNull SearchAheadResponse searchAheadResponse) {
                            Log.i("MAPQUEST", "Search Ahead V3 Success - Response: " + searchAheadResponse);

                            List<SearchAheadResult> searchAheadResults = searchAheadResponse.getResults();

                            if(searchAheadResults.size() > 0) {

                                searchResultsData.clear();

                                int size = (searchAheadResults.size() < 5) ? searchAheadResults.size() : 5;

                                for (int i = size-1;i >= 0 ; i--) {
                                    // create a hashmap
                                    HashMap<String, String> hashMap = new HashMap<>();

                                    AddressProperties addressProperties = searchAheadResults.get(i).getPlace().getAddressProperties();

                                    // convert image int to a string and place it into the hashmap with an images key
                                    hashMap.put("address", searchAheadResults.get(i).getName());
                                    hashMap.put("city", addressProperties.getCity() + ", " + addressProperties.getStateCode());
                                    hashMap.put("lat", String.valueOf(searchAheadResults.get(i).getPlace().getLatLng().getLatitude()));
                                    hashMap.put("long", String.valueOf(searchAheadResults.get(i).getPlace().getLatLng().getLongitude()));


                                    // add this hashmap to the list
                                    searchResultsData.add(hashMap);
                                }

                                //adapter inputs
                                String[] from = {
                                        "address",
                                        "city",
                                        "lat",
                                        "long"
                                };

                                int[] to = {R.id.text_result_address, R.id.text_result_city,R.id.hidden_location_lat,R.id.hidden_location_long};

                                searchAdapter = new SimpleAdapter(getApplicationContext(), searchResultsData, R.layout.item_search_result, from, to);
                                mListViewSearch.setAdapter(searchAdapter);
                                mListViewSearch.setVisibility(View.VISIBLE);
                            } else {
                                resetSearchAheadList();
                            }
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.e("MAPQUEST", "Search Ahead V3 Failure", e);
                        }
                    });
        } catch (IllegalQueryParameterException e) {
            L.e("Error performing search", e);
        }
    }

    private void resetSearchAheadList(){
        if(!searchResultsData.isEmpty()) {
            searchResultsData.clear();
            searchAdapter.notifyDataSetChanged();
        }
    }

    private void resetDoctorsList(){
        if(!nearbyDoctorsData.isEmpty()){
            nearbyDoctorsData.clear();
            doctorsAdapter.notifyDataSetChanged();
            mListViewDoctors.setVisibility(View.GONE);
        }
    }

    private void distanceMatrixNetworkCall(){
        //Get all addresses
        List<String> addresses = new ArrayList<>();

        //Get the address to find distances from first
        addresses.add(selectedLat+","+selectedLong);

        //Get doctors
        List<Doctor> doctors = new DoctorDao(getApplicationContext()).findAll();

        //add all doctors office addresses from database
        for (int i = 0; i < doctors.size(); i++) {
            addresses.add(doctors.get(i).getOfficeAddress());
        }

        //add the options for the request
        Map<String,Boolean> options = new HashMap<>();
        options.put("allToAll",false);

        //create an object
        DistanceMatrixRequest requestObj = new DistanceMatrixRequest(addresses,options);

        Gson gson = new Gson();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        GsonDistanceMatrixRequest gsonRequest = new GsonDistanceMatrixRequest(requestObj
                , new Response.Listener<DistanceMatrixResponse>() {
            @Override
            public void onResponse(DistanceMatrixResponse response) {

                //add doctors name address and distance to hashmap list for the nearby doctors list adapter
                for (int i = 0; i < doctors.size(); i++) {
                    // create a hashmap
                    HashMap<String, String> hashMap = new HashMap<>();

                    // convert image int to a string and place it into the hashmap with an images key
                    hashMap.put("id",String.valueOf(doctors.get(i).getID()));
                    hashMap.put("name","Dr. "+doctors.get(i).getFirstName()+" "+doctors.get(i).getLastName());
                    hashMap.put("address",doctors.get(i).getOfficeAddress());
                    hashMap.put("distance",String.valueOf(response.getDistance().get(i+1)));

                    DistanceMatrixResponse.Location location = response.getLocations().get(i+1);
                    hashMap.put("lat",String.valueOf(location.getLatLng().getLat()));
                    hashMap.put("lng",String.valueOf(location.getLatLng().getLng()));

                    // add this hashmap to the list
                    nearbyDoctorsData.add(hashMap);
                }

                displayNearbyDoctors(mListViewDoctors);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MapQuestActivity.this, "Unable to Retrieve Nearby Doctors", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(gsonRequest);

    }

    private void displayNearbyDoctors(ListView mListView){

        if(!nearbyDoctorsData.isEmpty()) {

            insertion(nearbyDoctorsData);

            for (HashMap i: nearbyDoctorsData) {

                float distance = Float.parseFloat(i.get("distance").toString());
                String convertedDistance;

                if(distance < 1 ){
                    //convert to m

                    convertedDistance = String.format("%.0f",(distance*1000)) + " m";
                } else{
                    convertedDistance = String.format("%.1f",distance) + " km";
                }

                i.remove("distance");
                i.put("distance",convertedDistance);

            }

            String[] from = {
                    "id",
                    "name",
                    "address",
                    "distance"
            };

            int[] to = {R.id.hidden_nearby_id, R.id.text_nearby_name, R.id.text_nearby_address, R.id.text_nearby_distance};

            //create a list adapter
            doctorsAdapter = new SimpleAdapter(getApplicationContext(), nearbyDoctorsData, R.layout.item_nearby_doctor, from, to);

            //apply list adapter
            mListView.setAdapter(doctorsAdapter);
            mListView.setVisibility(View.VISIBLE);

            addDoctorsMarkers(this.mMapboxMap,nearbyDoctorsData);

            //apply onclick listener to redirect to the selected doctors profile activity
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //on click send to doctor profile activity
                    TextView mTextViewId = view.findViewById(R.id.hidden_nearby_id);
                    TextView mTextViewName = view.findViewById(R.id.text_nearby_name);
                    TextView mTextViewAddress = view.findViewById(R.id.text_nearby_address);

                    Intent intent = new Intent(MapQuestActivity.this, DoctorInfoActivity.class);

                    //pass intent extra(doctorID)
                    intent.putExtra("doctorId", mTextViewId.getText().toString());
                    intent.putExtra("doctorName", mTextViewName.getText().toString());
                    intent.putExtra("doctorAddress", mTextViewAddress.getText().toString());
                    startActivity(intent);

                }
            });

        }
    }

    private void addMarker(MapboxMap mapboxMap) {
        MarkerOptions markerOptions = new MarkerOptions();

        IconFactory iconFactory = IconFactory.getInstance(MapQuestActivity.this);
        Icon icon = iconFactory.fromResource(R.drawable.mapquest_icon);

        markerOptions.position(new com.mapbox.mapboxsdk.geometry.LatLng(Double.parseDouble(selectedLat),Double.parseDouble(selectedLong)));
        markerOptions.snippet("Selected Location");
        markerOptions.setIcon(icon);
        mapboxMap.addMarker(markerOptions);
    }

    private void addDoctorsMarkers(MapboxMap mapboxMap, List<HashMap<String,String>> nearbyDoctorsData){

        for (int i = 0; i < nearbyDoctorsData.size(); i++) {

            MarkerOptions markerOptions = new MarkerOptions();

            markerOptions.position(new com.mapbox.mapboxsdk.geometry.LatLng(
                    Double.parseDouble(nearbyDoctorsData.get(i).get("lat")),
                    Double.parseDouble(nearbyDoctorsData.get(i).get("lng")))
            );
            markerOptions.snippet(nearbyDoctorsData.get(i).get("name"));
            mapboxMap.addMarker(markerOptions);

        }

    }

    private void hideKeyboard(View view){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

    private static void insertion(List<HashMap<String,String>> a) {

        for(int i = 1; i < a.size(); i++){

            // grab next comparison value
            HashMap<String,String> nextInsertValue = a.get(i);

            // place value into final its position
            insertIntoIndex(nextInsertValue, a, 0, (i-1));

        }
    }

    private static void insertIntoIndex(HashMap<String,String> nextToInsert, List<HashMap<String,String>> a,int sortStart,int sortEnd) {


        // while we are within bounds of the sorted subarray and sub end pointer value is less than it's preceeding index value
        // move preceeded value to pointer index
        // move pointer index over to the left
        // place nextToInsert into empty space

        int index = sortEnd;

        while (index >= sortStart && Float.parseFloat(a.get(index).get("distance")) > Float.parseFloat(nextToInsert.get("distance"))) {

            a.remove(index+1);
            a.add(index + 1,a.get(index));
            index--;
        }

        a.remove(index+1);
        a.add(index + 1,nextToInsert);

    }

    @Override
    public void onBackPressed() {
        if(locationFound){
            mEditSearch.setText("");
            resetSearchAheadList();
            resetDoctorsList();
        } else {
            super.onBackPressed();
        }
    }
}





