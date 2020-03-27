package com.Group6.checkup;

import androidx.annotation.NonNull;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

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
    private MapView mMapView;
    private MapboxMap mMapboxMap;
    private SearchAheadService mSearchAheadServiceV3;
    private List<HashMap<String, String>> searchResultsData = new ArrayList<>();;
    private SimpleAdapter adapter;
    private String selectedLatLong;
    private boolean locationFound = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapQuest.start(getApplicationContext());
        setContentView(R.layout.activity_map_quest);

        mMapView = findViewById(R.id.mapquestMapView);
        mListViewSearch = findViewById(R.id.list_search_location);
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

                //TODO search on edit delte
//                if( count < before ){
//                    locationFound = false;
//                    resetResultsList();
//                }

                if(!locationFound && count > 2){
                    searchQuery();
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
                TextView latLong = view.findViewById(R.id.hidden_text_location);

                selectedLatLong = latLong.getText().toString();
                locationFound = true;

                mEditSearch.setText(displayAddress.getText().toString());
                mEditSearch.setSelection(displayAddress.getText().length());
                resetResultsList();
            }
        });

        //on item click send

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

    private void searchQuery(){
        mSearchAheadServiceV3 = new SearchAheadService(this, API_KEY);

        String queryString = mEditSearch.getText().toString();

        LatLng surreyBC = new LatLng(49.104599F,-122.823509F);

        List searchCollections = Arrays.asList(SearchCollection.AIRPORT, SearchCollection.ADMINAREA,
                SearchCollection.ADDRESS,SearchCollection.FRANCHISE,SearchCollection.POI);
        try {
            SearchAheadQuery query = new SearchAheadQuery
                    .Builder(queryString, searchCollections)
                    .location(surreyBC)
                    .build();

            mSearchAheadServiceV3.predictResultsFromQuery(query,
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
                                    hashMap.put("latlong", searchAheadResults.get(i).getPlace().getLatLng().toString());


                                    // add this hashmap to the list
                                    searchResultsData.add(hashMap);
                                }

                                //adapter inputs
                                String[] from = {
                                        "address",
                                        "city",
                                        "latlong"
                                };

                                int[] to = {R.id.text_result_address, R.id.text_result_city,R.id.hidden_text_location};

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

    private void displayNearbyDoctors(){
        //Get doctors
        //

    }

}


