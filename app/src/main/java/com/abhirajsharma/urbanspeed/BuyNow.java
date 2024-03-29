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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;

import com.abhirajsharma.urbanspeed.model.grocery_cart_product_Model;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BuyNow extends AppCompatActivity {

    private TextView name, description, price, offer, cutPrice,remove_txt;
    private ImageView image;
    private TextView addproduct, subproduct, product_count;
    private Toolbar toolbar;
    private static String otp = new DecimalFormat( "000000" ).format( new Random( ).nextInt( 999999 ) );


    private static boolean isPickUp=false;
    private ConstraintLayout buynow;
    private TextView priceIncart;
    private static TextView tax;
    long[] shop_orderListSieze={0};


    private static TextView grandTotal;
    private static TextView totalSave, payAmount;
    private final String CHANNEL_ID = "ai";

    private Button order_Btn;

    private long order_no[] = {0};

    private int count[] = {1};
    private int calculaePrice = 0, calculateSave = 0;

    private static int DELIVERY_CHARGES = 0;
    private int Pprice = 0, Pcutprice = 0;
    private static String product_id = "";
    private static String store_id = "";

    private CheckBox pickUpCheck;
    private Dialog loadingDialog;
    private ConstraintLayout address_layout;


    private static String mName = "", mPrice = "", mcutPrice = "", mdescription = "", moffer = "", mimage = "";
    private ImageView shopImage;
    private TextView shopName,shopDescription;
    private String ShopDESCRIPTION="";
    private String ShopNMAE="";
    private String ShopIMAGE="";

    ///address

    private TextView addresss_name, address_details, address_phn, edit_address, adderss_type;
    private String user_address_details = "";
    private String user_name = "";
    private long previous_position = 0;
    private String user_phn = "";
    private String user_address_type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_now);

        buynow = findViewById( R.id.buy_now_activity );

        addresss_name = findViewById( R.id.grocery_cart_address_name );
        address_details = findViewById( R.id.grocery_cart_address_hostel_room );
        address_phn = findViewById( R.id.grocery_cart_address_phone );
        edit_address = findViewById( R.id.grocery_cart_address_editTxt );
        adderss_type = findViewById( R.id.grocery_cart_address_type );
        address_layout = findViewById( R.id.address_layout );
        remove_txt=findViewById( R.id.grocery_cart_remove_product );


        shopImage=findViewById( R.id.cart_shop_image );
        shopName=findViewById( R.id.cart_shop_name);
        shopDescription=findViewById( R.id.cart_shop_description );

        priceIncart = findViewById( R.id.itemTotalPrice );
        tax = findViewById( R.id.taxChargesPrice );
        grandTotal = findViewById( R.id.grandTotalPrice );
        name = findViewById( R.id.grocery_cart_product_title );
        description = findViewById( R.id.grocery_cart_productDescription );
        price = findViewById( R.id.grocery_cart_productPrice );
        offer = findViewById( R.id.grocery_cart_product_offer );
        cutPrice = findViewById( R.id.grocery_cart_productcutprice );
        image = findViewById( R.id.grocery_cart_productImage );
        addproduct = findViewById( R.id.grocery_cart_noCountAdd );
        subproduct = findViewById( R.id.grocery_cart_noCountSubtract );
        product_count = findViewById( R.id.grocery_cart_noCount );
        totalSave = findViewById( R.id.grocery_cart_totalSave );
        shop_orderListSieze = getListsize( );

        payAmount = findViewById( R.id.grocery_cart_payAmount );
        order_Btn = findViewById( R.id.myCartGroceryPayinCashPayment );
        pickUpCheck = findViewById( R.id.pickUpCheck );
        toolbar = findViewById( R.id.toolbar_buy_now );
        loadingDialog = new Dialog( BuyNow.this );
        loadingDialog.setContentView( R.layout.loading_progress_dialouge );
        loadingDialog.setCancelable( false );
        loadingDialog.getWindow( ).setLayout( ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT );
        loadingDialog.show( );


        buynow.setVisibility( View.INVISIBLE );

        toolbar.setTitle( "Buy Now" );
        setSupportActionBar( toolbar );
        toolbar.setTitleTextColor( Color.parseColor( "#FFFFFF" ) );

        getSupportActionBar( ).setDisplayShowHomeEnabled( true );
        getSupportActionBar( ).setDisplayHomeAsUpEnabled( true );

        product_id = getIntent( ).getStringExtra( "product_id" );
        store_id = getIntent( ).getStringExtra( "store_id" );
        final int stock = Integer.parseInt( getIntent( ).getStringExtra( "stock" ) );

        remove_txt.setVisibility( View.GONE );

        FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getCurrentUser( ).getUid( ) ).get( )
                .addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        if (task.isSuccessful( )) {
                            user_address_details = task.getResult( ).get( "address_details" ).toString( );
                            user_name = task.getResult( ).get( "fullname" ).toString( );
                            previous_position = (long) task.getResult( ).get( "previous_position" );
                            user_phn = task.getResult( ).get( "phone" ).toString( );
                            user_address_type = task.getResult( ).get( "address_type" ).toString( );


                            if (user_address_details.isEmpty( )) {
                                address_layout.setVisibility( View.GONE );
                            }
                            addresss_name.setText( user_name );
                            address_phn.setText( user_phn );
                            address_details.setText( user_address_details );
                            adderss_type.setText( user_address_type );


                        }


                    }
                } );


        FirebaseFirestore.getInstance( ).collection( "STORES" ).document( store_id ).collection( "PRODUCTS" ).document( product_id ).get( ).addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful( )) {

                    String p = task.getResult( ).get( "price" ).toString( );
                    String cp = task.getResult( ).get( "cut_price" ).toString( );

                    mPrice = task.getResult( ).get( "price" ).toString( );
                    mcutPrice = task.getResult( ).get( "cut_price" ).toString( );
                    mdescription = task.getResult( ).get( "description_01" ).toString( );
                    mimage = task.getResult( ).get( "image_01" ).toString( );
                    mName = task.getResult( ).get( "name" ).toString( );
                    moffer = task.getResult( ).get( "offer" ).toString( );


                    name.setText( task.getResult( ).get( "name" ).toString( ) );
                    description.setText( task.getResult( ).get( "description" ).toString( ) );

                    price.setText( "₹" + p + "/-" );
                    Pprice = Integer.parseInt( p );
                    Pcutprice = Integer.parseInt( cp );
                    cutPrice.setText( "₹" + cp + "/-" );

                    offer.setText( task.getResult( ).get( "offer" ).toString( ) + " off" );

                    if (task.getResult( ).get( "offer" ).toString( ).equals( "0" )) {
                        cutPrice.setVisibility( View.GONE );
                        offer.setVisibility( View.GONE );
                    }
                    Glide.with( BuyNow.this ).load( task.getResult( ).get( "image_01" ).toString( ) ).into( image );
                    priceIncart.setText( p );
                    calculaePrice = calculaePrice + Integer.parseInt( p );
                    calculateSave = calculateSave + Integer.parseInt( cp ) - Integer.parseInt( p );
                    totalSave.setText( "₹" + String.valueOf( calculateSave ) );
                    tax.setText( String.valueOf( DBquaries.DELIVERY_CHARGES ) );
                    grandTotal.setText( String.valueOf( DBquaries.DELIVERY_CHARGES+Integer.parseInt( p ) ) );
                    payAmount.setText( "₹" + String.valueOf( DBquaries.DELIVERY_CHARGES+Integer.parseInt( p ) ) );


                    buynow.setVisibility( View.VISIBLE );
                    loadingDialog.dismiss( );


                }
            }
        } );


        edit_address.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( BuyNow.this, MyAddress.class );
                intent.putExtra( "previous_position", previous_position );
                startActivity( intent );


            }
        } );

        FirebaseFirestore.getInstance( ).collection( "STORES" ).document( DBquaries.store_id ).get( ).addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful( )) {

                    String name = task.getResult( ).get( "name" ).toString( );
                    ShopNMAE = name;
                    String imaeg = task.getResult( ).get( "image" ).toString( );
                    DBquaries.DELIVERY_CHARGES=Integer.parseInt( task.getResult( ).get( "delivery_charges" ).toString( ) );
                    ShopIMAGE = imaeg;
                    if (imaeg.isEmpty()){
                        shopImage.setImageResource( R.drawable.store_default );
                    }else {
                        Glide.with( getApplicationContext() ).load( imaeg ).into( shopImage );
                    }
                    String description = task.getResult( ).get( "category" ).toString( );
                    ShopDESCRIPTION = description;

                    shopName.setText( name );
                    shopDescription.setText( description );


                }


            }
        } );

        product_count.setText( String.valueOf( count[0] ) );
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

        addproduct.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                if (count[0] < stock) {
                    loadingDialog.show( );

                    count[0] = count[0] + 1;
                    product_count.setText( String.valueOf( count[0] ) );
                    subproduct.setVisibility( View.VISIBLE );
                    calculaePrice = calculaePrice + Pprice;
                    calculateSave = calculateSave + Pcutprice - Pprice;
                    tax.setText( String.valueOf( DBquaries.DELIVERY_CHARGES ) );
                    grandTotal.setText( String.valueOf( DBquaries.DELIVERY_CHARGES+calculaePrice ) );
                    payAmount.setText( "₹" + String.valueOf( DBquaries.DELIVERY_CHARGES+calculaePrice ) );

                    totalSave.setText( "₹" + String.valueOf( calculateSave ) );
                    priceIncart.setText( String.valueOf( calculaePrice ) );
                    loadingDialog.dismiss( );

                } else {
                    Toast.makeText( BuyNow.this, "Max in stock", Toast.LENGTH_SHORT ).show( );
                }

            }
        } );


        subproduct.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                loadingDialog.show( );
                if (count[0] > 1) {

                    count[0] = count[0] - 1;
                    product_count.setText( String.valueOf( count[0] ) );
                    calculaePrice = calculaePrice - Pprice;
                    calculateSave = calculateSave - Pcutprice + Pprice;
                    priceIncart.setText( String.valueOf( calculaePrice ) );
                    tax.setText( String.valueOf( DBquaries.DELIVERY_CHARGES ) );
                    grandTotal.setText( String.valueOf( DBquaries.DELIVERY_CHARGES+calculaePrice ) );
                    payAmount.setText( "₹" + String.valueOf( DBquaries.DELIVERY_CHARGES+calculaePrice ) );
                    totalSave.setText( "₹" + String.valueOf( calculateSave ) );
                    loadingDialog.dismiss( );
                } else {
                    subproduct.setVisibility( View.GONE );
                    loadingDialog.dismiss( );

                }
            }
        } );



        order_Btn.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {

                if (address_layout.getVisibility( ) == View.GONE) {

                    Intent intent = new Intent( BuyNow.this, MyAddress.class );
                    intent.putExtra( "position", 0 );
                    startActivity( intent );

                } else {


                        loadingDialog.show( );
                        order_no[0] = order_no[0] + 1;
                        Date dNow = new Date( );
                        SimpleDateFormat ft = new SimpleDateFormat( "yyMMddhhmmssMs" );
                        String datetime = ft.format( dNow );
                        String Order_Id = datetime + order_no[0];

                        addOrderDetails( Order_Id, "POD", false, stock );




                }


            }
        } );
    }

    private void addOrderDetails(final String order_id, String Payment_Mode, boolean Payment_Status, final int Stock) {

        Map<String, Object> OrderDetails = new HashMap<>( );
        OrderDetails.put( "name", mName );
        OrderDetails.put( "product_id", product_id );
        OrderDetails.put( "price", mPrice );
        OrderDetails.put( "cut_price", mcutPrice );
        OrderDetails.put( "offer", moffer );
        OrderDetails.put( "description", mdescription );
        OrderDetails.put( "image", mimage );
        OrderDetails.put( "item_count", String.valueOf( count[0] ) );
        OrderDetails.put( "user_name", user_name );
        OrderDetails.put( "user_phn", user_phn );
        OrderDetails.put( "user_address_details", user_address_details );
        OrderDetails.put( "user_address_type", user_address_type );
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
        OrderDetails.put( "is_pickUp", pickUpCheck.isChecked( ) );


        FirebaseFirestore.getInstance( ).collection( "ORDERS" ).document( order_id ).collection( "ORDER_LIST" )
                .document( product_id ).set( OrderDetails ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful( )) {


                    Date dNow = new Date( );
                    SimpleDateFormat ft = new SimpleDateFormat( "yy/MM/dd hh:mm" );
                    String datetime = ft.format( dNow );
                    final Map<String, Object> details = new HashMap<>( );
                    details.put( "grand_total", String.valueOf( calculaePrice ) );
                    details.put( "id", order_id );
                    details.put( "otp", otp );
                    details.put( "tax", String.valueOf( DBquaries.DELIVERY_CHARGES ) );
                    details.put( "time", datetime );
                    details.put( "is_pickUp", pickUpCheck.isChecked( ) );
                    details.put( "is_paid", false );

                    FirebaseFirestore.getInstance( ).collection( "ORDERS" ).document( order_id ).set( details ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful( )) {

                            }
                        }
                    } );

                    final Map<String, Object> orderIdSHOP = new HashMap<>( );
                    orderIdSHOP.put( "order_id_" + shop_orderListSieze[0], order_id );
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
                            new NotificationCompat.Builder( BuyNow.this, CHANNEL_ID )
                                    .setSmallIcon( R.mipmap.ic_launcher )
                                    .setContentTitle( "Thank You" )
                                    .setContentText( "for placing order. Tap here for more details." );

                    Intent notificationIntent = new Intent( BuyNow.this, myOrder.class );
                    notificationIntent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );

                    PendingIntent contentIntent = PendingIntent.getActivity( BuyNow.this, 0, notificationIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT );
                    builder.setContentIntent( contentIntent );

                    NotificationManager manager = (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE );
                    manager.notify( 0, builder.build( ) );


                    final Map<String, Object> orderId = new HashMap<>( );
                    orderId.put( "order_id_" + DBquaries.grocery_OrderList.size( ), order_id );
                    orderId.put( "list_size", DBquaries.grocery_OrderList.size( ) + 1 );

                    FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getCurrentUser( ).getUid( ) )
                            .collection( "USER_DATA" ).document( "MY_GROCERY_ORDERS" ).update( orderId )
                            .addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful( )) {
                                        DBquaries.grocery_OrderList.add( order_id );
                                        Map<String, Object> Size = new HashMap<>( );
                                        Size.put( "list_size", 0 );
                                        Size.put( "store_id", "" );

                                        FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getCurrentUser( ).getUid( ) )
                                                .collection( "USER_DATA" ).document( "MY_GROCERY_CARTLIST" ).set( Size )
                                                .addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful( )) {
                                                            Map<String, Object> id = new HashMap<>( );
                                                            id.put( "user_name", FirebaseAuth.getInstance( ).getCurrentUser( ).getUid( ) );
                                                            id.put( "order_id", order_id );
                                                            id.put( "review", "" );
                                                            id.put( "rating", "0" );
                                                            FirebaseFirestore.getInstance( ).collection( "STORES" ).document( DBquaries.store_id ).collection( "PRODUCTS" ).document( product_id ).collection( "REVIEWS" ).document( order_id )
                                                                    .set( id ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    if (task.isSuccessful( )) {
                                                                        Map<String, Object> stockUpdate = new HashMap<>( );
                                                                        stockUpdate.put( "in_stock", Stock - count[0] );

                                                                        Toast.makeText( BuyNow.this, "Your Order Is Placed", Toast.LENGTH_SHORT ).show( );
                                                                        loadingDialog.dismiss();

                                                                        FirebaseFirestore.getInstance( ).collection( "STORES" ).document( store_id ).collection( "PRODUCTS" ).document( product_id ).update( stockUpdate ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                                                            @Override
                                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                                if (task.isSuccessful( )) {

                                                                                    loadingDialog.dismiss( );
                                                                                    Intent intent = new Intent( BuyNow.this, ShopActivity.class );
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
                            } );


                }
            }
        } );


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


}