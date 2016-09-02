package com.example.user.rdrun;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import static com.example.user.rdrun.R.id.textView7;

public class ServiceActivity extends FragmentActivity implements OnMapReadyCallback {
    //Explicit
    private GoogleMap mMap;
    private String idString;
    private String avataString;
    private String nameString;
    private String surnameString;
    private ImageView imageView;
    private TextView nameTextView, surnameTextView;
    private int[] avataInts;
    private double userLatAdouble = 13.807213,userLngADouble = 100.574669;//Connection
    private LocationManager locationManager;//เปิดในการค้นหาพิกัดที่อยู่บนโลก location แผนที่ คือ ละติจูด ลองติดจูด
    private Criteria criteria;
    private static final String urlPHP="http://swiftcodingthai.com/rd/edit_location_pattama.php";
    private boolean statusABoolean=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_service);
        //Bind Widget
        imageView = (ImageView) findViewById(R.id.imageView7);
        nameTextView = (TextView) findViewById(R.id.textView8);
        surnameTextView = (TextView) findViewById(R.id.textView9);


        //Get value From Intent
        idString = getIntent().getStringExtra("id");
        avataString = getIntent().getStringExtra("Avata");
        nameString = getIntent().getStringExtra("Name");
        surnameString = getIntent().getStringExtra("Surname");
        //Setup Location
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);//ตำแหน่งห่างจากน้ำทะเล

        //Show Text
        nameTextView.setText(nameString);
        surnameTextView.setText(surnameString);
        //Show Avat
        MyConstant myConstant = new MyConstant();
        avataInts = myConstant.getAvataInts();
        imageView.setImageResource(avataInts[Integer.parseInt(avataString)]);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }//Main Method

    public void clickNormal(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    public void clickSatellife(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }

    public void clickTerrain(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    }

    public void clickHybrid(View view) {
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }


    private class SynAllUser extends AsyncTask<Void, Void, String> {
        //Explicit
        private Context context;
        private GoogleMap googleMap;
        private static final String urlJSON="http://swiftcodingthai.com/rd/get_user_master.php";
        private String[] nameStrings, surStrings;
        private int[] avataInts;
        private double[] latDoubles, lngDoubles;
       // private boolean statusABoolean = true;


        public SynAllUser(Context context, GoogleMap googleMap) {
            this.context = context;
            this.googleMap = googleMap;
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(urlJSON).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();

            } catch (Exception e) {
                Log.d("2SepV2", "e doIn==>" + e.toString());
                return null;
            }

        }//doInBack

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("2SepV2", "JSON ==>" +s);
            try {
                JSONArray jsonArray = new JSONArray(s);
                nameStrings = new String[jsonArray.length()];
                surStrings = new String[jsonArray.length()];
                avataInts = new int[jsonArray.length()];
                latDoubles = new double[jsonArray.length()];
                lngDoubles = new double[jsonArray.length()];
                for(int i=0;i<jsonArray.length();i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    nameStrings[i] = jsonObject.getString("Name");
                    surStrings[i] = jsonObject.getString("Surname");
                    avataInts[i] = Integer.parseInt(jsonObject.getString("Avata"));
                    latDoubles[i] = Double.parseDouble(jsonObject.getString("Lat"));
                    lngDoubles[i] = Double.parseDouble(jsonObject.getString("Lng"));
                    //Create Marker
                    MyConstant myConstant = new MyConstant();
                    int[] iconInts = myConstant.getAvataInts();


                   googleMap.addMarker(new MarkerOptions()
                            .position(new LatLng(latDoubles[i],lngDoubles[i]))
                            .icon(BitmapDescriptorFactory.fromResource(iconInts[avataInts[i]]))
                            .title(nameStrings[i]+""+surStrings[i]));

                    Log.d("2SepV3", "Name ("+i+")=" + nameStrings[i]);
                    Log.d("2SepV3", "Lat ("+i+")=" + latDoubles[i]);
                    Log.d("2SepV3", "Lng ("+i+")=" + lngDoubles[i]);
                    Log.d("2SepV3", "==============================");
                }//for

                googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {
                        statusABoolean = !statusABoolean;
                        Log.d("2SepV4", "Status===>" + statusABoolean);
                    }
                });



            } catch (Exception e) {
                Log.d("2SepV3", "e onPost ==>" + e.toString());
            }

      }
    }//SynAllUser Class




    @Override
    //เช็คครั้งแรกจะได้ Network provider ก่อน แล้วจะได้ gps
    protected void onResume() {
        super.onResume();
        locationManager.removeUpdates(locationListener);
        Location networkLocation = myFindLocation(LocationManager.NETWORK_PROVIDER);
        if(networkLocation != null){
            userLatAdouble = networkLocation.getLatitude();
            userLngADouble = networkLocation.getLongitude();
        }
        Location gpsLocation = myFindLocation(LocationManager.GPS_PROVIDER);
        if (gpsLocation != null) {
            userLatAdouble = gpsLocation.getLatitude();
            userLngADouble = gpsLocation.getLongitude();
        }



    }// onResume

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(locationListener);

    }

    public  Location myFindLocation(String strProvider) {
        Location location = null;
        if (locationManager.isProviderEnabled(strProvider)) {
            locationManager.requestLocationUpdates(strProvider, 1000, 10, locationListener);
            location = locationManager.getLastKnownLocation(strProvider);

        } else {
            Log.d("1SepV1", "Cannot find Location");
        }

       // Log.d("2SepV", "Cannot find Location"+location);
        return location;
    }

    public LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            userLatAdouble = location.getLatitude();
            userLngADouble = location.getLongitude();


        }//OnLocationChange

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Setup Center of Map
        LatLng latLng = new LatLng(userLatAdouble, userLngADouble);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));
        //Loop
        myLoop();
    }

    private void myLoop() {
        //To do สิ่งที่ต้องทำ
        Log.d("1SepV2", "Lat==>" + userLatAdouble);
        Log.d("1SepV2", "Lng==>" + userLngADouble);
        editLatLngOnServer();
        if (statusABoolean) {
            createMarker();
        }


        //Post Delay
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                myLoop();
            }
        },1000);

    }   //myLoop

    private void createMarker() {
        //Clear Marker
        mMap.clear();

        SynAllUser synAllUser = new SynAllUser(this,mMap);
        synAllUser.execute();
    }//createMarker

    private void editLatLngOnServer() {
        OkHttpClient okHttpClient = new OkHttpClient();
        final RequestBody requestBody = new FormEncodingBuilder()
                .add("isAdd", "true")
                .add("id", idString)
                .add("Lat", Double.toString(userLatAdouble))
                .add("Lng", Double.toString(userLngADouble))
                .build();
        Request .Builder builder = new Request.Builder();
        Request request = builder.url(urlPHP).post(requestBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.d("2SepV1", "e ==>" + e.toString());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.d("2SepV1", "Result ==>" + response.body().string());
            }
        });

    }   //editLating
} //Main Class
