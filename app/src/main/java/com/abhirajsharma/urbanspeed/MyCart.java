package com.abhirajsharma.urbanspeed;


import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.abhirajsharma.urbanspeed.adapter.grocery_cart_product_Adapter;
import com.abhirajsharma.urbanspeed.model.grocery_cart_product_Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MyCart extends AppCompatActivity {


    private RecyclerView cartProduct_Recycler;
    public static List<grocery_cart_product_Model> grocery_cart_product_modelList;
    public static grocery_cart_product_Adapter grocery_cart_product_adapter;
    private Dialog loadingDialog;
    private LinearLayout noItemLL, payment_layout;
    private ConstraintLayout address_layout,cart_activity;
    public  static CheckBox pickUpCheck;
    public static TextView totalSave, payAmount;


    public static TextView priceIncart, tax, disccount, grandTotal;
    private TextView payInCash, editAddress;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);


        toolbar = findViewById(R.id.toolbar);
        cartProduct_Recycler = findViewById(R.id.cart_product_recycler);
        noItemLL = findViewById( R.id.no_item_layout );
        payAmount = findViewById( R.id.grocery_cart_payAmount );
        cart_activity=findViewById( R.id.cart_activity );


        cart_activity.setVisibility( View.INVISIBLE );
        payment_layout = findViewById( R.id.PaymentLayout );
        totalSave = findViewById( R.id.grocery_cart_totalSave );
        priceIncart = findViewById( R.id.itemTotalPrice );
        tax = findViewById( R.id.taxChargesPrice );
        disccount = findViewById( R.id.discount_price );
        grandTotal = findViewById( R.id.grandTotalPrice );
        payInCash = findViewById( R.id.myCartGroceryPayinCashPayment );
        toolbar.setTitle("My Cart");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        int size = DBquaries.grocery_CartList_product_id.size( );

        loadingDialog = new Dialog( MyCart.this );
        loadingDialog.setContentView( R.layout.loading_progress_dialouge );
        loadingDialog.setCancelable( false );
        loadingDialog.getWindow( ).setLayout( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT );
        loadingDialog.show( );

        DBquaries.PRICE_IN_CART_GROCERY =0;
        DBquaries.TOTAL_SAVE=0;

        if (size == 0) {
            noItemLL.setVisibility( View.VISIBLE );
            cart_activity.setVisibility( View.VISIBLE );

            payment_layout.setVisibility( View.INVISIBLE );
            loadingDialog.dismiss( );
        }

        grocery_cart_product_modelList = new ArrayList<>();

        grocery_cart_product_adapter = new grocery_cart_product_Adapter(
                grocery_cart_product_modelList
        );

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        cartProduct_Recycler.setLayoutManager(linearLayoutManager);
        cartProduct_Recycler.setAdapter(grocery_cart_product_adapter);
        cartProduct_Recycler.stopScroll();
        cartProduct_Recycler.setNestedScrollingEnabled( false );

        for (int x = 0; x < size; x++) {


            final String id = DBquaries.grocery_CartList_product_id.get( x );
            final int finalX = x;
            FirebaseFirestore.getInstance().collection( "USERS" ).document( FirebaseAuth.getInstance( ).getUid( ) )
                    .collection( "USER_DATA" ).document( "MY_GROCERY_CARTITEMCOUNT" ).get( )
                    .addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful( )) {

                                final String count = task.getResult( ).get( id ).toString( );
                                DBquaries.grocery_CartList_product_count.add( count );

                                loadingDialog.show( );
                                FirebaseFirestore.getInstance().collection( "PRODUCTS" ).document( id ).get( )
                                        .addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful( )) {
                                                    DBquaries.PRICE_IN_CART_GROCERY = DBquaries.PRICE_IN_CART_GROCERY + Integer.parseInt( count ) * Integer.parseInt( task.getResult( ).get( "price" ).toString( ) );
                                                    DBquaries.TOTAL_SAVE = DBquaries.TOTAL_SAVE + Integer.parseInt( count ) * (-Integer.parseInt( task.getResult( ).get( "price" ).toString( ) ) + Integer.parseInt( task.getResult( ).get( "cut_price" ).toString( ) ));
                                                    priceIncart.setText( String.valueOf( DBquaries.PRICE_IN_CART_GROCERY ) );
                                                    totalSave.setText( "₹" + String.valueOf( DBquaries.TOTAL_SAVE ) );
                                                    tax.setText(String.valueOf( DBquaries.DELIVERY_CHARGES  )  );
                                                    grandTotal.setText( String.valueOf( DBquaries.PRICE_IN_CART_GROCERY+ DBquaries.DELIVERY_CHARGES ) );
                                                    payAmount.setText( "₹"+String.valueOf( DBquaries.PRICE_IN_CART_GROCERY+ DBquaries.DELIVERY_CHARGES ) );
                                                    if ((long) task.getResult( ).get( "in_stock" ) == 0) {
                                                        DBquaries.grocery_CartList_product_OutOfStock.add( id );
                                                    }

                                                    grocery_cart_product_modelList.add( new grocery_cart_product_Model(
                                                            task.getResult( ).get( "name" ).toString( ),
                                                            task.getResult( ).get( "description" ).toString( ),
                                                            task.getResult( ).get( "price" ).toString( ),
                                                            task.getResult( ).get( "cut_price" ).toString( ),
                                                            id,
                                                            count,
                                                            task.getResult( ).get( "offer" ).toString( ),
                                                            task.getResult( ).get( "image_01" ).toString( ),
                                                            (long) task.getResult( ).get( "in_stock" ),
                                                            finalX


                                                    ) );


                                                }



                                                cart_activity.setVisibility( View.VISIBLE );
                                                loadingDialog.dismiss( );
                                                grocery_cart_product_adapter.notifyDataSetChanged( );

                                            }
                                        } );


                            }
                        }
                    } );


        }

        grocery_cart_product_adapter.notifyDataSetChanged( );


    }

    @Override
    protected void onRestart() {
        super.onRestart( );
        finish();
        startActivity(getIntent());
    }
}