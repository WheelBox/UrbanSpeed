package com.abhirajsharma.urbanspeed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.abhirajsharma.urbanspeed.adapter.SearchProductNameAdapter;

import java.util.ArrayList;
import java.util.List;

public class Search extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView product_recycler;
    private SearchProductNameAdapter searchProductNameAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_search );


        searchView= findViewById( R.id.search_view );
        product_recycler=findViewById( R.id.search_product_name_recycler );

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            searchView.setElevation( (float) 5.0 );
        }


        final List<String> previousSearch = new ArrayList<>( DBquaries.allTags );

        searchProductNameAdapter = new SearchProductNameAdapter( previousSearch);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager( Search.this );
        linearLayoutManager.setOrientation( RecyclerView.VERTICAL );
        product_recycler.setLayoutManager( linearLayoutManager );
        product_recycler.setAdapter( searchProductNameAdapter );
        searchProductNameAdapter.notifyDataSetChanged();

        searchView.setOnQueryTextListener( new SearchView.OnQueryTextListener( ) {
            @Override
            public boolean onQueryTextSubmit(String s) {


                Intent intent=new Intent( Search.this, SearchedStore.class );
                intent.putExtra( "tag_string", s);
                startActivity( intent );

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                searchProductNameAdapter.getFilter().filter( s );
                return false;
            }
        } );
    }
}