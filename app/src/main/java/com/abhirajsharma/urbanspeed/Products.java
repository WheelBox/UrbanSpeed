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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.abhirajsharma.urbanspeed.adapter.GroceryProductAdapter;
import com.abhirajsharma.urbanspeed.model.GroceryProductModel;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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
    private TextView name,address,description;
    private ImageView storeImage;
    ////admin
    private LinearLayout adminLL;
    private Button pendingOrders,confirmedOrders,addProduct;
    ////admin



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);


        product_recycler = findViewById(R.id.grocery_product_recycler);
        search_LL=findViewById( R.id.search_LL );
        name=findViewById( R.id.shop_name );
        address=findViewById( R.id.shop_address );
        description=findViewById( R.id.shop_description );
        storeImage=findViewById( R.id.store_image);

        ////admin
        adminLL=findViewById( R.id.admin_ll );
        pendingOrders=findViewById( R.id.admin_pending_order );
        confirmedOrders=findViewById( R.id.admin_confirmed_order );
        addProduct=findViewById( R.id.admin_add_product );

        ////admin



        loadingDialog= new Dialog( Products.this );
        loadingDialog.setContentView( R.layout.loading_progress_dialouge );
        loadingDialog.setCancelable( false );
        loadingDialog.getWindow().setLayout( ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT );
        loadingDialog.show();

        final String id = getIntent().getStringExtra( "store_id" );



      FirebaseFirestore.getInstance().collection( "STORES" ).document( id ).get().addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    name.setText( task.getResult().get( "name" ).toString() );
                    address.setText( task.getResult().get( "address" ).toString() );
                    description.setText( task.getResult().get( "category" ).toString() );
                    String image=task.getResult().get( "category" ).toString();
                    if (image.isEmpty()){
                        storeImage.setImageResource( R.drawable.store_default );
                    }else {
                        Glide.with( getApplicationContext() ).load( image ).into( storeImage );
                    }

                }
            }
        } );


        groceryProductModel = new ArrayList<>();


        search_LL.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {

                /*FirebaseFirestore.getInstance().collection( "STORES" ).document( id ).collection( "PRODUCTS").get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot:task.getResult()){
                                String Id=documentSnapshot.getId();
                                ArrayList<String> list=new ArrayList<>(  );
                                list.add( "one" );
                                list.add( "1" );
                                list.add( "two" );
                                list.add( "2" );
                                list.add( "three" );

                                Map<String,Object> map=new HashMap<>(  );
                                map.put( "tags",list );


                                FirebaseFirestore.getInstance().collection( "STORES" ).document( id ).collection( "PRODUCTS").document( Id ).update( map )
                                        .addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Toast.makeText( Products.this, "success", Toast.LENGTH_SHORT ).show( );
                                            }
                                        } );




                            }


                        }

                    }
                } );*/

                Intent intent=new Intent( Products.this,XSearch.class );
                intent.putExtra( "store_id" ,id );
                startActivity( intent );

            }
        } );

        FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get().
                addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot documentSnapshot=task.getResult();
                            if(documentSnapshot.exists()){
                                adminLL.setVisibility( View.GONE );
                            }else {
                                adminLL.setVisibility( View.VISIBLE );
                            }

                        }
                    }
                } );


        confirmedOrders.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( Products.this,AdminConfirmedOrders.class );
                intent.putExtra( "store_id",id );
                startActivity( intent );
            }
        } );
        pendingOrders.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( Products.this,AdminPendingOrders.class );
                intent.putExtra( "store_id",id );
                startActivity( intent );
            }
        } );

        addProduct.setOnClickListener( new View.OnClickListener( ) {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent( Products.this,AddProduct.class );
                intent.putExtra( "store_id",id );
                startActivity( intent );
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
                            DBquaries.allProductStore.clear();
                            for (QueryDocumentSnapshot documentSnapshot : task.getResult( )) {

                                String name=documentSnapshot.get( "name" ).toString( );
                                DBquaries.allProductStore.add( name );

                                groceryProductModel.add( new GroceryProductModel( documentSnapshot.get( "image_01" ).toString( )
                                        , name
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
