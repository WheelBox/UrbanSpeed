package com.abhirajsharma.urbanspeed;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import com.abhirajsharma.urbanspeed.adapter.ShopAdapter;
import com.abhirajsharma.urbanspeed.model.ShopModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchedStore extends AppCompatActivity {


    private RecyclerView seachedProductRecycler;
    private TextView noProductTxt;
    private static String name = "";
    private static List<ShopModel> list = new ArrayList<>( );
    private Toolbar toolbar;
    String[] tags;
    ShopModel model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_searched_store );

        seachedProductRecycler = findViewById( R.id.searched_product_recycler );


        noProductTxt = findViewById( R.id.search_No_product_txt );

        int from_product_list=getIntent( ).getIntExtra( "",-1 );

        toolbar = findViewById( R.id.toolbar_searched_product );
        toolbar.setTitle( "Stores" );
        setSupportActionBar( toolbar );
        toolbar.setTitleTextColor( Color.parseColor( "#FFFFFF" ) );

        getSupportActionBar( ).setDisplayShowHomeEnabled( true );
        getSupportActionBar( ).setDisplayHomeAsUpEnabled( true );


        final List<String> ids = new ArrayList<>( );

        final Adapter adapter = new Adapter( list );
        seachedProductRecycler.setAdapter( adapter );
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        seachedProductRecycler.setLayoutManager(linearLayoutManager);


        name = getIntent( ).getStringExtra( "tag_string" ).toLowerCase();
        tags = name.split( " " );


        for (final String tag : tags) {

            ids.clear( );
            list.clear( );
            FirebaseFirestore.getInstance( ).collection( "STORES" ).whereArrayContains( "tags", tag )
                    .get( ).addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    if (task.isSuccessful( )) {
                        for (DocumentSnapshot documentSnapshot : Objects.requireNonNull( task.getResult( ) )) {

                            if(DBquaries.nearbyShopIds.contains( documentSnapshot.getId() )){
                                int index=DBquaries.nearbyShopIds.indexOf(  documentSnapshot.getId());
                                model = new ShopModel( documentSnapshot.get( "image" ).toString(),
                                        documentSnapshot.get( "name" ).toString(),
                                        documentSnapshot.get( "category" ).toString(),
                                        DBquaries.nearbyShopIdsDistance.get( index ),
                                        documentSnapshot.get( "rating" ).toString(),
                                        documentSnapshot.get( "offer" ).toString(),
                                        documentSnapshot.getId());
                                model.setTags( (ArrayList<String>) documentSnapshot.get( "tags" ) );
                            }

                            if (!ids.contains( model.getId( ) )) {
                                list.add( model );
                                ids.add( model.getId( ) );
                            }
                        }

                        if (tag.equals( tags[tags.length - 1] )) {
                            if (list.size( ) == 0) {
                                seachedProductRecycler.setVisibility( View.GONE );
                                noProductTxt.setVisibility( View.VISIBLE );
                            } else {
                                seachedProductRecycler.setVisibility( View.VISIBLE );
                                noProductTxt.setVisibility( View.GONE );
                                adapter.getFilter( ).filter( name );

                            }
                        }
                    } else {
                        String e = task.getException( ).getMessage( );
                        Toast.makeText( SearchedStore.this, e, Toast.LENGTH_SHORT ).show( );
                    }
                }
            } );
        }




    }

    public class Adapter extends ShopAdapter implements Filterable {
        private List<ShopModel> originalList;

        public Adapter(List<ShopModel> groceryProductModelList) {
            super( groceryProductModelList );
            originalList = groceryProductModelList;
        }

        @Override
        public Filter getFilter() {
            return new Filter( ) {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    FilterResults filterResults = new FilterResults( );
                    List<ShopModel> filterList = new ArrayList<>( );

                    final String[] tags = name.toLowerCase( ).split( " " );

                    ArrayList<String> presentTags = new ArrayList<>( );


                    for (ShopModel model : originalList) {


                        for (String tag : tags) {

                            if (model.getTags( ).contains( tag )) {

                                presentTags.add( tag );


                            }

                            model.setTags( presentTags );


                        }
                    }
                    for (int i = tags.length; i > 0; i--) {
                        for (ShopModel model : originalList) {
                            if (model.getTags( ).size( ) == i) {
                                filterList.add( model );
                            }
                        }
                    }
                    filterResults.values = filterList;
                    filterResults.count = filterList.size( );
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    if (filterResults.count > 0) {
                        setShopModelList( (List<ShopModel>) filterResults.values );
                    }

                    notifyDataSetChanged( );
                }
            };
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);

    }

}