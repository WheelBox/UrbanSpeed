package com.abhirajsharma.urbanspeed;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abhirajsharma.urbanspeed.adapter.MyOrderAdapter;
import com.abhirajsharma.urbanspeed.model.MyOrderModel;

import java.util.ArrayList;
import java.util.List;

public class OrderDetails extends AppCompatActivity {

    private RecyclerView otherProduct_Recycler;
    private List<MyOrderModel> myOrderGroceryModelList;
    private MyOrderAdapter myOrderGroceryAdapter;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        otherProduct_Recycler = findViewById(R.id.order_details_other_product_recycler);
        toolbar = findViewById(R.id.toolbar_grocery_Orders_details);


        toolbar.setTitle("Order Details");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        myOrderGroceryModelList = new ArrayList<>();
        myOrderGroceryModelList.add(new MyOrderModel("product_name", "0", "", "this is product!", "ajgdfuj", "akysgfbha", "3.4", "aydgfuaye", false, false, "233232"));
        myOrderGroceryModelList.add(new MyOrderModel("product_name", "0", "", "this is product!", "ajgdfuj", "akysgfbha", "3.4", "aydgfuaye", false, false, "233232"));
        myOrderGroceryModelList.add(new MyOrderModel("product_name", "0", "", "this is product!", "ajgdfuj", "akysgfbha", "3.4", "aydgfuaye", false, false, "233232"));
        myOrderGroceryModelList.add(new MyOrderModel("product_name", "0", "", "this is product!", "ajgdfuj", "akysgfbha", "3.4", "aydgfuaye", false, false, "233232"));


        myOrderGroceryAdapter = new MyOrderAdapter(myOrderGroceryModelList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        otherProduct_Recycler.setLayoutManager(layoutManager);
        otherProduct_Recycler.setAdapter(myOrderGroceryAdapter);


    }
}