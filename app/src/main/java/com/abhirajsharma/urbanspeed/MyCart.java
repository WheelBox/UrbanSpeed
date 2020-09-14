package com.abhirajsharma.urbanspeed;


import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.abhirajsharma.urbanspeed.adapter.grocery_cart_product_Adapter;
import com.abhirajsharma.urbanspeed.model.grocery_cart_product_Model;

import java.util.ArrayList;
import java.util.List;

public class MyCart extends AppCompatActivity {


    private RecyclerView cartProduct_Recycler;
    public static List<grocery_cart_product_Model> grocery_cart_product_modelList;
    public static grocery_cart_product_Adapter grocery_cart_product_adapter;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);


        toolbar = findViewById(R.id.toolbar);
        cartProduct_Recycler = findViewById(R.id.cart_product_recycler);

        toolbar.setTitle("My Cart");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        grocery_cart_product_modelList = new ArrayList<>();
        grocery_cart_product_modelList.add(new grocery_cart_product_Model("product_name", "product_description", "200", "300", "lsdihfa", "2", "30", "", 22, 12));

        grocery_cart_product_adapter = new grocery_cart_product_Adapter(
                grocery_cart_product_modelList
        );

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        cartProduct_Recycler.setLayoutManager(linearLayoutManager);
        cartProduct_Recycler.setAdapter(grocery_cart_product_adapter);


    }
}