package com.abhirajsharma.urbanspeed;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.abhirajsharma.urbanspeed.adapter.GroceryProductAdapter;
import com.abhirajsharma.urbanspeed.adapter.ReviewAdapter;
import com.abhirajsharma.urbanspeed.adapter.grocery_product_details_descrioption_Adapter;
import com.abhirajsharma.urbanspeed.adapter.productDetailsViewPagerAdapter;
import com.abhirajsharma.urbanspeed.model.GroceryProductModel;
import com.abhirajsharma.urbanspeed.model.ReviewModel;
import com.abhirajsharma.urbanspeed.model.grocery_product_details_descrioption_Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDetails extends AppCompatActivity {

    private RecyclerView descriptionRecycler, reviewRecycler, relevant_recycler;
    private List<grocery_product_details_descrioption_Model> grocery_product_details_descrioption_modelList = new ArrayList<>();
    private List<ReviewModel> reviewModelList;
    private ReviewAdapter reviewAdapter;
    private GroceryProductAdapter groceryProductAdapter;
    private List<GroceryProductModel> groceryProductModel;
    private TextView out_of_stockText;
    public static LinearLayout addtoCart,gotoCart;
    String in_stock="";

    private Dialog loadingDialog;

    private ViewPager imageViewPager;
    private TabLayout viewPagerIndicator;
    private FloatingActionButton addtoWishlist,cartFAB;
    public static boolean ADDED_towishList = false;
    private List<String> productImages;
    String product_id="";
    String store_id="";
    String store_name="";

    /////productImage/nmae/price

    private TextView name, price, cutprice, offer, rating, reviewCount;



    /////productImage/nmae/price

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);



        addtoWishlist = findViewById( R.id.addtoWishlist );
        imageViewPager = findViewById( R.id.grocery_product_image_VP );
        viewPagerIndicator = findViewById( R.id.grocery_product_image_VP_indicator );

        descriptionRecycler = findViewById(R.id.description_recycler);
        reviewRecycler = findViewById(R.id.review_recycler);
        relevant_recycler = findViewById(R.id.relevant_product_recycler);

        /////productImage/nmae/price

        out_of_stockText=findViewById( R.id.grocery_product_details_OutstockText );
        name = findViewById( R.id.grocery_product_details_Name );
        price = findViewById( R.id.grocery_product_details_Price );
        cutprice = findViewById( R.id.grocery_product_details_CutPrice );
        rating = findViewById( R.id.grocery_product_details_Rating );
        reviewCount = findViewById( R.id.grocery_product_details_ReviewCount );
        offer = findViewById( R.id.grocery_product_details_Offer );
        cartFAB=findViewById( R.id.cartList );


        /////productImage/nmae/price
        addtoCart=findViewById( R.id.addtoGroceryCary );
        gotoCart=findViewById( R.id.gotoGroceryCary );



        loadingDialog= new Dialog( ProductDetails.this );
        loadingDialog.setContentView( R.layout.loading_progress_dialouge );
        loadingDialog.setCancelable( false );
        loadingDialog.getWindow().setLayout( ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT );
        loadingDialog.show();


        product_id=getIntent().getStringExtra( "product_id" );
        store_id=getIntent().getStringExtra( "store_id" );


        if(DBquaries.grocery_CartList_product_id.contains( product_id )){

            addtoCart.setVisibility( View.GONE );
            gotoCart.setVisibility( View.VISIBLE );
            cartFAB.setSupportImageTintList( ColorStateList.valueOf( Color.parseColor( "#39559e" ) ) );

        }else {
            cartFAB.setSupportImageTintList( ColorStateList.valueOf( Color.parseColor( "#696969" ) ) );

        }

        gotoCart.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {

                if(DBquaries.grocery_CartList_product_id.contains( product_id )){


                }else {
                    cartFAB.setSupportImageTintList( ColorStateList.valueOf( Color.parseColor( "#696969" ) ) );
                    Intent intent=new Intent( ProductDetails.this,MyCart.class );
                    startActivity( intent );
                }



            }
        } );


        cartFAB.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( ProductDetails.this,MyCart.class );
                startActivity( intent );
            }
        } );






        final grocery_product_details_descrioption_Adapter grocery_product_details_descrioption_adapter = new grocery_product_details_descrioption_Adapter(grocery_product_details_descrioption_modelList);


        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        layoutManager.setOrientation(RecyclerView.VERTICAL);
        descriptionRecycler.setAdapter(grocery_product_details_descrioption_adapter);
        descriptionRecycler.setLayoutManager(layoutManager);
        descriptionRecycler.setHasFixedSize(true);
        descriptionRecycler.setNestedScrollingEnabled(false);


        reviewModelList = new ArrayList<>();
        reviewModelList.add(new ReviewModel("user_name", "4", "20/11/20", "kjdshfugsdufybuydbduiybf", ""));
        reviewModelList.add(new ReviewModel("user_name", "4", "20/11/20", "kjdshfugsdufybuydbduiybf", ""));
        reviewModelList.add(new ReviewModel("user_name", "4", "20/11/20", "kjdshfugsdufybuydbduiybf", ""));


        reviewAdapter = new ReviewAdapter(reviewModelList);
        LinearLayoutManager reviewlayoutManager = new LinearLayoutManager(this);
        reviewlayoutManager.setOrientation(RecyclerView.VERTICAL);
        reviewRecycler.setAdapter(reviewAdapter);
        reviewRecycler.setLayoutManager(reviewlayoutManager);
        reviewRecycler.setHasFixedSize(true);
        reviewRecycler.setNestedScrollingEnabled(false);

        groceryProductModel = new ArrayList<>();
        groceryProductModel.add(new GroceryProductModel("jegf", "product", "%", "200", "3000", "4.1", "22", 22, "laiuihehdifiuh", "","this is deascription",store_id));
        groceryProductModel.add(new GroceryProductModel("jegf", "product", "%", "200", "3000", "4.1", "22", 22, "laiuihehdifiuh", "","this is deascription",store_id));


        productImages=new ArrayList<>(  );
        groceryProductAdapter = new GroceryProductAdapter(groceryProductModel);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation( RecyclerView.VERTICAL );
        relevant_recycler.setLayoutManager( linearLayoutManager );
        relevant_recycler.setAdapter(groceryProductAdapter);


        FirebaseFirestore.getInstance().collection( "STORES" ).document( store_id ).collection( "PRODUCTS" ).document( product_id ).get( )
                .addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful( )) {
                            in_stock= String.valueOf((long) task.getResult( ).get( "in_stock" ));
                            long no_of_images = (long) task.getResult( ).get( "no_of_image" );
                            long no_of_description = (long) task.getResult( ).get( "no_of_description" );
                            if(no_of_description>4){
                               // viewAllDescription.setVisibility( View.VISIBLE );
                            }else {
                               // viewAllDescription.setVisibility( View.GONE );
                            }
                            for (long x = 1; x < no_of_images + 1; x++) {

                                productImages.add( task.getResult( ).get( "image_0" + x ).toString( ) );
                            }
                            productDetailsViewPagerAdapter productDetailsViewPagerAdapter = new productDetailsViewPagerAdapter( productImages );
                            imageViewPager.setAdapter( productDetailsViewPagerAdapter );

                            for (long x = 1; x < no_of_description + 1; x++) {

                                grocery_product_details_descrioption_modelList.add( new grocery_product_details_descrioption_Model( task.getResult( ).get( "description_0" + x ).toString( ) ) );
                            }

                            name.setText( task.getResult( ).get( "name" ).toString( ) );
                            price.setText( "₹" + task.getResult( ).get( "price" ).toString( ) + "/-" );

                            if(in_stock.equals( "0" )){
                                out_of_stockText.setVisibility( View.VISIBLE );
                            }else {
                                out_of_stockText.setVisibility( View.GONE );

                            }
                            if(task.getResult( ).get( "offer" ).toString( ).equals( "0" )){

                                cutprice.setVisibility( View.GONE );
                                offer.setVisibility( View.GONE );
                            }
                            if(task.getResult( ).get( "rating" ).toString( ) .length()==1){
                               // rating_LL.setVisibility( View.GONE );
                                //review_layout.setVisibility( View.GONE );
                                reviewCount.setVisibility( View.GONE );
                            }
                            cutprice.setText( "₹" + task.getResult( ).get( "cut_price" ).toString( ) + "/-" );
                            offer.setText( task.getResult( ).get( "offer" ).toString( ) + " off " );
                            reviewCount.setText( "(" + task.getResult( ).get( "review_count" ).toString( ) + ")" );
                            rating.setText( task.getResult( ).get( "rating" ).toString( ) );
                            grocery_product_details_descrioption_adapter.notifyDataSetChanged( );
                          //  product_details_CL.setVisibility( View.VISIBLE );
                            loadingDialog.dismiss();
                        }
                    }
                } );


        viewPagerIndicator.setupWithViewPager( imageViewPager, true );


        addtoCart.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                AddtoCart();


            }
        } );





    }
    @Override
    protected void onRestart() {
        super.onRestart( );
        finish();
        startActivity(getIntent());
    }


    private void AddtoCart(){
        if(DBquaries.store_id.equals(store_id)){
            addtoCart.setClickable( false );

            final Map<String, Object> updateListSize = new HashMap<>( );
            updateListSize.put( "list_size", (long) DBquaries.grocery_CartList_product_id.size( ) + 1 );

            Map<String, Object> product_Id = new HashMap<>( );
            product_Id.put( "id_" + (long) DBquaries.grocery_CartList_product_id.size( ), product_id );

            FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getUid( ) )
                    .collection( "USER_DATA" ).document( "MY_GROCERY_CARTLIST" ).
                    update( product_Id ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getUid( ) )
                                .collection( "USER_DATA" ).document( "MY_GROCERY_CARTLIST" ).
                                update( updateListSize ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    DBquaries.grocery_CartList_product_id.add( product_id );
                                    addtoCart.setVisibility( View.GONE );
                                    addtoCart.setClickable( true );
                                    cartFAB.setSupportImageTintList( ColorStateList.valueOf( Color.parseColor( "#39559e" ) ) );

                                    gotoCart.setVisibility( View.VISIBLE );
                                    Toast.makeText( ProductDetails.this, "Added to cart", Toast.LENGTH_SHORT ).show( );



                                    loadingDialog.dismiss();


                                    Map<String, Object> Count = new HashMap<>( );
                                    Count.put( product_id,1 );

                                    FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getUid( ) )
                                            .collection( "USER_DATA" ).document( "MY_GROCERY_CARTITEMCOUNT" ).
                                            update( Count ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                        }
                                    } );

                                }

                            }
                        } );

                    }
                }
            } );


        }else {

            final Dialog continuue_dialog=new Dialog( ProductDetails.this );
            continuue_dialog.setContentView( R.layout.cart_alert );
            continuue_dialog.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT );
            continuue_dialog.setCancelable( true );
            final Button btn=continuue_dialog.findViewById( R.id.update_button );
            continuue_dialog.show();
            btn.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick(View view) {
                    continuue_dialog.dismiss();
                    addtoCart.setClickable( false );

                    DBquaries.grocery_CartList_product_id.clear();
                    DBquaries.grocery_CartList_product_count.clear( );


                    Map<String, Object> product_Id = new HashMap<>( );
                    DBquaries.store_id=store_id;
                    product_Id.put( "id_" + (long) DBquaries.grocery_CartList_product_id.size( ), product_id );
                    product_Id.put( "list_size", (long) DBquaries.grocery_CartList_product_id.size( ) + 1 );

                    product_Id.put( "store_id", store_id );



                    FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getUid( ) )
                            .collection( "USER_DATA" ).document( "MY_GROCERY_CARTLIST" ).
                            set( product_Id ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                DBquaries.grocery_CartList_product_id.add( product_id );
                                addtoCart.setVisibility( View.GONE );
                                addtoCart.setClickable( true );
                                cartFAB.setSupportImageTintList( ColorStateList.valueOf( Color.parseColor( "#39559e" ) ) );

                                gotoCart.setVisibility( View.VISIBLE );
                                Toast.makeText( ProductDetails.this, "Added to cart", Toast.LENGTH_SHORT ).show( );



                                loadingDialog.dismiss();


                                Map<String, Object> Count = new HashMap<>( );
                                Count.put( product_id,1 );

                                FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getUid( ) )
                                        .collection( "USER_DATA" ).document( "MY_GROCERY_CARTITEMCOUNT" ).
                                        update( Count ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                    }
                                } );

                            }

                        }
                    } );
                }
            } );











        }







    }
}