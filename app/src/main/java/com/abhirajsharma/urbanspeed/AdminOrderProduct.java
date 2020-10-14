package com.abhirajsharma.urbanspeed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;

import com.abhirajsharma.urbanspeed.adapter.MyOrderAdapter;
import com.abhirajsharma.urbanspeed.model.MyOrderModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AdminOrderProduct extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView orderProductRecycler;
    private MyOrderAdapter myOrderGroceryAdapter;
    private List<MyOrderModel> myOrderGroceryModelList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_admin_order_product );


        toolbar=findViewById( R.id.adminorderProduct_toolbar );
        toolbar.setTitle("Orders Product");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor( Color.parseColor("#FFFFFF"));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);





        orderProductRecycler=findViewById( R.id.adminorderProductRecycler );

        myOrderGroceryModelList = new ArrayList<>( );
        myOrderGroceryAdapter = new MyOrderAdapter( myOrderGroceryModelList );
        LinearLayoutManager layoutManager = new LinearLayoutManager( this );
        layoutManager.setOrientation( RecyclerView.VERTICAL );
        orderProductRecycler.setLayoutManager( layoutManager );
        orderProductRecycler.setAdapter( myOrderGroceryAdapter );



        final String order_id=getIntent().getStringExtra( "order_id" );





        FirebaseFirestore.getInstance( ).collection( "ORDERS" ).document( order_id ).collection( "ORDER_LIST" ).orderBy( "product_id" )
                .get( ).addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful( )) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult( )) {

                        myOrderGroceryModelList.add( new MyOrderModel(
                                documentSnapshot.get( "name" ).toString( ),
                                documentSnapshot.get( "delivery_time" ).toString( ),
                                documentSnapshot.get( "image" ).toString( ),
                                documentSnapshot.get( "description" ).toString( ),
                                order_id,
                                documentSnapshot.get( "product_id" ).toString( ),
                                documentSnapshot.get( "rating" ).toString( ),
                                documentSnapshot.get( "review" ).toString( ),
                                (boolean) documentSnapshot.get( "is_cancled" ),
                                (boolean) documentSnapshot.get( "delivery_status" ),
                                documentSnapshot.get( "otp" ).toString( ),
                                documentSnapshot.get( "store_name" ).toString( ),
                                documentSnapshot.get( "store_id" ).toString( )

                        ) );


                    }


                    myOrderGroceryAdapter.notifyDataSetChanged( );
                }
            }
        } );
    }
}