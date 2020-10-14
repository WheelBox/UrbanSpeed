package com.abhirajsharma.urbanspeed.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.abhirajsharma.urbanspeed.AddProduct;
import com.abhirajsharma.urbanspeed.DBquaries;
import com.abhirajsharma.urbanspeed.ProductDetails;
import com.abhirajsharma.urbanspeed.R;
import com.abhirajsharma.urbanspeed.model.GroceryProductModel;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroceryProductAdapter extends RecyclerView.Adapter<GroceryProductAdapter.ViewHolder> {

    private List<GroceryProductModel> groceryProductModelList;



    public GroceryProductAdapter(List<GroceryProductModel> groceryProductModelList) {
        this.groceryProductModelList = groceryProductModelList;
    }

    public List<GroceryProductModel> getGroceryProductModelList() {
        return groceryProductModelList;
    }

    public void setGroceryProductModelList(List<GroceryProductModel> groceryProductModelList) {
        this.groceryProductModelList = groceryProductModelList;
    }

    @NonNull
    @Override
    public GroceryProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext( ) ).inflate( R.layout.product_item_horizoltal, parent, false );

        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryProductAdapter.ViewHolder holder, int position) {
        String name = groceryProductModelList.get( position ).getName( );

        String offerType = groceryProductModelList.get( position ).getOffertype( );
        String offerAmount = groceryProductModelList.get( position ).getOfferAmount( );
        String price = groceryProductModelList.get( position ).getPrice( );
        String rating = groceryProductModelList.get( position ).getRating( );
        String reviewCount = groceryProductModelList.get( position ).getReviewCount( );
        String id = groceryProductModelList.get( position ).getId( );
        String tag=groceryProductModelList.get( position ).getTag_list();
        String description=groceryProductModelList.get( position ).getDescription();
        long inStock = groceryProductModelList.get( position ).getStock( );
        String image = groceryProductModelList.get( position ).getImage( );
        String store_id=groceryProductModelList.get( position ).getStore_id( );



        holder.setData( name, offerType, price, offerAmount, inStock, rating, reviewCount, image, id,tag,description,store_id );


    }

    @Override
    public int getItemCount() {
        return groceryProductModelList.size( );
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name, offer, cutprice, price, rating,description;
        private ImageView image;
        private ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull final View itemView) {
            super( itemView );

            name = itemView.findViewById( R.id.grocery_home_product_Name );
            offer = itemView.findViewById( R.id.grocery_home_product_Offer );
            cutprice = itemView.findViewById( R.id.grocery_home_product_CutPrice );
            price = itemView.findViewById( R.id.grocery_home_product_Price );
            image = itemView.findViewById( R.id.grocery_home_productImage );
            description=itemView.findViewById( R.id.grocery_home_productDescription );
            constraintLayout = itemView.findViewById( R.id.grocery_home_product_layout );
            rating = itemView.findViewById( R.id.grocery_home_product_Rating );


        }


        @SuppressLint("RestrictedApi")
        private void setData(final String Name, String cutPrice, String Price, String OfferAmount, long stock, String Rating, String reciew_count, String resource, final String Id, final String Tag, String Description, final String store_id) {

            constraintLayout.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick(View view) {

                    String currentUser= FirebaseAuth.getInstance().getCurrentUser().getUid();
                    if(DBquaries.admins_list.contains(currentUser)){
                        Intent intent=new Intent( itemView.getContext(), AddProduct.class );
                        intent.putExtra( "product_id",Id );
                        intent.putExtra( "layout_code",1 );
                        intent.putExtra( "store_id",store_id);
                        intent.putExtra( "relevant_tag",Tag);
                        intent.putExtra( "name",Name);
                        itemView.getContext().startActivity( intent );


                    } else {
                        Intent intent = new Intent( itemView.getContext( ), ProductDetails.class );
                        intent.putExtra( "product_id", Id );
                        intent.putExtra( "store_id", store_id );
                        intent.putExtra( "tag_string" ,Tag);
                        itemView.getContext( ).startActivity( intent );


                    }

                }
            } );



            price.setText( "₹"+Price+"/-" );
            cutprice.setText( "₹"+cutPrice+"/-" );

            name.setText( Name );
            offer.setText( OfferAmount + " off" );
            description.setText( Description );






            if (OfferAmount.equals( "0" )) {
                offer.setVisibility( View.GONE );
                cutprice.setVisibility( View.GONE );
            }



            rating.setText( Rating );

            Glide.with( itemView.getContext( ) ).load( resource ).into( image );


         /*   favouriteButton.setOnClickListener( new View.OnClickListener( ) {
                @Override
                public void onClick(View view) {

                    favouriteButton.setClickable( false );
                    if (ADDED_towishList) {
                        ADDED_towishList = false;
                        favouriteButton.setSupportImageTintList( ColorStateList.valueOf( Color.parseColor( "#9e9e9e" ) ) );
                        ////remove from wish List

                        DBquaries.removeFromGroceryWishList( Id, itemView.getContext( ) );

                        favouriteButton.setClickable( true );

                    } else {
                        ADDED_towishList = true;
                        favouriteButton.setSupportImageTintList( ColorStateList.valueOf( Color.parseColor( "#DF4444" ) ) );
                        ////add to wish List
                        final Map<String, Object> updateListSize = new HashMap<>( );
                        updateListSize.put( "list_size", (long) DBquaries.grocery_wishList.size( ) + 1 );
                        Map<String, Object> product_Id = new HashMap<>( );
                        product_Id.put( "id_" + (long) DBquaries.grocery_wishList.size( ), Id );
                        FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getUid( ) )
                                .collection( "USER_DATA" ).document( "MY_GROCERY_WISHLIST" ).
                                update( product_Id ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful( )) {

                                    FirebaseFirestore.getInstance( ).collection( "USERS" ).document( FirebaseAuth.getInstance( ).getUid( ) )
                                            .collection( "USER_DATA" ).document( "MY_GROCERY_WISHLIST" ).
                                            update( updateListSize ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful( )) {
                                                Toast.makeText( itemView.getContext( ), "Added to Wishlist", Toast.LENGTH_SHORT ).show( );
                                                DBquaries.grocery_wishList.add( Id );
                                                favouriteButton.setClickable( true );
                                            }

                                        }
                                    } );


                                }

                            }
                        } );

                    }


                }
            } );*/

        }


    }
}
