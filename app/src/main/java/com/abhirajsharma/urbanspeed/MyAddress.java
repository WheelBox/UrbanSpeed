package com.abhirajsharma.urbanspeed;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abhirajsharma.urbanspeed.adapter.AddressAdapter;
import com.abhirajsharma.urbanspeed.model.AddressModel;

import java.util.ArrayList;
import java.util.List;

public class MyAddress extends AppCompatActivity {
    private LinearLayout addnewAddress;
    private Toolbar toolbar;
    private RecyclerView addRecycler;
    private AddressAdapter addressAdapter;
    private List<AddressModel> addressModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);
        addnewAddress = findViewById(R.id.add_new_address_LL);


        addRecycler = findViewById(R.id.my_address_recycler);

        toolbar = findViewById(R.id.toolbar_address);
        toolbar.setTitle("My Address");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        addnewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyAddress.this, NewAddress.class));
            }
        });

        addressModelList = new ArrayList<>();
        addressModelList.add(new AddressModel("NAME", "8347538475", "HOME", "this will be the address details the address added by thg user<", "9347593864", 0));
        addressModelList.add(new AddressModel("NAME", "8347538475", "HOME", "this will be the address details the address added by thg user<", "9347593864", 0));
        addressModelList.add(new AddressModel("NAME", "8347538475", "HOME", "this will be the address details the address added by thg user<", "9347593864", 0));
        addressModelList.add(new AddressModel("NAME", "8347538475", "HOME", "this will be the address details the address added by thg user<", "9347593864", 0));
        addressModelList.add(new AddressModel("NAME", "8347538475", "HOME", "this will be the address details the address added by thg user<", "9347593864", 0));
        addressModelList.add(new AddressModel("NAME", "8347538475", "HOME", "this will be the address details the address added by thg user<", "9347593864", 0));

        addressAdapter = new AddressAdapter(addressModelList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        addRecycler.setLayoutManager(layoutManager);
        addRecycler.setAdapter(addressAdapter);

    }
}