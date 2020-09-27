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


import com.abhirajsharma.urbanspeed.adapter.GroceryProductAdapter;
import com.abhirajsharma.urbanspeed.model.GroceryProductModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class Products extends AppCompatActivity {

    public static GroceryProductAdapter groceryProductAdapter;
    private RecyclerView product_recycler;
    private List<GroceryProductModel> groceryProductModel;
    private Dialog loadingDialog;
    private LinearLayout search_LL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        product_recycler = findViewById(R.id.grocery_product_recycler);
        search_LL=findViewById( R.id.search_LL );


        loadingDialog= new Dialog( Products.this );
        loadingDialog.setContentView( R.layout.loading_progress_dialouge );
        loadingDialog.setCancelable( false );
        loadingDialog.getWindow().setLayout( ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT );
        loadingDialog.show();


        groceryProductModel = new ArrayList<>();

        product_recycler.setOnScrollChangeListener( new View.OnScrollChangeListener( ) {
            @Override
            public void onScrollChange(View view, int scrollx, int scrolly, int oldscrollx, int oldscrolly) {


                if(oldscrolly>scrolly){


                    search_LL.setVisibility( View.VISIBLE );


                }else {
                    search_LL.setVisibility( View.GONE );

                }




            }
        } );


        groceryProductAdapter = new GroceryProductAdapter(groceryProductModel);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation( RecyclerView.VERTICAL );
        product_recycler.setLayoutManager(linearLayoutManager);
        product_recycler.setAdapter(groceryProductAdapter);

        FirebaseFirestore.getInstance( ).collection( "PRODUCTS" ).orderBy( "name" ).get( )
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
                                        documentSnapshot.get( "description" ).toString( )
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
