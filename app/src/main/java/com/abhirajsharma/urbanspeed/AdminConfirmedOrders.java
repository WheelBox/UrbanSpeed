package com.abhirajsharma.urbanspeed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.abhirajsharma.urbanspeed.adapter.AdminOrderAdaptrer;
import com.abhirajsharma.urbanspeed.model.AdminOrderModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AdminConfirmedOrders extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView confirmOrderRecycler;
    private AdminOrderAdaptrer adminOrderAdaptrer;
    private List<AdminOrderModel> adminOrderModelList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_admin_confirmed_orders );



        toolbar=findViewById( R.id.confirm_toolbar );
        confirmOrderRecycler=findViewById( R.id.confirmOrderRecycler );



        toolbar.setTitle( "Confirm Order" );
        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayShowHomeEnabled( true );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        adminOrderModelList = new ArrayList<>( );

        adminOrderAdaptrer = new AdminOrderAdaptrer( adminOrderModelList );
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager( this );
        linearLayoutManager.setOrientation( RecyclerView.VERTICAL );
        confirmOrderRecycler.setLayoutManager( linearLayoutManager );
        confirmOrderRecycler.setAdapter( adminOrderAdaptrer );


        String store_id= getIntent().getStringExtra( "store_id" );


        FirebaseFirestore.getInstance().collection( "STORES" ).document( store_id ).collection( "ORDERS" ).document( "order_list" ).get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    long list_size=(long)task.getResult().get( "list_size" );
                    if(list_size==0){
                        Toast.makeText( AdminConfirmedOrders.this, "No Orders Yet !", Toast.LENGTH_SHORT ).show( );
                    }
                    for(long x=0;x<list_size;x++){

                        String order_id=task.getResult().get( "order_id_"+x ).toString();
                        FirebaseFirestore.getInstance().collection( "ORDERS" ).document( order_id ).get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()){

                                    boolean is_paid=(boolean) task.getResult().get( "is_paid" );
                                    if(is_paid){

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
                adminOrderAdaptrer.notifyDataSetChanged();

            }
        } );

    }
}