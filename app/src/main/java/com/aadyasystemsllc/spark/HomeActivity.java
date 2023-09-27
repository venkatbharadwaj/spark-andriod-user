package com.aadyasystemsllc.spark;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import org.checkerframework.checker.nullness.qual.NonNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.firebase.appcheck.internal.util.Logger.TAG;

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback {
    String parkingname, locationdata, parkingpoint, mobilenumber, ImageUrl;
    //ParkingDetails parkingDetails;
    ArrayList<ParkingDetails> parkingDetailsArrayList = new ArrayList<>();
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;
    private static final int REQUEST_CODE = 101;
    private GoogleMap mMap;

    public CarouselPagerAdapter adapter;
    public static ViewPager pager;
    public final static int LOOPS = 1000;
    public static int count; //ViewPager items size
    /**
     * You shouldn't define first page = 0.
     * Let define firstpage = 'number viewpager size' to make endless carousel
     */
    public static int FIRST_PAGE = 3;
    String selectedCarTypeId = "";
    private FragmentManager fragmentManager;
    GoogleMap googleMap;
    private long pressedTime;

    GeoPoint geoPoint;
    double lat, lng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        pager = findViewById(R.id.myviewpager);
        fragmentManager = getSupportFragmentManager();

        initializeListViews();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fetchLocation();

    }

    private void initializeListViews() {
        FirebaseFirestore databaseReference = FirebaseFirestore.getInstance()/*.collection("ParkingDetails")*/;
        //   Log.e("dataa","value is:"+databaseReference.toString().);
        databaseReference.collection("ParkingDetails").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (QueryDocumentSnapshot document : task.getResult()) {

                        // String name=document.getString("name");
                      /*  double lat = document.getDouble("latitude");
                        double lng = document.getDouble("longitude");
                        Log.e("geoPoint", "geoPoint:" + lat);*/

                        Log.e("dataa", "value is:" + document.toString());
                        //   parkingDetailsArrayList.clear();
                        //  for (int i = 0; i < document.toString().length(); i++) {

                        // geoPoint = document.getGeoPoint("parkingPoint");


                        HashMap parkingJsonObject = (HashMap) document.getData();
                        Log.e("parkingJsonObject", "parkingJsonObject value is:" + parkingJsonObject.size());


                        ParkingDetails parkingDetails = new ParkingDetails();
                        parkingDetails.setParkingName(String.valueOf(parkingJsonObject.get("parkingName")));
                        parkingDetails.setParkingLocation(String.valueOf(parkingJsonObject.get("parkingLocation")));
                        parkingDetails.setParkingPoint(String.valueOf(parkingJsonObject.get("parkingPoint")));
                        parkingDetails.setMobileNumber(String.valueOf(parkingJsonObject.get("mobileNumber")));
                        parkingDetails.setImageUrl(String.valueOf(parkingJsonObject.get("ImageUrl")));
                        parkingDetails.setCommunity_location(document.getGeoPoint("parkingPoint"));
                        parkingDetailsArrayList.add(parkingDetails);


                           // googleMap.addPolygon(poly);

                     //   }



                    /*    communities = new ArrayList<>();
                        db.collection("Rooms").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                        Communities communityList = (Communities) documentSnapshot.getData();
                                        communities.add(communityList);
                                    }
                                }
                            }
                        });*/
                        /*   }*/

                          /*  ParkingDetails parkingDetails = new ParkingDetails(mobilenumber, locationdata, parkingname, parkingpoint,ImageUrl);
                            parkingDetailsArrayList.add(parkingDetails);*/


                        // }


                    }

                    adapter = new CarouselPagerAdapter(HomeActivity.this, fragmentManager, parkingDetailsArrayList);
                    pager.setAdapter(adapter);
                    setAdapter();


                } else {
                    Log.e("error", "value is:" + task.getException());
                }
            }
        });
    /*    databaseReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(Task<QuerySnapshot> task) {
if(task.isSuccessful()){
    QuerySnapshot doc=task.getResult();
    if (!doc.isEmpty()){
        Log.e("notempty",""+doc.getDocuments().toString());
    }else {
        Log.e("notempty","no data");
    }
}
            }
        });*/
       /* FirebaseDatabase database=FirebaseDatabase.getInstance();
        DatabaseReference myRef=database.getReference("/ParkingDetails");

        Log.e("dataa","value is:"+myRef.toString());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for(DataSnapshot ds:snapshot.getChildren()){
                    ParkingDetails parkingDetails=ds.getValue(ParkingDetails.class);
                    Log.e("dataa","value is:"+parkingDetails.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });*/
    }

    private void fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    Log.e("location data", "location datassss" + currentLocation);

                    Toast.makeText(getApplicationContext(), currentLocation.getLatitude() + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                    SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.myMap);
                    assert supportMapFragment != null;
                    supportMapFragment.getMapAsync(HomeActivity.this);
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("I am here!");
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
        googleMap.addMarker(markerOptions);
       /* mMap.addMarker(new MarkerOptions().position(latLng).title("I am here!"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));*/
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLocation();
                }
                break;
        }
    }

    private void setAdapter() {
        // pager.setAdapter(new CarouselPagerAdapter(getFragmentManager(),getContext()));


        //set page margin between pages for viewpager

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int pageMargin = ((metrics.widthPixels / 4) * 2);
        pager.setPageMargin(-pageMargin);

        //  FragmentManager fragManager = myContext.getFragmentManager();
        /*adapter = new CarouselPagerAdapter(myContext, getFragmentManager());
        pager.setAdapter(adapter);
        adapter.notifyDataSetChanged();*/

       /* adapter = new CarouselPagerAdapter(this, fragmentManager, parkingDetailsArrayList);
        pager.setAdapter(adapter);
        adapter.notifyDataSetChanged();*/

        pager.addOnPageChangeListener(adapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                selectedCarTypeId = parkingDetailsArrayList.get(position).mobileNumber;


                // Toast.makeText(getApplicationContext(), currentLocation.getLatitude() + "" + currentLocation.getLongitude(), Toast.LENGTH_SHORT).show();
                Log.e("location data", "location data" + parkingDetailsArrayList.get(position).getParkingPoint());
                //  Toast.makeText(getApplicationContext(), selectedlocation.getLatitude() + "" + selectedlocation.getLongitude(), Toast.LENGTH_SHORT).show();

                if (parkingDetailsArrayList.get(position).getParkingPoint() != null) {
                    GeoPoint polyGeo = (GeoPoint) parkingDetailsArrayList.get(position).getCommunity_location();
                    double lat = polyGeo.getLatitude();
                    double lng = polyGeo.getLongitude();
                    Log.e("latitide data", "latitude" + parkingDetailsArrayList.get(position).getParkingPoint());
                    Log.e("latitide data", "latitude" + lat);
                    Log.e("latitide data", "latitude" + lng);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // Set current item to the middle page so we can fling to both
        // directions left and right
        pager.setCurrentItem(FIRST_PAGE);
        pager.setOffscreenPageLimit(1);

    }

    @Override
    public void onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            //super.onBackPressed();
            finishAffinity();
        } else {
            Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }

    public class Points {
        double x;
        double y;
    }

    class ClassA {
        List<Points> polygon;
    }
}