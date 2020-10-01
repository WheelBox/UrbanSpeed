package com.abhirajsharma.urbanspeed;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.abhirajsharma.urbanspeed.adapter.GroceryProductAdapter;
import com.abhirajsharma.urbanspeed.model.GroceryProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Products extends AppCompatActivity {

    public static GroceryProductAdapter groceryProductAdapter;
    private RecyclerView product_recycler;
    private List<GroceryProductModel> groceryProductModel;
    private Dialog loadingDialog;
    private LinearLayout search_LL;
    private TextView name,rating,address,description;
    int count=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        product_recycler = findViewById(R.id.grocery_product_recycler);
        search_LL=findViewById( R.id.search_LL );
        name=findViewById( R.id.shop_name );
        rating=findViewById( R.id.shop_rating );
        address=findViewById( R.id.shop_address );
        description=findViewById( R.id.shop_description );


        loadingDialog= new Dialog( Products.this );
        loadingDialog.setContentView( R.layout.loading_progress_dialouge );
        loadingDialog.setCancelable( false );
        loadingDialog.getWindow().setLayout( ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT );
        loadingDialog.show();

        final String id = getIntent().getStringExtra( "store_id" );
        Toast.makeText( this, id, Toast.LENGTH_SHORT ).show( );


      FirebaseFirestore.getInstance().collection( "STORES" ).document( id ).get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    name.setText( task.getResult().get( "name" ).toString() );
                    address.setText( task.getResult().get( "address" ).toString() );
                    description.setText( task.getResult().get( "category" ).toString() );
                    rating.setText( task.getResult().get( "rating" ).toString() );


                }
            }
        } );


        groceryProductModel = new ArrayList<>();


        search_LL.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {

                /*
                DocumentReference ref = FirebaseFirestore.getInstance().collection("STORES").document( "vGD5LbcLYT7mOaT6NRGv" ).collection( "PRODUCTS" ).document(  );
                String product_id = ref.getId();


                Map<String,Object> AddProductDetails=new HashMap<>(  );
                AddProductDetails.put( "name","Product Name "+count );
                AddProductDetails.put( "price",String.valueOf( 100*count ) );
                AddProductDetails.put( "rating",String.valueOf( 2.3 ) );
                AddProductDetails.put( "review_count","0" );
                AddProductDetails.put( "in_stock",10*count);
                AddProductDetails.put( "no_of_description",3 );
                AddProductDetails.put( "no_of_image",2 );
                AddProductDetails.put( "offer","30% off" );
                AddProductDetails.put( "image_01","https://firebasestorage.googleapis.com/v0/b/gsstore.appspot.com/o/PRODUCTS%2Fdetol_liquid_500ml_1.jpg?alt=media&token=b560d1d7-37c9-45d8-871c-9b2d3f721e71" );
                AddProductDetails.put( "image_02","https://firebasestorage.googleapis.com/v0/b/urban-speed-d20cb.appspot.com/o/laddu_sweet_delicacy_dessert_indian_treat_bowl-512.png?alt=media&token=f5529781-b8c7-42a2-b725-3b5776cd6abd" );
                AddProductDetails.put( "description_01","type,this is description 1" );
                AddProductDetails.put( "description_02","type,this is description 2" );
                AddProductDetails.put( "description_03","type,this is description 3" );
                AddProductDetails.put( "description","description of product" );

                FirebaseFirestore.getInstance().collection( "STORES" ).document( "vGD5LbcLYT7mOaT6NRGv" ).collection( "PRODUCTS" ).document( product_id )
                        .( AddProductDetails ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            count++;

                        }
                    }
                } );

*/
            }
        } );



        product_recycler.setOnScrollChangeListener( new View.OnScrollChangeListener( ) {
            @Override
            public void onScrollChange(View view, int scrollx, int scrolly, int oldscrollx, int oldscrolly) {
                if(oldscrolly>scrolly){
                    search_LL.setVisibility( View.VISIBLE );
                }else {
                    search_LL.setVisibility( View.VISIBLE );
                }




            }
        } );


        groceryProductAdapter = new GroceryProductAdapter(groceryProductModel);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation( RecyclerView.VERTICAL );
        product_recycler.setLayoutManager(linearLayoutManager);
        product_recycler.setAdapter(groceryProductAdapter);

        FirebaseFirestore.getInstance().collection( "STORES" ).document( id ).collection( "PRODUCTS" ).orderBy( "name" ).get( )
                .addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful( )) {
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult( )) {

                                groceryProductModel.add( new GroceryProductModel( documentSnapshot.get( "image_01" ).toString( )
                                        , documentSnapshot.get( "name" ).toString( )
                                        , documentSnapshot.get( "cut_price" ).toString( )
                                        , documentSnapshot.get( "offer" ).toString( )
                                        , documentSnapshot.get( "price" ).toString( )
                                        , documentSnapshot.get( "rating" ).toString( )
                                        , documentSnapshot.get( "review_count" ).toString( )
                                        , (long) documentSnapshot.get( "in_stock" )
                                        , documentSnapshot.getId( )
                                        ,"kjsdbbf",
                                        documentSnapshot.get( "description" ).toString( ),
                                        id
                                        ) );


                            }                                loadingDialog.dismiss();

                            groceryProductAdapter.notifyDataSetChanged( );

                        }

                    }
                } );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_and_cart_icon, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        int id = item.getItemId();

        if (item.getItemId() == android.R.id.home) {

            finish();

        }


        if (id == R.id.productcartMenu) {


            Intent intent = new Intent(Products.this, MyCart.class);
            startActivity(intent);


        }
        if (id == R.id.productsearchMenu) {

            // Intent intent = new Intent( Product.this, Search.class );
            //startActivity( intent );
            finish();


        }



        return super.onOptionsItemSelected(item);

    }
}
