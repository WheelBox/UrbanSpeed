package com.abhirajsharma.urbanspeed.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abhirajsharma.urbanspeed.Products;
import com.abhirajsharma.urbanspeed.R;
import com.abhirajsharma.urbanspeed.model.ShopModel;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {
    private List<ShopModel> shopModelList;

    public List<ShopModel> getShopModelList() {
        return shopModelList;
    }

    public void setShopModelList(List<ShopModel> shopModelList) {
        this.shopModelList = shopModelList;
    }

    public ShopAdapter(List<ShopModel> shopModelList) {
        this.shopModelList = shopModelList;
    }

    @NonNull
    @Override
    public ShopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.shop_item, parent, false);
        return new ViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull ShopAdapter.ViewHolder holder, int position) {
        String image=shopModelList.get( position ).getImage();
        String name=shopModelList.get( position ).getName();
        String category=shopModelList.get( position ).getCategory();
        String rating=shopModelList.get( position ).getRating();
        String distace=shopModelList.get( position ).getDistance();
        String offer=shopModelList.get( position ).getOffer();
        String id=shopModelList.get( position ).getId();

        holder.setData( image,name,category,rating,offer,distace,id);










    }

    @Override
    public int getItemCount() {
        return shopModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView image;
        private TextView name,category,rating,offer,distance;


        public ViewHolder(@NonNull View itemView) {
            super( itemView );

            image=itemView.findViewById( R.id.shop_item_image );
            name=itemView.findViewById( R.id.shop_item_name );
            category=itemView.findViewById( R.id.shop_item_category );
            rating=itemView.findViewById( R.id.shop_item_rating );
            offer=itemView.findViewById( R.id.shop_item_offer);
            distance=itemView.findViewById( R.id.shop_item_distance );

        }

        private void setData(String Image, String Nmaae, String Category, String Rating, String Offer, String Distance, final String ID){
            Glide.with( itemView.getContext() ).load( Image ).into(image);
            name.setText( Nmaae );
            category.setText( Category );
            rating.setText( Rating );
            offer.setText( Offer );
            distance.setText( Distance );

            image.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent( itemView.getContext(), Products.class );
                    intent.putExtra( "store_id", ID);
                    itemView.getContext().startActivity( intent );

                 /*   FirebaseFirestore.getInstance().collection( "STORES" ).document( ID ).collection( "PRODUCTS" ).get().addOnCompleteListener( new OnCompleteListener<QuerySnapshot>( ) {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for (QueryDocumentSnapshot documentSnapshot:task.getResult()){
                                    String id =documentSnapshot.getId();
                                    Map<String,Object> AddProductDetails=new HashMap<>(  );
                                    AddProductDetails.put( "cut_price","1000" );

                                    FirebaseFirestore.getInstance().collection( "STORES" ).document(ID).collection( "PRODUCTS" ).document( id ).update( AddProductDetails ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){

                                                Toast.makeText( itemView.getContext(), " Task Done", Toast.LENGTH_SHORT ).show( );

                                            }
                                        }
                                    } );




                                }



                            }
                        }
                    } );*/






                }
            } );

        }
    }



}
