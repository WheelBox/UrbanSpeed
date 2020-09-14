package com.abhirajsharma.urbanspeed;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;



import com.abhirajsharma.urbanspeed.adapter.GroceryProductAdapter;
import com.abhirajsharma.urbanspeed.model.GroceryProductModel;

import java.util.ArrayList;
import java.util.List;

public class Products extends AppCompatActivity {

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
