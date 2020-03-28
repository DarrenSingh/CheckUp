package com.Group6.checkup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.Group6.checkup.Entities.Doctor;
import com.Group6.checkup.Utils.Dao.DoctorDao;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
    private SimpleAdapter adapter;

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
        mImageSearch = findViewById(R.id.img_search_location);

        mImageSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        mEditSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if( count == 0 || s.toString().trim().length() == 0){
                    locationFound = false;
                    resetResultsList();
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
                resetResultsList();
                mMapboxMap.clear();
                mMapboxMap.setCameraPosition(new CameraPosition.Builder()
                        .target(new com.mapbox.mapboxsdk.geometry.LatLng(Double.parseDouble(selectedLat)-0.05,Double.parseDouble(selectedLong))) // Sets the new camera position
                        .zoom(10) // Sets the zoom to level 10
                        .tilt(20) // Set the camera tilt to 20 degrees
                        .build());
                addMarker(mMapboxMap);
                displayNearbyDoctors(mListViewDoctors);
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

                                for (int i = 0;i < size ; i++) {
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

                                adapter = new SimpleAdapter(getApplicationContext(), searchResultsData, R.layout.item_search_result, from, to);
                                mListViewSearch.setAdapter(adapter);
                                mListViewSearch.setVisibility(View.VISIBLE);
                            } else {
                                resetResultsList();
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

    private void resetResultsList(){
        searchResultsData.clear();
        adapter.notifyDataSetChanged();
    }

    private void displayNearbyDoctors(ListView mListView){
        //TODO get doctor addresses
        //Get current latlong

        //Get doctors
        List<Doctor> doctors = new DoctorDao(getApplicationContext()).findAll();

        //Get all addresses
//        List<String> addresses = new ArrayList<>();
//
//        for (int i = 0; i < doctors.size(); i++) {
//            addresses.add(doctors.get(i).getOfficeAddress());
//        }

        //do an api call for distance matrix


        //filter results into hashmap list
        List<HashMap<String,String>> doctorData = new ArrayList<>();

        for (int i = 0; i < doctors.size(); i++) {
            // create a hashmap
            HashMap<String, String> hashMap = new HashMap<>();

            // convert image int to a string and place it into the hashmap with an images key
            hashMap.put("id",String.valueOf(doctors.get(i).getID()));
            hashMap.put("name","Dr. "+doctors.get(i).getFirstName()+" "+doctors.get(i).getLastName());
            hashMap.put("address",doctors.get(i).getOfficeAddress());
            hashMap.put("distance","0.0 km");

            // add this hashmap to the list
            doctorData.add(hashMap);
        }

        String[] from = {
                "id",
                "name",
                "address",
                "distance"
        };

        int[] to = {R.id.hidden_nearby_id,R.id.text_nearby_name,R.id.text_nearby_address,R.id.text_nearby_distance};

        //create a list adapter
        SimpleAdapter simpleAdapter = new SimpleAdapter(getApplicationContext(),doctorData,R.layout.item_nearby_doctor,from,to);

        //apply list adapter
        mListView.setAdapter(simpleAdapter);
        mListView.setVisibility(View.VISIBLE);

        //apply onclick listener
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //on click send to doctor profile activity
                TextView mTextViewId = view.findViewById(R.id.hidden_nearby_id);
                TextView mTextViewName = view.findViewById(R.id.text_nearby_name);
                TextView mTextViewAddress = view.findViewById(R.id.text_nearby_address);

                Intent intent = new Intent(MapQuestActivity.this,DoctorInfoActivity.class);

                //pass intent extra(doctorID)
                intent.putExtra("doctorId",mTextViewId.getText().toString());
                intent.putExtra("doctorName",mTextViewName.getText().toString());
                intent.putExtra("doctorAddress",mTextViewAddress.getText().toString());
                startActivity(intent);

            }
        });

    }

    private void addMarker(MapboxMap mapboxMap) {
        MarkerOptions markerOptions = new MarkerOptions();


        markerOptions.position(new com.mapbox.mapboxsdk.geometry.LatLng(Double.parseDouble(selectedLat),Double.parseDouble(selectedLong)));
//        markerOptions.title("MapQuest");
        markerOptions.snippet("Current Location");
        mapboxMap.addMarker(markerOptions);
    }

    private void hideKeyboard(View view){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

}


