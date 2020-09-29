package com.abhirajsharma.urbanspeed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.abhirajsharma.urbanspeed.adapter.ShopAdapter;
import com.abhirajsharma.urbanspeed.model.ShopModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
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






        FirebaseFirestore.getInstance().collection( "STORES" ).orderBy( "name" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot documentSnapshot :task.getResult()){

                        shopModelList.add( new ShopModel(documentSnapshot.get( "image" ).toString(),
                                documentSnapshot.get( "name" ).toString(),
                                documentSnapshot.get( "category" ).toString(),
                                "2 km away from you !",
                                documentSnapshot.get( "rating" ).toString(),
                                documentSnapshot.get( "offer" ).toString()

                        ) );
                        shopAdapter.notifyDataSetChanged();



                    }
                    shopAdapter.notifyDataSetChanged();



                }
            }
        } );









    }
}