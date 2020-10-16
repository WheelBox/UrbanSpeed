package com.abhirajsharma.urbanspeed;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserDetails extends AppCompatActivity {

    private ImageView profile_Image;
    private EditText userNmae;
    private Button upload;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;
    private StorageReference mStorageRef;
    private DatabaseReference mDetabaseRef;
    private String imageUri="";
    private Dialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_user_details );
        mStorageRef = FirebaseStorage.getInstance( ).getReference( "USERS" );
        mDetabaseRef = FirebaseDatabase.getInstance( ).getReference( "uploads" );
        profile_Image=findViewById( R.id. selectProfilePicture);
        userNmae=findViewById( R.id.profileUsername );
        upload=findViewById( R.id.profileCreateBtn );

        DBquaries.setShop();



        loadingDialog= new Dialog( UserDetails.this );
        loadingDialog.setContentView( R.layout.loading_progress_dialouge );
        loadingDialog.setCancelable( true );
        loadingDialog.getWindow().setLayout( ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT );

        upload.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {


                if (userNmae.getText().toString().isEmpty()){
                    Toast.makeText( UserDetails.this, "Fill UserName ", Toast.LENGTH_SHORT ).show( );
                }else {
                    loadingDialog.show();

                    Map<String,Object> userData=new HashMap<>(  );
                    userData.put( "permanent_name", userNmae.getText().toString());
                    userData.put( "image", imageUri);

                    FirebaseFirestore.getInstance().collection( "USERS" ).document( FirebaseAuth.getInstance().getCurrentUser().getUid() ).update(userData).
                            addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Intent intent=new Intent( UserDetails.this,MainActivity.class );
                                        startActivity( intent );

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