package com.abhirajsharma.urbanspeed.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abhirajsharma.urbanspeed.R;
import com.abhirajsharma.urbanspeed.SearchedProduct;
import com.abhirajsharma.urbanspeed.SearchedStore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SearchXAdapter extends RecyclerView.Adapter<SearchXAdapter.ViewHolder> implements Filterable {




    private List<String> searchProductNameModelList;
    private List<String> allProducts;
    private String store_id;

    public SearchXAdapter(List<String> searchProductNameModelList,String store_id) {
        this.searchProductNameModelList = searchProductNameModelList;
        this.allProducts=new ArrayList<>( searchProductNameModelList );
        this.store_id=store_id;
    }


    @NonNull
    @Override
    public SearchXAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext( ) ).inflate( R.layout.search_product_name_item, parent, false );
        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull SearchXAdapter.ViewHolder holder, int position) {


        holder.setData( searchProductNameModelList.get( position ),position );
    }

    @Override
    public int getItemCount() {


        return searchProductNameModelList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }


    Filter filter=new Filter( ) {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<String> filteredList=new ArrayList<>(  );

            if(charSequence.toString().isEmpty()){
                filteredList.addAll( allProducts  );
            }else {
                for (String list :allProducts ){
                    if(list.toLowerCase().contains( charSequence.toString().toLowerCase() )){

                        filteredList.add(  list ) ;
                    }
                }
            }

            FilterResults filterResults=new FilterResults();
            filterResults.values=filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            searchProductNameModelList.clear();
            searchProductNameModelList.addAll( (Collection<? extends String>) filterResults.values );
            notifyDataSetChanged();

        }
    };

    public class ViewHolder extends  RecyclerView.ViewHolder{

        public TextView textView;

        public ViewHolder(@NonNull final View itemView) {
            super( itemView );
            textView=itemView.findViewById( R.id.search_product_item_name );

        }

        private void setData(final String name, final int index){


            textView.setText( name );
            textView.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent( itemView.getContext(), SearchedProduct.class );
                    intent.putExtra( "tag_string", name);
                    intent.putExtra( "store_id",store_id );
                    itemView.getContext().startActivity( intent );



                }
            } );
        }

    }


}


