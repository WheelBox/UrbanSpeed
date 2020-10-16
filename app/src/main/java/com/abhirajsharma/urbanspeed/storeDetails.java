package com.abhirajsharma.urbanspeed;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
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

public class storeDetails extends AppCompatActivity {

    private EditText name, description, delivery_charges;
    private TextView address;
    private Button next, addLocation;
    private String lat1 = "";
    private String lon1 = "";
    private String imageUri="";
    private TextView addimage;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDetabaseRef;
    private String store_id;
    private ImageView store_image;
    private Dialog loadingDialog;

    FusedLocationProviderClient fusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_store_details );

        name = findViewById( R.id.add_name_et );
        description = findViewById( R.id.add_description_et );
        delivery_charges = findViewById( R.id.add_deliveryCharge_name_et );
        address = findViewById( R.id.address_txt );
        next = findViewById( R.id.next_continue );
        addLocation = findViewById( R.id.add_location );
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient( this );
        addimage = findViewById( R.id.addShopImage );
        store_image = findViewById( R.id.store_image );
        mStorageRef = FirebaseStorage.getInstance( ).getReference( "STORES" );
        mDetabaseRef = FirebaseDatabase.getInstance( ).getReference( "uploads" );
        loadingDialog= new Dialog( storeDetails.this );
        loadingDialog.setContentView( R.layout.loading_progress_dialouge );
        loadingDialog.setCancelable( false );
        loadingDialog.getWindow().setLayout( ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT );


        store_id = getIntent( ).getStringExtra( "store_id" );


        addLocation.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                getLocation( );
            }
        } );


        next.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                loadingDialog.show();
                if (!(name.getText( ).toString( ).isEmpty( ) || description.getText( ).toString( ).isEmpty( ) || delivery_charges.getText( ).toString( ).isEmpty( ) || address.getText( ).toString( ).isEmpty( ))) {
                    Map<String, Object> adminData = new HashMap<>( );
                    adminData.put( "name", name.getText( ).toString( ) );
                    adminData.put( "category", description.getText( ).toString( ) );
                    adminData.put( "delivery_charges", delivery_charges.getText( ).toString( ) );
                    adminData.put( "address", address.getText( ).toString( ) );
                    adminData.put( "image", imageUri );
                    adminData.put( "lat", lat1 );
                    adminData.put( "lon", lon1 );
                    FirebaseFirestore.getInstance( ).collection( "STORES" ).document( store_id ).set( adminData ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful( )) {

                                Map<String, Object> Data = new HashMap<>( );
                                adminData.put( "list_size", 0 );

                                FirebaseFirestore.getInstance( ).collection( "STORES" ).document( store_id ).collection( "ORDERS" ).document( "order_list" )
                                        .set( Data ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            loadingDialog.dismiss();
                                            Intent intent=new Intent( storeDetails.this,Products.class );
                                            intent.putExtra( "store_id" ,store_id );
                                            startActivity( intent );
                                        }
                                    }
                                } ) ;


                            }
                        }
                    } );
                } else {
                    loadingDialog.dismiss();
                    Toast.makeText( storeDetails.this, "Fill all the details", Toast.LENGTH_SHORT ).show( );
                }
            }
        } );


        addimage.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        } );


    }


    private void getLocation() {
        if (ActivityCompat.checkSelfPermission( storeDetails.this,
                Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED) {

            fusedLocationProviderClient.getLastLocation( ).addOnCompleteListener( new OnCompleteListener<Location>( ) {
                @Override
                public void onComplete(@NonNull Task<Location> task) {

                    Location location = task.getResult( );
                    if (location != null) {


                        try {

                            double lat = location.getLatitude( );
                            double lon = location.getLongitude( );

                            lat1 = String.valueOf( lat );
                            lon1 = String.valueOf( lon );
                            List<Address> addresses;
                            Geocoder geocoder = new Geocoder( storeDetails.this, Locale.getDefault( ) );
                            addresses = geocoder.getFromLocation( lat, lon, 1 ); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                            String Address = addresses.get( 0 ).getAddressLine( 0 );

                            address.setText( Address );
                        } catch (NullPointerException | IOException e) {
                            e.printStackTrace( );
                        }
                    } else {
                        Toast.makeText( storeDetails.this, "location is null", Toast.LENGTH_SHORT ).show( );
                    }

                }
            } );

        } else {
            ActivityCompat.requestPermissions( storeDetails.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44 );

        }

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
            final StorageReference fileref = mStorageRef.child( name.getText( ).toString( ) + System.currentTimeMillis( ) + "." + getFileExtention( mImageUri ) );
            Toast.makeText( storeDetails.this, "Uploading Image", Toast.LENGTH_SHORT ).show( );

            fileref.putFile( mImageUri ).addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>( ) {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


             /*     Upload upload= new Upload( productname.getText().toString().trim(),
                            taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());

                    String UploadId=mDetabaseRef.push().getKey();
                    mDetabaseRef.child( UploadId ).setValue( upload );*/

                    Objects.requireNonNull( taskSnapshot.getMetadata( ) ).getReference( ).getDownloadUrl( ).addOnSuccessListener( new OnSuccessListener<Uri>( ) {
                        @Override
                        public void onSuccess(final Uri uri) {
                            imageUri=uri.toString( ) ;
                            Glide.with( storeDetails.this ).load( uri.toString( ) ).into( store_image);
                            Toast.makeText( storeDetails.this, "Upload Successful", Toast.LENGTH_SHORT ).show( );


                        }
                    } );


                }
            } ).addOnFailureListener( new OnFailureListener( ) {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText( storeDetails.this, e.getMessage( ), Toast.LENGTH_SHORT ).show( );
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