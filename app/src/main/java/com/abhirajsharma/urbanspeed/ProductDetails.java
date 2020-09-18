package com.abhirajsharma.urbanspeed;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ProductDetails extends AppCompatActivity {

    private RecyclerView descriptionRecycler, reviewRecycler, relevant_recycler;
    private List<grocery_product_details_descrioption_Model> grocery_product_details_descrioption_modelList = new ArrayList<>();
    private List<ReviewModel> reviewModelList;
    private ReviewAdapter reviewAdapter;
    private GroceryProductAdapter groceryProductAdapter;
    private List<GroceryProductModel> groceryProductModel;
    private TextView out_of_stockText;
    String in_stock="";


    private ViewPager imageViewPager;
    private TabLayout viewPagerIndicator;
    private FloatingActionButton addtoWishlist,cartFAB;
    public static boolean ADDED_towishList = false;
    private List<String> productImages;


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


        String product_id=getIntent().getStringExtra( "product_id" );




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
        reviewModelList.add(new ReviewModel("user_name", "4", "20/11/20", "kjdshfugsdufybuydbduiybf", ""));
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
        groceryProductModel.add(new GroceryProductModel("jegf", "product", "%", "200", "3000", "4.1", "22", 22, "laiuihehdifiuh", ""));
        groceryProductModel.add(new GroceryProductModel("jegf", "product", "%", "200", "3000", "4.1", "22", 22, "laiuihehdifiuh", ""));
        groceryProductModel.add(new GroceryProductModel("jegf", "product", "%", "200", "3000", "4.1", "22", 22, "laiuihehdifiuh", ""));
        groceryProductModel.add(new GroceryProductModel("jegf", "product", "%", "200", "3000", "4.1", "22", 22, "laiuihehdifiuh", ""));
        groceryProductModel.add(new GroceryProductModel("jegf", "product", "%", "200", "3000", "4.1", "22", 22, "laiuihehdifiuh", ""));
        groceryProductModel.add(new GroceryProductModel("jegf", "product", "%", "200", "3000", "4.1", "22", 22, "laiuihehdifiuh", ""));


        productImages=new ArrayList<>(  );
        groceryProductAdapter = new GroceryProductAdapter(groceryProductModel);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        relevant_recycler.setLayoutManager(gridLayoutManager);
        relevant_recycler.setAdapter(groceryProductAdapter);

        FirebaseFirestore.getInstance( ).collection( "PRODUCTS" ).document( product_id ).get( )
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
                          //  loadingDialog.dismiss();
                        }
                    }
                } );


        viewPagerIndicator.setupWithViewPager( imageViewPager, true );














    }
}