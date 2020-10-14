package com.abhirajsharma.urbanspeed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import com.abhirajsharma.urbanspeed.adapter.AdminOrderAdaptrer;
import com.abhirajsharma.urbanspeed.model.AdminOrderModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AdminPendingOrders extends AppCompatActivity {


    private Toolbar toolbar;
    private RecyclerView pendingOrderRecycler;
    private AdminOrderAdaptrer adminOrderAdaptrer;
    private List<AdminOrderModel> adminOrderModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_admin_pending_orders );


        toolbar=findViewById( R.id.pending_toolbar );
        pendingOrderRecycler=findViewById( R.id.pendingOrderRecycler );

        adminOrderModelList = new ArrayList<>( );

        adminOrderAdaptrer = new AdminOrderAdaptrer( adminOrderModelList );
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager( this );
        linearLayoutManager.setOrientation( RecyclerView.VERTICAL );
        pendingOrderRecycler.setLayoutManager( linearLayoutManager );
        pendingOrderRecycler.setAdapter( adminOrderAdaptrer );

         String store_id= getIntent().getStringExtra( "store_id" );



        toolbar.setTitle( "Pending Order" );
        setSupportActionBar( toolbar );
        toolbar.setTitleTextColor( Color.parseColor( "#FFFFFF" ) );
        getSupportActionBar().setDisplayShowHomeEnabled( true );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );


        FirebaseFirestore.getInstance().collection( "STORES" ).document( store_id ).collection( "ORDERS" ).document( "order_list" ).get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful()){
                    long list_size=(long)task.getResult().get( "list_size" );
                    for(long x=0;x<list_size;x++){

                        String order_id=task.getResult().get( "order_id_"+x ).toString();
                        FirebaseFirestore.getInstance().collection( "ORDERS" ).document( order_id ).get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()){

                                    boolean is_paid=(boolean) task.getResult().get( "is_paid" );
                                    if(!is_paid){

                                        adminOrderModelList.add( new AdminOrderModel( task.getResult().get( "id" ).toString(),
                                                task.getResult().get( "time" ).toString(),
                                                task.getResult().get( "otp" ).toString(),
                                                task.getResult().get( "grand_total" ).toString(),
                                                (boolean)task.getResult().get( "is_paid" ),
                                                (boolean)task.getResult().get( "is_pickUp" )
                                        ) );

                                        adminOrderAdaptrer.notifyDataSetChanged();


                                    }



                                }

                            }
                        } );


                        adminOrderAdaptrer.notifyDataSetChanged();


                    }



                }

            }
        } );

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,Products.class);
        startActivity(intent);
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){
            onBackPressed();
        }

        return super.onOptionsItemSelected( item );
    }

}