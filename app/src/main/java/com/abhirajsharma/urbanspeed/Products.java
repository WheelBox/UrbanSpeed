package com.abhirajsharma.urbanspeed;

<<<<<<< HEAD
=======
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

>>>>>>> master
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

<<<<<<< HEAD
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

=======
>>>>>>> master
import com.abhirajsharma.urbanspeed.adapter.GroceryProductAdapter;
import com.abhirajsharma.urbanspeed.model.GroceryProductModel;

import java.util.ArrayList;
import java.util.List;

public class Products extends AppCompatActivity {

<<<<<<< HEAD
    public static GroceryProductAdapter groceryProductAdapter;
    private Toolbar toolbar;
    private RecyclerView product_recycler;
    private List<GroceryProductModel> groceryProductModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        product_recycler = findViewById(R.id.grocery_product_recycler);
        toolbar = findViewById(R.id.toolbar_grocery_product);

        toolbar.setTitle("Products");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        groceryProductModel = new ArrayList<>();
        groceryProductModel.add(new GroceryProductModel("jegf", "product", "%", "200", "3000", "4.1", "22", 22, "laiuihehdifiuh", ""));
        groceryProductModel.add(new GroceryProductModel("jegf", "product", "%", "200", "3000", "4.1", "22", 22, "laiuihehdifiuh", ""));
        groceryProductModel.add(new GroceryProductModel("jegf", "product", "%", "200", "3000", "4.1", "22", 22, "laiuihehdifiuh", ""));
        groceryProductModel.add(new GroceryProductModel("jegf", "product", "%", "200", "3000", "4.1", "22", 22, "laiuihehdifiuh", ""));
        groceryProductModel.add(new GroceryProductModel("jegf", "product", "%", "200", "3000", "4.1", "22", 22, "laiuihehdifiuh", ""));
        groceryProductModel.add(new GroceryProductModel("jegf", "product", "%", "200", "3000", "4.1", "22", 22, "laiuihehdifiuh", ""));
        groceryProductModel.add(new GroceryProductModel("jegf", "product", "%", "200", "3000", "4.1", "22", 22, "laiuihehdifiuh", ""));
        groceryProductModel.add(new GroceryProductModel("jegf", "product", "%", "200", "3000", "4.1", "22", 22, "laiuihehdifiuh", ""));
        groceryProductModel.add(new GroceryProductModel("jegf", "product", "%", "200", "3000", "4.1", "22", 22, "laiuihehdifiuh", ""));
        groceryProductModel.add(new GroceryProductModel("jegf", "product", "%", "200", "3000", "4.1", "22", 22, "laiuihehdifiuh", ""));
        groceryProductModel.add(new GroceryProductModel("jegf", "product", "%", "200", "3000", "4.1", "22", 22, "laiuihehdifiuh", ""));
        groceryProductModel.add(new GroceryProductModel("jegf", "product", "%", "200", "3000", "4.1", "22", 22, "laiuihehdifiuh", ""));


        groceryProductAdapter = new GroceryProductAdapter(groceryProductModel);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        product_recycler.setLayoutManager(gridLayoutManager);

        product_recycler.setAdapter(groceryProductAdapter);
=======
    private Toolbar toolbar;
    private RecyclerView product_recycler;
    public static GroceryProductAdapter groceryProductAdapter;
    private List<GroceryProductModel> groceryProductModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_products );

        product_recycler = findViewById( R.id.grocery_product_recycler );
        toolbar = findViewById( R.id.toolbar_grocery_product );

        toolbar.setTitle( "Products" );
        setSupportActionBar( toolbar );
        toolbar.setTitleTextColor( Color.parseColor( "#FFFFFF" ) );
        getSupportActionBar( ).setDisplayShowHomeEnabled( true );
        getSupportActionBar( ).setDisplayHomeAsUpEnabled( true );



        groceryProductModel = new ArrayList<>( );
        groceryProductModel.add( new GroceryProductModel( "jegf","product","%","200","3000","4.1","22",22 ,"laiuihehdifiuh","") );
        groceryProductModel.add( new GroceryProductModel( "jegf","product","%","200","3000","4.1","22",22 ,"laiuihehdifiuh","") );
        groceryProductModel.add( new GroceryProductModel( "jegf","product","%","200","3000","4.1","22",22 ,"laiuihehdifiuh","") );
        groceryProductModel.add( new GroceryProductModel( "jegf","product","%","200","3000","4.1","22",22 ,"laiuihehdifiuh","") );
        groceryProductModel.add( new GroceryProductModel( "jegf","product","%","200","3000","4.1","22",22 ,"laiuihehdifiuh","") );
        groceryProductModel.add( new GroceryProductModel( "jegf","product","%","200","3000","4.1","22",22 ,"laiuihehdifiuh","") );
        groceryProductModel.add( new GroceryProductModel( "jegf","product","%","200","3000","4.1","22",22 ,"laiuihehdifiuh","") );
        groceryProductModel.add( new GroceryProductModel( "jegf","product","%","200","3000","4.1","22",22 ,"laiuihehdifiuh","") );
        groceryProductModel.add( new GroceryProductModel( "jegf","product","%","200","3000","4.1","22",22 ,"laiuihehdifiuh","") );
        groceryProductModel.add( new GroceryProductModel( "jegf","product","%","200","3000","4.1","22",22 ,"laiuihehdifiuh","") );
        groceryProductModel.add( new GroceryProductModel( "jegf","product","%","200","3000","4.1","22",22 ,"laiuihehdifiuh","") );
        groceryProductModel.add( new GroceryProductModel( "jegf","product","%","200","3000","4.1","22",22 ,"laiuihehdifiuh","") );


        groceryProductAdapter = new GroceryProductAdapter( groceryProductModel );
        GridLayoutManager gridLayoutManager = new GridLayoutManager( getApplicationContext( ), 2 );
        product_recycler.setLayoutManager( gridLayoutManager );

        product_recycler.setAdapter( groceryProductAdapter );
>>>>>>> master
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
<<<<<<< HEAD
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_and_cart_icon, menu);
=======
        MenuInflater inflater = getMenuInflater( );
        inflater.inflate( R.menu.search_and_cart_icon, menu );
>>>>>>> master
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

<<<<<<< HEAD
        int id = item.getItemId();

        if (item.getItemId() == android.R.id.home) {

            finish();
=======
        int id = item.getItemId( );

        if (item.getItemId( ) == android.R.id.home) {

            finish( );
>>>>>>> master
        }


        if (id == R.id.productcartMenu) {

<<<<<<< HEAD
            Intent intent = new Intent(Products.this, MyCart.class);
            startActivity(intent);
=======
           Intent intent = new Intent( Products.this, MyCart.class );
           startActivity( intent );
>>>>>>> master

        }
        if (id == R.id.productsearchMenu) {

<<<<<<< HEAD
            // Intent intent = new Intent( Product.this, Search.class );
            //startActivity( intent );
            finish();
=======
           // Intent intent = new Intent( Product.this, Search.class );
            //startActivity( intent );
            finish( );
>>>>>>> master

        }


<<<<<<< HEAD
        return super.onOptionsItemSelected(item);
=======
        return super.onOptionsItemSelected( item );
>>>>>>> master
    }
}
