package com.abhirajsharma.urbanspeed;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abhirajsharma.urbanspeed.adapter.GroceryProductAdapter;
import com.abhirajsharma.urbanspeed.adapter.ReviewAdapter;
import com.abhirajsharma.urbanspeed.adapter.grocery_product_details_descrioption_Adapter;
import com.abhirajsharma.urbanspeed.model.GroceryProductModel;
import com.abhirajsharma.urbanspeed.model.ReviewModel;
import com.abhirajsharma.urbanspeed.model.grocery_product_details_descrioption_Model;

import java.util.ArrayList;
import java.util.List;

public class ProductDetails extends AppCompatActivity {

    private RecyclerView descriptionRecycler, reviewRecycler, relevant_recycler;
    private List<grocery_product_details_descrioption_Model> grocery_product_details_descrioption_modelList = new ArrayList<>();
    private List<ReviewModel> reviewModelList;
    private ReviewAdapter reviewAdapter;
    private GroceryProductAdapter groceryProductAdapter;
    private List<GroceryProductModel> groceryProductModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);


        descriptionRecycler = findViewById(R.id.description_recycler);
        reviewRecycler = findViewById(R.id.review_recycler);
        relevant_recycler = findViewById(R.id.relevant_product_recycler);


        grocery_product_details_descrioption_modelList.add(new grocery_product_details_descrioption_Model("category,details"));
        grocery_product_details_descrioption_modelList.add(new grocery_product_details_descrioption_Model("category,details"));
        grocery_product_details_descrioption_modelList.add(new grocery_product_details_descrioption_Model("category,details"));
        grocery_product_details_descrioption_modelList.add(new grocery_product_details_descrioption_Model("category,details"));
        grocery_product_details_descrioption_modelList.add(new grocery_product_details_descrioption_Model("category,details"));
        grocery_product_details_descrioption_modelList.add(new grocery_product_details_descrioption_Model("category,details"));
        grocery_product_details_descrioption_modelList.add(new grocery_product_details_descrioption_Model("category,details"));
        grocery_product_details_descrioption_modelList.add(new grocery_product_details_descrioption_Model("category,details"));
        grocery_product_details_descrioption_modelList.add(new grocery_product_details_descrioption_Model("category,details"));
        grocery_product_details_descrioption_modelList.add(new grocery_product_details_descrioption_Model("category,details"));


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


        groceryProductAdapter = new GroceryProductAdapter(groceryProductModel);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        relevant_recycler.setLayoutManager(gridLayoutManager);
        relevant_recycler.setAdapter(groceryProductAdapter);

    }
}