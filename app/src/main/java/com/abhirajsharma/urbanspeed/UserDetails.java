package com.abhirajsharma.urbanspeed;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class UserDetails extends AppCompatActivity {

    private ImageView profile_Image;
    private EditText userNmae;
    private Button upload,addLocation;
    private TextView address_txt;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDetabaseRef;
    private String imageUri="";
    private String lat1 = "";
    private String lon1 = "";
    private Dialog loadingDialog;
    private static final int REQUEST_CODE_LOCATION = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_user_details );
        mStorageRef = FirebaseStorage.getInstance( ).getReference( "USERS" );
        mDetabaseRef = FirebaseDatabase.getInstance( ).getReference( "uploads" );
        profile_Image=findViewById( R.id. selectProfilePicture);
        userNmae=findViewById( R.id.profileUsername );
        upload=findViewById( R.id.profileCreateBtn );
        address_txt=findViewById( R.id.address_txt_user);
        addLocation=findViewById( R.id.addLocationBtn );


        addLocation.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                LocationManager locationManager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
                if (!locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER )) {
                    Toast.makeText( UserDetails.this, "Turn On your Location to proceed", Toast.LENGTH_SHORT ).show( );
                    startActivity( new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS ) );
                } else {
                    if (ContextCompat.checkSelfPermission(
                            getApplicationContext( ), Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions( UserDetails.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_LOCATION );

                    } else {
                        getCURRENTlocation( );
                    }                }
            }
        } );



        loadingDialog= new Dialog( UserDetails.this );
        loadingDialog.setContentView( R.layout.loading_progress_dialouge );
        loadingDialog.setCancelable( true );
        loadingDialog.getWindow().setLayout( ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT );

        upload.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {


                if (userNmae.getText().toString().isEmpty()||lat1.isEmpty()||lon1.isEmpty()){
                    Toast.makeText( UserDetails.this, "Add All Details ", Toast.LENGTH_SHORT ).show( );
                }else {
                    loadingDialog.show();


                    Map<String,Object> userData=new HashMap<>(  );
                    userData.put( "permanent_name", userNmae.getText().toString());
                    userData.put( "image", imageUri);
                    userData.put( "lat", lat1);
                    userData.put( "lon", lon1);

                    FirebaseFirestore.getInstance().collection( "USERS" ).document( FirebaseAuth.getInstance().getCurrentUser().getUid() ).update(userData).
                            addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        DBquaries.findDistance();
                                        DBquaries.setShop();
                                        Intent intent=new Intent( UserDetails.this,MainActivity.class );
                                        startActivity( intent );
                                        finish();

                                    }
                                }
                            } );

                }
            }
        } );
        profile_Image.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                if(userNmae.getText().toString().isEmpty()){
                    Toast.makeText( UserDetails.this, "Fill User Name First ", Toast.LENGTH_SHORT ).show( );
                }else {
                    openFileChooser();
                }

            }
        } );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );

        if (requestCode == REQUEST_CODE_LOCATION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCURRENTlocation( );
            } else {
                Toast.makeText( this, "Permission Denied", Toast.LENGTH_SHORT ).show( );
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getCURRENTlocation() {

        LocationRequest locationRequest = new LocationRequest( );
        locationRequest.setInterval( 10000 );
        locationRequest.setFastestInterval( 3000 );
        locationRequest.setPriority( LocationRequest.PRIORITY_HIGH_ACCURACY );


        LocationServices.getFusedLocationProviderClient( getApplicationContext( ) ).
                requestLocationUpdates( locationRequest, new LocationCallback( ) {

                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult( locationResult );

                        LocationServices.getFusedLocationProviderClient( UserDetails.this )
                                .removeLocationUpdates( this );
                        if(locationResult!=null && locationResult.getLocations().size()>0){
                            int LatestLocationIndex=locationResult.getLocations().size()-1;
                            double lat=locationResult.getLocations().get( LatestLocationIndex ).getLatitude();
                            double lon=locationResult.getLocations().get( LatestLocationIndex ).getLongitude();
                            lat1=String.valueOf(lat  );
                            lon1=String.valueOf(lon  );



                            Geocoder geocoder;
                            List<Address> addresses;
                            geocoder = new Geocoder( getApplicationContext(), Locale.getDefault( ) );

                            try {
                                address_txt.setVisibility( View.VISIBLE );
                                addresses = geocoder.getFromLocation( lat, lon, 1 ); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                                String Address = addresses.get( 0 ).getAddressLine( 0 );
                                address_txt.setText( Address );



                            } catch (IOException e) {
                                e.printStackTrace( );
                            }


                        }else {
                            Toast.makeText( UserDetails.this, "null", Toast.LENGTH_SHORT ).show( );

                        }
                    }
                }, Looper.getMainLooper( ) );

    }


    private void openFileChooser() {

        Intent intent = new Intent( );
        intent.setType( "image/*" );
        intent.setAction( Intent.ACTION_GET_CONTENT );
        startActivityForResult( intent, PICK_IMAGE_REQUEST );


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData( ) != null) {


            mImageUri = data.getData( );

            uploadFile();

        }
    }
    private String getFileExtention(Uri uri) {
        ContentResolver cR = getContentResolver( );
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton( );
        return mimeTypeMap.getExtensionFromMimeType( cR.getType( uri ) );
    }
    public void uploadFile() {

        if (mImageUri != null) {
            final StorageReference fileref = mStorageRef.child( userNmae.getText( ).toString( ) + System.currentTimeMillis( ) + "." + getFileExtention( mImageUri ) );
            Toast.makeText( UserDetails.this, "Uploading Image", Toast.LENGTH_SHORT ).show( );
            loadingDialog.show();
            fileref.putFile( mImageUri ).addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>( ) {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    loadingDialog.show();

             /*     Upload upload= new Upload( productname.getText().toString().trim(),
                            taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());

                    String UploadId=mDetabaseRef.push().getKey();
                    mDetabaseRef.child( UploadId ).setValue( upload );*/

                    Objects.requireNonNull( taskSnapshot.getMetadata( ) ).getReference( ).getDownloadUrl( ).addOnSuccessListener( new OnSuccessListener<Uri>( ) {
                        @Override
                        public void onSuccess(final Uri uri) {
                            loadingDialog.dismiss();
                            imageUri=uri.toString( ) ;
                            Glide.with( UserDetails.this ).load( uri.toString( ) ).into( profile_Image);
                            Toast.makeText( UserDetails.this, "Upload Successful", Toast.LENGTH_SHORT ).show( );


                        }
                    } );

                    loadingDialog.dismiss();
                }
            } ).addOnFailureListener( new OnFailureListener( ) {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText( UserDetails.this, e.getMessage( ), Toast.LENGTH_SHORT ).show( );
                }
            } ).addOnProgressListener( new OnProgressListener<UploadTask.TaskSnapshot>( ) {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {


                }
            } );
        } else {
            Toast.makeText( this, "no item selected", Toast.LENGTH_SHORT ).show( );
        }


    }


}