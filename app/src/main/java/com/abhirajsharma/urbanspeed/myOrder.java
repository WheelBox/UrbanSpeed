package com.abhirajsharma.urbanspeed;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abhirajsharma.urbanspeed.adapter.MyOrderAdapter;
import com.abhirajsharma.urbanspeed.model.MyOrderModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class myOrder extends AppCompatActivity {


    RecyclerView orderRecyclere;
    Toolbar toolbar;
    private List<MyOrderModel> myOrderGroceryModelList;
    private MyOrderAdapter myOrderGroceryAdapter;
    private Dialog loadingDialog;
    private LinearLayout noItemLL;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        orderRecyclere = findViewById(R.id.order_recycler);

        toolbar = findViewById(R.id.toolbar_grocery_Orders);
        toolbar.setTitle("My Orders");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadingDialog = new Dialog( myOrder.this );
        loadingDialog.setContentView( R.layout.loading_progress_dialouge );
        loadingDialog.setCancelable( false );
        loadingDialog.getWindow( ).setLayout( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT );
        loadingDialog.show( );
        noItemLL = findViewById( R.id.no_item_layout );


        myOrderGroceryModelList = new ArrayList<>();


        myOrderGroceryAdapter = new MyOrderAdapter(myOrderGroceryModelList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        orderRecyclere.setLayoutManager(layoutManager);
        orderRecyclere.setAdapter(myOrderGroceryAdapter);
        FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getCurrentUser( ).getUid( ) )
                .collection( "USER_DATA" ).document( "MY_GROCERY_ORDERS" ).get( ).addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful( )) {

                    long size = (long) task.getResult( ).get( "list_size" );

                    if (size == 0) {
                        loadingDialog.dismiss( );
                        noItemLL.setVisibility( View.VISIBLE );

                    }
                    for (long x =0; x<size; x++) {
                        final String order_id = task.getResult( ).get( "order_id_"+x ).toString( );

                        FirebaseFirestore.getInstance( ).collection( "ORDERS" ).document( order_id ).collection( "ORDER_LIST" ).orderBy( "product_id" )
                                .get( ).addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful( )) {
                                    for (QueryDocumentSnapshot documentSnapshot : task.getResult( )) {

                                        myOrderGroceryModelList.add( new MyOrderModel (
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
                                                documentSnapshot.get( "otp" ).toString( )


                                        ) );


                                        loadingDialog.dismiss( );

                                    }
                                    myOrderGroceryAdapter.notifyDataSetChanged( );
                                }
                            }
                        } );


                    }
                    loadingDialog.dismiss( );


                }

            }
        } );


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}