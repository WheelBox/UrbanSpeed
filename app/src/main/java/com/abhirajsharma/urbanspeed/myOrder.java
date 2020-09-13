package com.abhirajsharma.urbanspeed;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abhirajsharma.urbanspeed.adapter.MyOrderAdapter;
import com.abhirajsharma.urbanspeed.model.MyOrderModel;

import java.util.ArrayList;
import java.util.List;

public class myOrder extends AppCompatActivity {


    RecyclerView orderRecyclere;
    Toolbar toolbar;
    private List<MyOrderModel> myOrderGroceryModelList;
    private MyOrderAdapter myOrderGroceryAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);

        orderRecyclere = findViewById(R.id.order_recycler);

        toolbar = findViewById(R.id.toolbar_grocery_Orders);
        toolbar.setTitle("My Orders");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        myOrderGroceryModelList = new ArrayList<>();
        myOrderGroceryModelList.add(new MyOrderModel("product_name", "0", "", "this is product!", "ajgdfuj", "akysgfbha", "3.4", "aydgfuaye", false, false, "233232"));
        myOrderGroceryModelList.add(new MyOrderModel("product_name", "0", "", "this is product!", "ajgdfuj", "akysgfbha", "3.4", "aydgfuaye", false, false, "233232"));
        myOrderGroceryModelList.add(new MyOrderModel("product_name", "0", "", "this is product!", "ajgdfuj", "akysgfbha", "3.4", "aydgfuaye", false, false, "233232"));
        myOrderGroceryModelList.add(new MyOrderModel("product_name", "0", "", "this is product!", "ajgdfuj", "akysgfbha", "3.4", "aydgfuaye", false, false, "233232"));
        myOrderGroceryModelList.add(new MyOrderModel("product_name", "0", "", "this is product!", "ajgdfuj", "akysgfbha", "3.4", "aydgfuaye", false, false, "233232"));
        myOrderGroceryModelList.add(new MyOrderModel("product_name", "0", "", "this is product!", "ajgdfuj", "akysgfbha", "3.4", "aydgfuaye", false, false, "233232"));
        myOrderGroceryModelList.add(new MyOrderModel("product_name", "0", "", "this is product!", "ajgdfuj", "akysgfbha", "3.4", "aydgfuaye", false, false, "233232"));
        myOrderGroceryModelList.add(new MyOrderModel("product_name", "0", "", "this is product!", "ajgdfuj", "akysgfbha", "3.4", "aydgfuaye", false, false, "233232"));
        myOrderGroceryModelList.add(new MyOrderModel("product_name", "0", "", "this is product!", "ajgdfuj", "akysgfbha", "3.4", "aydgfuaye", false, false, "233232"));
        myOrderGroceryModelList.add(new MyOrderModel("product_name", "0", "", "this is product!", "ajgdfuj", "akysgfbha", "3.4", "aydgfuaye", false, false, "233232"));


        myOrderGroceryAdapter = new MyOrderAdapter(myOrderGroceryModelList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        orderRecyclere.setLayoutManager(layoutManager);
        orderRecyclere.setAdapter(myOrderGroceryAdapter);
        myOrderGroceryAdapter.notifyDataSetChanged();


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}