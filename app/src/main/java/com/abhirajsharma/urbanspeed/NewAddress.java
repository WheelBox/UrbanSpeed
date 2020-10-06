package com.abhirajsharma.urbanspeed;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class NewAddress extends AppCompatActivity {
    private EditText name, phone, alternatePhone, pin, details, city, state, landmark;
    private CheckBox home, office;
    private Button saveAddress;
    private static String type = "";
    private Dialog loadingDialog;
    private Toolbar toolbar;
    private LinearLayout useGpsLocation;
    LocationManager locationManager;
    private static final int REQUEST_LOCATION = 1;
    private static  boolean IS_FROM_GPS=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_new_address );

        name = findViewById( R.id.add_address_name_et );
        phone = findViewById( R.id.add_address_number_et );
        alternatePhone = findViewById( R.id.add_address_alternate_no_et );
        pin = findViewById( R.id.add_address_pin_et );
        details = findViewById( R.id.add_address_details_et );
        city = findViewById( R.id.add_address_city_et );
        useGpsLocation=findViewById( R.id.current_location_ll );


        state = findViewById( R.id.add_address_state_et );
        landmark = findViewById( R.id.add_address_LandMark_et );
        home = findViewById( R.id.address_type_Home );
        office = findViewById( R.id.address_type_office );
        saveAddress = findViewById( R.id.save_address_btn );

        toolbar=findViewById( R.id.toolbar_editaddress );

        loadingDialog= new Dialog( NewAddress.this );
        loadingDialog.setContentView( R.layout.loading_progress_dialouge );
        loadingDialog.setCancelable( false );
        loadingDialog.getWindow().setLayout( ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT );
        loadingDialog.show();


        toolbar.setTitle( "Edit Address" );
        setSupportActionBar( toolbar );
        toolbar.setTitleTextColor( Color.parseColor( "#FFFFFF" ) );

        getSupportActionBar( ).setDisplayShowHomeEnabled( true );
        getSupportActionBar( ).setDisplayHomeAsUpEnabled( true );

        final int position = getIntent( ).getIntExtra( "position", -1 );
        final int layout_code = getIntent( ).getIntExtra( "layout_code", -2 );


        if (layout_code == 1) {

            FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getCurrentUser( ).getUid( ) )
                    .collection( "USER_DATA" ).document( "MY_ADDRESS" ).collection( "address_list" ).document( "address_" + position )
                    .get( ).addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                    if (task.isSuccessful( )) {

                        name.setText( task.getResult( ).get( "name" ).toString( ) );
                        phone.setText( task.getResult( ).get( "phone" ).toString( ) );
                        alternatePhone.setText( task.getResult( ).get( "altPhone" ).toString( ) );
                        pin.setText( task.getResult( ).get( "pin" ).toString( ) );
                        details.setText( task.getResult( ).get( "details" ).toString( ) );
                        city.setText( task.getResult( ).get( "city" ).toString( ) );
                        state.setText( task.getResult( ).get( "state" ).toString( ) );
                        landmark.setText( task.getResult( ).get( "landmark" ).toString( ) );

                        if (task.getResult( ).get( "type" ).toString( ).equals( "HOME" )) {
                            home.setChecked( true );
                            type = "HOME";

                        } else {
                            office.setChecked( true );
                            type = "OFFICE";
                        }
                        loadingDialog.dismiss();

                    }

                }
            } );
        }


        home.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener( ) {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (home.isChecked( )) {
                    type = "HOME";
                    office.setChecked( false );
                    home.setChecked( true );
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        home.setCompoundDrawableTintList( ColorStateList.valueOf( Color.parseColor( "#DF4444" ) ) );
                    }
                }
            }
        } );
        office.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener( ) {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (office.isChecked( )) {
                    type = "OFFICE";
                    home.setChecked( false );
                    office.setChecked( true );
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        office.setCompoundDrawableTintList( ColorStateList.valueOf( Color.parseColor( "#DF4444" ) ) );
                    }
                }
            }
        } );


        useGpsLocation.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                locationManager = (LocationManager) getSystemService( Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    OnGPS();
                } else {
                    getLocation();
                }

            }
        } );

        saveAddress.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                UploadAddress( layout_code,name.getText( ).toString( ), phone.getText( ).toString( ), alternatePhone.getText( ).toString( ),
                        pin.getText( ).toString( ), details.getText( ).toString( ), city.getText( ).toString( ), state.getText( ).toString( ),
                        landmark.getText( ).toString( ), type, position );

            }
        } );
        loadingDialog.dismiss();


    }

    private void UploadAddress(final int Layout_code, final String Name, final String Phone, final String AltPhone, final String Pin, final String Details, final String City, final String State, final String Landmak, final String Type, final int Position) {

        if (Name.isEmpty( ) || Phone.isEmpty( ) || Pin.isEmpty( ) || Details.isEmpty( ) || City.isEmpty( ) || State.isEmpty( ) || Type.isEmpty( )) {

            Toast.makeText( NewAddress.this, "Enter all * details", Toast.LENGTH_SHORT ).show( );
        } else {
            loadingDialog.show();


            final Map<String, Object> Data = new HashMap<>( );
            Data.put( "name", Name );
            Data.put( "phone", Phone );
            Data.put( "pin", Pin );
            Data.put( "details", Details );
            Data.put( "city", City );
            Data.put( "state", State );
            Data.put( "type", Type );
            Data.put( "index", Position );
            if(IS_FROM_GPS){
                Data.put( "address_details", Details );
            }else {
                Data.put( "address_details", Details + ", " + Landmak + ", " + City + ", " + State + ", " + Pin );
            }
            Data.put( "is_visible" ,true );


            if (Landmak.isEmpty( )) {
                Data.put( "landmark", " " );
            } else {
                Data.put( "landmark", Landmak );
            }
            if (AltPhone.isEmpty( )) {
                Data.put( "altPhone", " " );
            } else {
                Data.put( "altPhone", AltPhone );
            }


            FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getCurrentUser( ).getUid( ) )
                    .collection( "USER_DATA" ).document( "MY_ADDRESS" ).collection( "address_list" ).document( "address_" + Position )
                    .set( Data ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful( )) {
                        Toast.makeText( NewAddress.this, "Your Address is Added", Toast.LENGTH_SHORT ).show( );


                        if (Layout_code == 1) {
                            Intent intent = new Intent( NewAddress.this, MyAddress.class );
                            startActivity( intent );
                            loadingDialog.dismiss();

                        }else {
                            Map<String, Object> UserData = new HashMap<>( );
                            UserData.put( "fullname", Name );
                            UserData.put( "phone", Phone );
                            UserData.put( "address_type", Type );
                            UserData.put( "previous_position", 1 );
                            if(IS_FROM_GPS){
                                UserData.put( "address_details", Details );
                            }else {
                                UserData.put( "address_details", Details + ", " + Landmak + ", " + City + ", " + State + ", " + Pin );
                            }




                            if (AltPhone.isEmpty( )) {
                                UserData.put( "altPhone", " " );
                            } else {
                                UserData.put( "altPhone", AltPhone );
                            }

                            FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getCurrentUser( ).getUid( ) )
                                    .update( UserData ).addOnCompleteListener(
                                    new OnCompleteListener<Void>( ) {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful( )) {
                                                Intent intent = new Intent( NewAddress.this, MyAddress.class );
                                                startActivity( intent );
                                                finish();

                                                Map<String, Object> UserData = new HashMap<>( );
                                                UserData.put( "list_size", Position + 1 );
                                                FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getCurrentUser( ).getUid( ) )
                                                        .collection( "USER_DATA" ).document( "MY_ADDRESS" ).set( UserData )
                                                        .addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                MyAddress.selectedAdd.setVisibility( View.VISIBLE );
                                                                loadingDialog.dismiss();
                                                            }
                                                        } );


                                            }
                                        }
                                    }
                            );

                        }
                    }

                }
            } );


        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId( ) == android.R.id.home) {

            finish();

        }

        return super.onOptionsItemSelected( item );


    }

    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void getLocation() {

        if (ActivityCompat.checkSelfPermission(
                NewAddress.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                NewAddress.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {

            // Get the location manager
            LocationManager locationManager = (LocationManager)
                    getSystemService(LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String bestProvider = locationManager.getBestProvider(criteria, true);
            Location location = locationManager.getLastKnownLocation(bestProvider);
            LocationListener loc_listener = new LocationListener() {

                public void onLocationChanged(Location l) {}

                public void onProviderEnabled(String p) {}

                public void onProviderDisabled(String p) {}

                public void onStatusChanged(String p, int status, Bundle extras) {}
            };
            locationManager
                    .requestLocationUpdates(bestProvider, 0, 0, loc_listener);
            location = locationManager.getLastKnownLocation(bestProvider);
            try {
                double lat = location.getLatitude();
                double lon = location.getLongitude();
                IS_FROM_GPS=true;
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(this, Locale.getDefault());

                addresses = geocoder.getFromLocation(lat, lon, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String City = addresses.get(0).getLocality();
                String State = addresses.get(0).getAdminArea();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();
                String knownName = addresses.get(0).getFeatureName();


                pin.setText( postalCode );
                city.setText( City );
                state.setText( State );
                details.setText( address );
                landmark.setText( knownName );




            } catch (NullPointerException | IOException e) {

            }
        }
    }

}
