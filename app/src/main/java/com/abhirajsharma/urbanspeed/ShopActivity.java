package com.abhirajsharma.urbanspeed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.abhirajsharma.urbanspeed.adapter.ShopAdapter;
import com.abhirajsharma.urbanspeed.model.ShopModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShopActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<ShopModel> shopModelList=new ArrayList<>(  );
    ShopAdapter shopAdapter;
    private Toolbar toolbar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_shop );

        recyclerView=findViewById( R.id.shop_recycler );

        shopAdapter = new ShopAdapter(shopModelList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(shopAdapter);

        toolbar = findViewById(R.id.toolbar_shop);
        toolbar.setTitle("Stores");
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor( Color.parseColor("#FFFFFF"));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        FirebaseFirestore.getInstance().collection("USERS").document( FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("MY_NEAR_STORES").orderBy("distance", Query.Direction.ASCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                String id = documentSnapshot.getId();

                                final long distance = (long) documentSnapshot.get("distance");
                                FirebaseFirestore.getInstance().collection("STORES").document(id).get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {

                                                    shopModelList.add(new ShopModel(task.getResult().get("image").toString(),
                                                            task.getResult().get("name").toString(),
                                                            task.getResult().get("category").toString(),
                                                            String.valueOf(distance)+ " km away from you",
                                                            "2.8",
                                                            task.getResult().get("offer").toString(),
                                                            task.getResult().getId()
                                                    ));

                                                }
                                                shopAdapter.notifyDataSetChanged();
                                            }
                                        });


                            }
                            shopAdapter.notifyDataSetChanged();



                        }
                    }
                });




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);

    }

}