package com.abhirajsharma.urbanspeed;


import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.abhirajsharma.urbanspeed.adapter.grocery_cart_product_Adapter;
import com.abhirajsharma.urbanspeed.model.grocery_cart_product_Model;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MyCart extends AppCompatActivity {


    private RecyclerView cartProduct_Recycler;
    public static List<grocery_cart_product_Model> grocery_cart_product_modelList;
    public static grocery_cart_product_Adapter grocery_cart_product_adapter;
    private Dialog loadingDialog;
    private LinearLayout noItemLL, payment_layout;
    private ConstraintLayout address_layout,cart_activity;
    public  static CheckBox pickUpCheck;
    private static boolean isPickUp=false;
    public static TextView totalSave, payAmount;
    public static TextView priceIncart, tax, disccount, grandTotal;
    private TextView  editAddress;
    private static String otp = new DecimalFormat( "000000" ).format( new Random( ).nextInt( 999999 ) );
    private final String CHANNEL_ID = "ai";
    private String ShopNMAE="";
    private String ShopIMAGE="";
    private String userNmae="";
    private String userADDRESS="";
    private String userAdderssType="";
    private String userPhone="";
    private String ShopDESCRIPTION="";

    long[] shop_orderListSieze={0};


    private ImageView shopImage;
    private TextView shopName,shopDescription;

    private Button payInCash;

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

        pickUpCheck=findViewById( R.id.pickUpCheck );


        address_layout=findViewById( R.id.address_layout );
        cart_activity.setVisibility( View.INVISIBLE );
        payment_layout = findViewById( R.id.PaymentLayout );
        totalSave = findViewById( R.id.grocery_cart_totalSave );
        priceIncart = findViewById( R.id.itemTotalPrice );
        tax = findViewById( R.id.taxChargesPrice );
        disccount = findViewById( R.id.discount_price );
        grandTotal = findViewById( R.id.grandTotalPrice );
        payInCash = findViewById( R.id.myCartGroceryPayinCashPayment );
        shopImage=findViewById( R.id.cart_shop_image );
        shopName=findViewById( R.id.cart_shop_name);
        shopDescription=findViewById( R.id.cart_shop_description );
        editAddress=findViewById( R.id.grocery_cart_address_editTxt );
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










        editAddress.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( MyCart.this,MyAddress.class);
                startActivity( intent );
            }
        } );

        if (size == 0) {
            noItemLL.setVisibility( View.VISIBLE );
            cart_activity.setVisibility( View.VISIBLE );

            payment_layout.setVisibility( View.INVISIBLE );
            loadingDialog.dismiss( );
        }else {



            FirebaseFirestore.getInstance().collection( "USERS" ).document( FirebaseAuth.getInstance().getCurrentUser().getUid() ).get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        userNmae=task.getResult().get( "fullname" ).toString();
                        userAdderssType=task.getResult().get( "address_type" ).toString();
                        userPhone=task.getResult().get( "phone" ).toString();
                        userADDRESS=task.getResult().get( "address_details" ).toString();

                        if (userADDRESS.isEmpty()) {
                            address_layout.setVisibility( View.GONE );
                        }

                    }
                }
            } );
            FirebaseFirestore.getInstance( ).collection( "STORES" ).document( DBquaries.store_id ).get( ).addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful( )) {

                        String name = task.getResult( ).get( "name" ).toString( );
                        ShopNMAE = name;
                        String imaeg = task.getResult( ).get( "image" ).toString( );
                        ShopIMAGE = imaeg;

                        String description = task.getResult( ).get( "category" ).toString( );
                        ShopDESCRIPTION = description;


                        Glide.with( MyCart.this ).load( imaeg ).into( shopImage );
                        shopName.setText( name );
                        shopDescription.setText( description );


                    }


                }
            } );
            grocery_cart_product_modelList = new ArrayList<>( );

            grocery_cart_product_adapter = new grocery_cart_product_Adapter(
                    grocery_cart_product_modelList
            );

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager( this );
            linearLayoutManager.setOrientation( RecyclerView.VERTICAL );
            cartProduct_Recycler.setLayoutManager( linearLayoutManager );
            cartProduct_Recycler.setAdapter( grocery_cart_product_adapter );
            cartProduct_Recycler.stopScroll( );
            cartProduct_Recycler.setNestedScrollingEnabled( false );
            for (int x = 0; x < size; x++) {


                final String id = DBquaries.grocery_CartList_product_id.get( x );
                final int finalX = x;
                FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getUid( ) )
                        .collection( "USER_DATA" ).document( "MY_GROCERY_CARTITEMCOUNT" ).get( )
                        .addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful( )) {
                                    final String count = task.getResult( ).get( id ).toString( );
                                    DBquaries.grocery_CartList_product_count.add( count );
                                    loadingDialog.show( );
                                    FirebaseFirestore.getInstance( ).collection( "STORES" ).document( DBquaries.store_id ).collection( "PRODUCTS" ).document( id ).get( )
                                            .addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful( )) {
                                                        DBquaries.PRICE_IN_CART_GROCERY = DBquaries.PRICE_IN_CART_GROCERY + Integer.parseInt( count ) * Integer.parseInt( task.getResult( ).get( "price" ).toString( ) );
                                                        DBquaries.TOTAL_SAVE = DBquaries.TOTAL_SAVE + Integer.parseInt( count ) * (-Integer.parseInt( task.getResult( ).get( "price" ).toString( ) ) + Integer.parseInt( task.getResult( ).get( "cut_price" ).toString( ) ));
                                                        priceIncart.setText( String.valueOf( DBquaries.PRICE_IN_CART_GROCERY ) );
                                                        totalSave.setText( "₹" + String.valueOf( DBquaries.TOTAL_SAVE ) );
                                                        tax.setText( String.valueOf( DBquaries.DELIVERY_CHARGES ) );
                                                        grandTotal.setText( String.valueOf( DBquaries.PRICE_IN_CART_GROCERY + DBquaries.DELIVERY_CHARGES ) );
                                                        payAmount.setText( "₹" + String.valueOf( DBquaries.PRICE_IN_CART_GROCERY + DBquaries.DELIVERY_CHARGES ) );
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
            shop_orderListSieze = getListsize( );
            grocery_cart_product_adapter.notifyDataSetChanged( );


            pickUpCheck.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener( ) {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if(pickUpCheck.isChecked()){
                        isPickUp=true;
                    }else {
                        isPickUp=false;
                    }
                }
            } );




            payInCash.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick(View view) {

                    if (address_layout.getVisibility( ) == View.GONE) {

                        Intent intent=new Intent( MyCart.this,MyAddress.class);
                        startActivity( intent );

                    }else {
                        Date dNow = new Date( );
                        SimpleDateFormat ft = new SimpleDateFormat( "yyMMddhhmmssMs" );
                        String datetime = ft.format( dNow );

                        String OId = "ON" + datetime + otp;

                        addOrderDetails( OId, "PID", false );
                    }




                }
            } );
        }

    }


    private void addOrderDetails(final String OrderId, String Payment_Mode, boolean Payment_Status) {

        String pid = "";
        int grandToatal = 0;
        for (grocery_cart_product_Model groceryCartProductModel : grocery_cart_product_modelList) {

            Map<String, Object> OrderDetails = new HashMap<>( );
            OrderDetails.put( "name", groceryCartProductModel.getName( ) );
            OrderDetails.put( "product_id", groceryCartProductModel.getProduct_id( ) );
            OrderDetails.put( "price", groceryCartProductModel.getPrice( ) );
            OrderDetails.put( "cut_price", groceryCartProductModel.getCutprice( ) );
            OrderDetails.put( "offer", groceryCartProductModel.getOffer( ) );
            OrderDetails.put( "description", groceryCartProductModel.getDescription( ) );
            OrderDetails.put( "image", groceryCartProductModel.getImage( ) );
            OrderDetails.put( "item_count", DBquaries.grocery_CartList_product_count.get( groceryCartProductModel.getInde( ) ) );
          ///need to be update
            OrderDetails.put( "user_name", userNmae );
            OrderDetails.put( "user_phn", userPhone );
            OrderDetails.put( "user_address_details", userADDRESS );
            OrderDetails.put( "user_address_type", userAdderssType );
            OrderDetails.put( "user_id", FirebaseAuth.getInstance( ).getCurrentUser( ).getUid( ) );
            OrderDetails.put( "payment_mode", Payment_Mode );
            OrderDetails.put( "delivery_status", false );
            OrderDetails.put( "is_cancled", false );
            OrderDetails.put( "delivery_time", "1" );
            OrderDetails.put( "payment_status", Payment_Status );
            OrderDetails.put( "review", "" );
            OrderDetails.put( "rating", "0" );
            OrderDetails.put( "otp", otp );
            OrderDetails.put( "store_id",DBquaries.store_id);
            OrderDetails.put( "store_name",ShopNMAE);
            OrderDetails.put( "store_image",ShopIMAGE);
            OrderDetails.put( "store_description",ShopDESCRIPTION);

            OrderDetails.put( "is_pickUp",isPickUp);
            pid = groceryCartProductModel.getProduct_id( );
            String stock = String.valueOf( groceryCartProductModel.getIn_stock( ) );
            grandToatal = grandToatal + Integer.parseInt( groceryCartProductModel.getPrice( ) );
            final Map<String, Object> Stock = new HashMap<>( );
            Stock.put( "in_stock", Integer.parseInt( stock ) - Integer.parseInt( DBquaries.grocery_CartList_product_count.get( groceryCartProductModel.getInde( ) ) ) );

            FirebaseFirestore.getInstance().collection( "STORES" ).document( DBquaries.store_id ).collection( "PRODUCTS" ).document( pid )
                    .update( Stock ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                }
            } );


            final String finalPid = pid;
            final int finalGrandToatal = grandToatal;
            FirebaseFirestore.getInstance( ).collection( "ORDERS" ).document( OrderId ).collection( "ORDER_LIST" )
                    .document( groceryCartProductModel.getProduct_id( ) ).set( OrderDetails ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful( )) {


                        Date dNow = new Date( );
                        SimpleDateFormat ft = new SimpleDateFormat( "yy/MM/dd hh:mm" );
                        String datetime = ft.format( dNow );
                        final Map<String, Object> details = new HashMap<>( );
                        details.put( "grand_total", DBquaries.PRICE_IN_CART_GROCERY );
                        details.put( "id", OrderId );
                        details.put( "otp", otp );
                        details.put( "tax", DBquaries.DELIVERY_CHARGES );
                        details.put( "time", datetime );
                        details.put( "is_paid", false );
                        details.put( "is_pickUp",true);
                        FirebaseFirestore.getInstance().collection( "ORDERS" ).document( OrderId ).set( details ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful( )) {

                                }
                            }
                        } );

                        final Map<String, Object> orderIdSHOP = new HashMap<>( );
                        orderIdSHOP.put( "order_id_" + shop_orderListSieze[0], OrderId );
                        orderIdSHOP.put( "list_size", shop_orderListSieze[0]+ 1 );

                        FirebaseFirestore.getInstance().collection( "STORES" ).document( DBquaries.store_id ).collection( "ORDERS" ).document( "order_list" )
                                .update( orderIdSHOP ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                }
                            }
                        } );




                        NotificationCompat.Builder builder =
                                new NotificationCompat.Builder( MyCart.this, CHANNEL_ID )
                                        .setSmallIcon( R.mipmap.ic_launcher )
                                        .setContentTitle( "Thank You" )
                                        .setContentText( "for placing order. Tap here for more details." );

                        Intent notificationIntent = new Intent( MyCart.this, myOrder.class );
                        notificationIntent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );

                        PendingIntent contentIntent = PendingIntent.getActivity( MyCart.this, 0, notificationIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT );
                        builder.setContentIntent( contentIntent );

                        NotificationManager manager = (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE );
                        manager.notify( 0, builder.build( ) );

                        Toast.makeText( MyCart.this, "Your Order Is Placed", Toast.LENGTH_SHORT ).show( );

                        final Map<String, Object> orderId = new HashMap<>( );
                        orderId.put( "order_id_" + DBquaries.grocery_OrderList.size( ), OrderId );
                        orderId.put( "list_size", DBquaries.grocery_OrderList.size( ) + 1 );


                        FirebaseFirestore.getInstance().collection( "USERS" ).document( FirebaseAuth.getInstance( ).getCurrentUser( ).getUid( ) )
                                .collection( "USER_DATA" ).document( "MY_GROCERY_ORDERS" ).update( orderId )
                                .addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful( )) {
                                            loadingDialog.dismiss( );
                                            DBquaries.grocery_OrderList.add( OrderId );
                                            Map<String, Object> Size = new HashMap<>( );
                                            Size.put( "list_size", 0 );
                                            Size.put( "store_id", "" );
                                            FirebaseFirestore.getInstance().collection( "USERS" ).document( FirebaseAuth.getInstance( ).getCurrentUser( ).getUid( ) )
                                                    .collection( "USER_DATA" ).document( "MY_GROCERY_CARTLIST" ).set( Size )
                                                    .addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful( )) {
                                                                Map<String, Object> id = new HashMap<>( );
                                                                id.put( "user_name", FirebaseAuth.getInstance( ).getCurrentUser( ).getUid( ) );
                                                                id.put( "order_id", OrderId );
                                                                id.put( "review", "" );
                                                                id.put( "rating", "0" );
                                                                FirebaseFirestore.getInstance( ).collection( "STORES" ).document( DBquaries.store_id ).collection( "PRODUCTS" ).document( finalPid ).collection( "REVIEWS" ).document( OrderId )
                                                                        .set( id ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {


                                                                    }
                                                                } );
                                                                DBquaries.grocery_CartList_product_id.clear( );
                                                                DBquaries.grocery_CartList_product_count.clear( );

                                                                Intent intent = new Intent( MyCart.this, ShopActivity.class );
                                                                startActivity( intent );
                                                            }

                                                        }
                                                    } );


                                        }
                                    }
                                } );


                    }
                }
            } );


        }


    }


    private long[] getListsize(){
        final long[] size = {0};
        FirebaseFirestore.getInstance().collection( "STORES" ).document( DBquaries.store_id ).collection( "ORDERS" ).document( "order_list" )
                .get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    size[0] =( (long)task.getResult().get( "list_size" ));
                }


            }
        } );
        return  size;

    }



    @Override
    protected void onRestart() {
        super.onRestart( );
        finish();
        startActivity(getIntent());
    }
}