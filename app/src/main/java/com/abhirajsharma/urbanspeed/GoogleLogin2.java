package com.abhirajsharma.urbanspeed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class GoogleLogin2 extends AppCompatActivity {

    private Button user,admin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_google_login2 );

        user=findViewById( R.id.google_user );
        admin=findViewById( R.id.google_admin );

        user.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                updateUI( "user" );

            }
        } );

        admin.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                updateUI( "vendor" );
            }
        } );
    }



    private void updateUI(String account_type){

        FirebaseFirestore.getInstance().collection("USERS").document( FirebaseAuth.getInstance().getCurrentUser().getUid()).get()
                .addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot snapshot=task.getResult();
                            if(snapshot.exists()){
                                DBquaries.findDistance();
                                DBquaries.setShop();
                                startActivity(new Intent(GoogleLogin2.this, MainActivity.class));


                            }else {
                                FirebaseFirestore.getInstance().collection("ADMINS").document( FirebaseAuth.getInstance().getCurrentUser().getUid()).get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if(task.isSuccessful()){

                                            DocumentSnapshot snapshot1=task.getResult();
                                            if(snapshot1.exists()){

                                                String id=task.getResult().get( "store_id" ).toString();
                                                Intent intent=new Intent( GoogleLogin2.this,Products.class );
                                                intent.putExtra( "store_id",id );
                                                startActivity( intent );

                                            }else {
                                                if(account_type.equals( "vendor" )){
                                                    DBquaries.setAdminDATA( GoogleLogin2.this );
                                                }else if(account_type.equals( "user" )){
                                                    DBquaries.setUserData();

                                                    Map<String,Object> data=new HashMap<>(  );
                                                    data.put( "fullname","" );
                                                    data.put( "lat","" );
                                                    data.put( "lon","" );
                                                    data.put( "permanent_phone","" );
                                                    data.put( "previous_position",0 );
                                                    data.put( "phone","" );
                                                    data.put( "address_details","" );
                                                    data.put( "address_type","" );
                                                    data.put( "img","" );

                                                    FirebaseFirestore.getInstance().collection( "USERS" ).document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set( data )
                                                            .addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if(task.isSuccessful()){
                                                                        startActivity(new Intent(GoogleLogin2.this, UserDetails.class));

                                                                    }
                                                                }
                                                            } );


                                                }

                                            }


                                        }
                                    }
                                } );


                            }


                        }

                    }
                } );


    }
}