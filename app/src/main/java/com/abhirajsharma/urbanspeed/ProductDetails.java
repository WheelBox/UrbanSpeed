package com.abhirajsharma.urbanspeed;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDetails extends AppCompatActivity {

    private RecyclerView descriptionRecycler, reviewRecycler;
    private List<grocery_product_details_descrioption_Model> grocery_product_details_descrioption_modelList = new ArrayList<>();
    private List<ReviewModel> reviewModelList;
    private ReviewAdapter reviewAdapter;
    private GroceryProductAdapter groceryProductAdapter;
    private List<GroceryProductModel> groceryProductModel;
    private TextView out_of_stockText;
    public static LinearLayout addtoCart,gotoCart,rating_ll;
    String in_stock="";

    private Dialog loadingDialog;

    private ViewPager imageViewPager;
    private TabLayout viewPagerIndicator;
    private FloatingActionButton addtoWishlist,cartFAB;
    public static boolean ADDED_towishList = false;
    private List<String> productImages=new ArrayList<>(  );
    String product_id="";
    String store_id="";
    String store_name="";
    private Button buy_now;

    /////productImage/nmae/price
    private TextView name, price, cutprice, offer, rating, reviewCount;
    /////productImage/nmae/price

///rating
    private ConstraintLayout product_details_rating_CL;
    private TextView star_1_count;
    private TextView star_2_count;
    private TextView star_3_count;
    private TextView star_4_count;
    private TextView star_5_count;
    private TextView avg_rating;
    private TextView totalstarCount;
    private TextView totalreviewCount;
    private ProgressBar progressBar1;
    private ProgressBar progressBar2;
    private ProgressBar progressBar3;
    private ProgressBar progressBar4;
    private ProgressBar progressBar5;
    final int[] one_star_count = {0};
    final int[] two_star_count = {0};
    final int[] five_star_count = {0};
    final int[] four_star_count = {0};
    final int[] three_star_count = {0};
    final int[] total_rating_count = {0};
    final int[] total_review_count = {0};
    final double[] avg_star = {0.00000};
///rating
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        addtoWishlist = findViewById( R.id.addtoWishlist );
        imageViewPager = findViewById( R.id.grocery_product_image_VP );
        viewPagerIndicator = findViewById( R.id.grocery_product_image_VP_indicator );

        descriptionRecycler = findViewById(R.id.description_recycler);
        reviewRecycler = findViewById(R.id.review_recycler);
        buy_now=findViewById( R.id.buy_now_groceryBtn );

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


        /////productRating

        reviewRecycler=findViewById( R.id.review_recycler );
        star_1_count = findViewById( R.id.review_1_star_count );
        star_2_count = findViewById( R.id.review_2_star_count );
        star_3_count = findViewById( R.id.review_3_star_count );
        star_4_count = findViewById( R.id.review_4_star_count );
        star_5_count = findViewById( R.id.review_5_star_count );
        progressBar1 = findViewById( R.id.progressBar1star );
        progressBar2 = findViewById( R.id.progressBar2star );
        progressBar3 = findViewById( R.id.progressBar3star );
        progressBar4 = findViewById( R.id.progressBar4star );
        progressBar5 = findViewById( R.id.progressBar5star );
        avg_rating = findViewById( R.id.review_avg_star );
        totalstarCount = findViewById( R.id.review_total_rating );
        totalreviewCount = findViewById( R.id.review_total_review_count );
        product_details_rating_CL=findViewById( R.id.product_details_rating_CL );






        /////productRating
        addtoCart=findViewById( R.id.addtoGroceryCary );
        gotoCart=findViewById( R.id.gotoGroceryCary );
        rating_ll=findViewById( R.id.linearLayout17 );


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

        buy_now.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( ProductDetails.this,BuyNow.class );
                intent.putExtra( "product_id",product_id );
                intent.putExtra( "store_id",store_id );

                intent.putExtra( "stock",in_stock );
                startActivity( intent );
            }
        } );

        gotoCart.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {

                    Intent intent=new Intent( ProductDetails.this,MyCart.class );
                    startActivity( intent );

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

        reviewAdapter = new ReviewAdapter(reviewModelList);
        LinearLayoutManager reviewlayoutManager = new LinearLayoutManager(this);
        reviewlayoutManager.setOrientation(RecyclerView.VERTICAL);
        reviewRecycler.setAdapter(reviewAdapter);
        reviewRecycler.setLayoutManager(reviewlayoutManager);
        reviewRecycler.setHasFixedSize(true);
        reviewRecycler.setNestedScrollingEnabled(false);

        FirebaseFirestore.getInstance().collection( "STORES" ).document( store_id ).collection( "PRODUCTS" ).document(product_id).collection( "REVIEWS" ).orderBy( "order_id", Query.Direction.DESCENDING )
                .get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot:task.getResult()){
                        String rating=documentSnapshot.get( "rating" ).toString();
                        String review=documentSnapshot.get( "review" ).toString();

                        if(!(review.length()==0)){
                            total_review_count[0]= total_review_count[0]+1;
                            totalreviewCount.setText( String.valueOf(  total_review_count[0] ) );
                            reviewModelList.add( new ReviewModel(
                                    documentSnapshot.get("user_name").toString(),
                                    rating,
                                    documentSnapshot.get("date").toString(),
                                    review,
                                    documentSnapshot.get("image").toString()
                            ) );
                            reviewAdapter.notifyDataSetChanged();
                        }
                        totalreviewCount.setText( String.valueOf(  total_review_count[0] ) );
                        totalstarCount.setText( String.valueOf( total_rating_count[0] ) );




                        if (rating.equals( "5" )) {
                            total_rating_count[0] = total_rating_count[0] + 1;
                            totalstarCount.setText( String.valueOf( total_rating_count[0] ) );
                            five_star_count[0] = five_star_count[0] + 1;
                            star_5_count.setText( String.valueOf( five_star_count[0] ) );
                        } else {
                            star_5_count.setText( String.valueOf( five_star_count[0] ) );
                        }
                        if (rating.equals( "4" )) {
                            total_rating_count[0] = total_rating_count[0] + 1;
                            totalstarCount.setText( String.valueOf( total_rating_count[0] ) );
                            four_star_count[0] = four_star_count[0] + 1;
                            star_4_count.setText( String.valueOf( four_star_count[0] ) );

                        } else {
                            star_4_count.setText( String.valueOf( four_star_count[0] ) );

                        }
                        if (rating.equals( "3" )) {
                            total_rating_count[0] = total_rating_count[0] + 1;
                            totalstarCount.setText( String.valueOf( total_rating_count[0] ) );
                            three_star_count[0] = three_star_count[0] + 1;
                            star_3_count.setText( String.valueOf( three_star_count[0] ) );

                        } else {
                            star_3_count.setText( String.valueOf( three_star_count[0] ) );
                        }
                        if (rating.equals( "2" )) {
                            total_rating_count[0] = total_rating_count[0] + 1;
                            totalstarCount.setText( String.valueOf( total_rating_count[0] ) );
                            two_star_count[0] = two_star_count[0] + 1;
                            star_2_count.setText( String.valueOf( two_star_count[0] ) );

                        } else {
                            star_2_count.setText( String.valueOf( two_star_count[0] ) );

                        }
                        if (rating.equals( "1" )) {
                            total_rating_count[0] = total_rating_count[0] + 1;
                            totalstarCount.setText( String.valueOf( total_rating_count[0] ) );
                            one_star_count[0] = one_star_count[0] + 1;
                            star_1_count.setText( String.valueOf( one_star_count[0] ) );
                        } else {
                            star_1_count.setText( String.valueOf( one_star_count[0] ) );

                        }


                        if(total_rating_count[0]!=0) {
                            product_details_rating_CL.setVisibility( View.VISIBLE );
                            reviewRecycler.setVisibility( View.VISIBLE );
                            reviewCount.setVisibility( View.VISIBLE );
                            rating_ll.setVisibility( View.VISIBLE );
                            avg_star[0] = (5.0 * five_star_count[0] + 4.0 * four_star_count[0] + 3.0 * three_star_count[0] + 2.0 * two_star_count[0] + one_star_count[0]) / total_rating_count[0];
                            avg_rating.setText( String.format( "%.1f", avg_star[0] ) );

                            Map< String,Object> Update_ratings= new HashMap< >( ) ;
                            Update_ratings.put( "rating",String.format( "%.1f", avg_star[0] ) );
                            Update_ratings.put( "review_count",String.valueOf( total_rating_count[0] ) );

                            FirebaseFirestore.getInstance().collection( "STORES" ).document( store_id ).collection( "PRODUCTS" ).document( product_id ).update( Update_ratings )
                                    .addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                            }
                                        }
                                    } );


                            progressBar1.setProgress( (one_star_count[0] * 100) / total_rating_count[0] );
                            progressBar2.setProgress( (two_star_count[0] * 100) / total_rating_count[0] );
                            progressBar3.setProgress( (three_star_count[0] * 100) / total_rating_count[0] );
                            progressBar4.setProgress( (four_star_count[0] * 100) / total_rating_count[0] );
                            progressBar5.setProgress( (five_star_count[0] * 100) / total_rating_count[0] );
                        }else {
                            product_details_rating_CL.setVisibility( View.GONE );
                            reviewRecycler.setVisibility( View.GONE );
                            reviewCount.setVisibility( View.GONE );
                            rating_ll.setVisibility( View.GONE );
                        }

                        if(total_review_count[0]>1){
                          //  allReviews.setVisibility( View.VISIBLE );
                        }

                    }
                    reviewAdapter.notifyDataSetChanged();
                }else {
                }

            }
        } );



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
                                reviewCount.setVisibility( View.GONE );
                                rating_ll.setVisibility( View.GONE );
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
        if(DBquaries.store_id.equals(store_id)||DBquaries.store_id.isEmpty()){
            addtoCart.setClickable( false );
            DBquaries.store_id=store_id;

            final Map<String, Object> updateListSize = new HashMap<>( );
            updateListSize.put( "list_size", (long) DBquaries.grocery_CartList_product_id.size( ) + 1 );

            Map<String, Object> product_Id = new HashMap<>( );
            product_Id.put( "id_" + (long) DBquaries.grocery_CartList_product_id.size( ), product_id );
            product_Id.put( "store_id", store_id );

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