package com.abhirajsharma.urbanspeed;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.abhirajsharma.urbanspeed.model.ShopModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class DBquaries {

    private static final int LOCATION_PERMISSION_CODE = 1;
    public static List<String> grocery_CartList_product_id = new ArrayList<>();
    public static List<String> grocery_CartList_product_count = new ArrayList<>();
    public static List<String> grocery_CartList_product_OutOfStock = new ArrayList<>();
    public static List<String> grocery_OrderList = new ArrayList<>();
    public static List<String> allProductStore = new ArrayList<>();
    public static String store_id = "";
    private static LinearLayout search_ll;
    public static int PRICE_IN_CART_GROCERY = 0;
    public static int TOTAL_SAVE = 0;
    public static int DELIVERY_CHARGES = 0;
    public static boolean IS_ADMIN = false;
    public static boolean IS_USER = false;
    public static int MIN_ORDER_AMOUNT = 0;
    public final static double AVERAGE_RADIUS_OF_EARTH_KM = 6371;
    /////nearbyStores
    public static List<ShopModel> shopModelList = new ArrayList<>();
    public static List<String> nearbyShopIds = new ArrayList<>();
    public static List<String> nearbyShopIdsDistance = new ArrayList<>();
    /////allproductTagForstore
    public static List<String> allTags = new ArrayList<>();
    ////Identifying_User
    public static List<String> users_list = new ArrayList<>();
    public static List<String> admins_list = new ArrayList<>();

    public static void removeFromGroceryCartList(final String id, final Context context) {
        grocery_CartList_product_id.remove(id);

        final int size = grocery_CartList_product_id.size();

        if (size == 0) {
            DBquaries.store_id="";
            Map<String, Object> Size = new HashMap<>();
            Size.put("list_size", 0);
            Size.put("store_id", "");


            FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid())
                    .collection("USER_DATA").document("MY_GROCERY_CARTLIST")
                    .set(Size).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        DBquaries.grocery_CartList_product_id.clear();
                        DBquaries.grocery_CartList_product_count.clear();
                        Toast.makeText(context, "removed from cart", Toast.LENGTH_SHORT).show();
                        DBquaries.grocery_CartList_product_OutOfStock.remove(id);

                        ProductDetails.gotoCart.setVisibility(View.GONE);
                        ProductDetails.addtoCart.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(context, MyCart.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);


                    }


                }
            });

        }
        for (int x = 0; x < size; x++) {
            Map<String, Object> updateWishList = new HashMap<>();
            updateWishList.put("id_" + x, grocery_CartList_product_id.get(x));

            FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid())
                    .collection("USER_DATA").document("MY_GROCERY_CARTLIST")
                    .set(updateWishList).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Map<String, Object> Size = new HashMap<>();
                        Size.put("list_size", size);
                        Size.put("store_id", DBquaries.store_id);

                        FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid())
                                .collection("USER_DATA").document("MY_GROCERY_CARTLIST")
                                .update(Size).addOnCompleteListener(new OnCompleteListener<Void>() {

                            @Override
                            public void onComplete(@NonNull Task<Void> task) {


                                if (task.isSuccessful()) {


                                    Toast.makeText(context, "removed from cart", Toast.LENGTH_SHORT).show();
                                    DBquaries.grocery_CartList_product_OutOfStock.remove(id);
                                    ProductDetails.gotoCart.setVisibility(View.GONE);
                                    ProductDetails.addtoCart.setVisibility(View.VISIBLE);
                                    Intent intent = new Intent(context, MyCart.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);


                                }


                            }
                        });

                    } else {
                        String error = task.getException().getMessage();
                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show();


                    }
                }
            });


        }


    }

//    private static LinearLayout search_ll;

    public static void findDistance() {

        FirebaseFirestore.getInstance().collection( "USERS" ).document(FirebaseAuth.getInstance().getCurrentUser().getUid()).get()
                .addOnCompleteListener( new OnCompleteListener<DocumentSnapshot>( ) {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            final double userLat = Double.parseDouble(task.getResult().get( "lat" ).toString());
                            final double userLng = Double.parseDouble(task.getResult().get( "lon" ).toString());
                            FirebaseFirestore.getInstance().collection("STORES").orderBy("name").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                            String lat = documentSnapshot.get("lat").toString();
                                            final String id = documentSnapshot.getId();
                                            double venueLat = Double.parseDouble(lat);
                                            String lon = documentSnapshot.get("lon").toString();
                                            double venueLng = Double.parseDouble(lon);

                                            double latDistance = Math.toRadians(userLat - venueLat);
                                            double lngDistance = Math.toRadians(userLng - venueLng);

                                            double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                                                    + Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(venueLat))
                                                    * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

                                            double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

                                            long distance = Math.round(AVERAGE_RADIUS_OF_EARTH_KM * c);


                                            Map<String, Object> dis = new HashMap<>();
                                            dis.put("distance", distance);

                                            FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid())
                                                    .collection("MY_NEAR_STORES").document(id).set(dis).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if (task.isSuccessful()) {


                                                    }

                                                }
                                            });

                                        }


                                    }


                                }
                            });

                        }

                    }
                } );



    }

    public static void loadGroceryOrders() {
        grocery_OrderList.clear();
        FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("USER_DATA").document("MY_GROCERY_ORDERS").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            long size = (long) task.getResult().get("list_size");
                            for (long x = 0; x < size; x++) {
                                grocery_OrderList.add(task.getResult().get("order_id_" + x).toString());
                            }


                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("checkMe", Objects.requireNonNull(e.getMessage()));
                    }
                });
    }

    public static void loadGroceryCartList(final Context context) {
        grocery_CartList_product_id.clear();
        FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getUid())
                .collection("USER_DATA").document("MY_GROCERY_CARTLIST")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    store_id = task.getResult().get("store_id").toString();
                    for (long x = 0; x < (long) task.getResult().get("list_size"); x++) {
                        String id = task.getResult().get("id_" + x).toString();
                        grocery_CartList_product_id.add(id);
                    }
                } else {
                    String error = task.getException().getMessage();
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    /////nearbyStores

    public static void calcualtePriceGrocery(String todo, String amount) {
//         if (todo.equals("+")) {
//             DBquaries.PRICE_IN_CART_GROCERY = DBquaries.PRICE_IN_CART_GROCERY + Integer.parseInt(amount);
//             MyCart.priceIncart.setText(String.valueOf(PRICE_IN_CART_GROCERY));
//             MyCart.tax.setText(String.valueOf(DELIVERY_CHARGES));
//             MyCart.grandTotal.setText(String.valueOf(PRICE_IN_CART_GROCERY + DELIVERY_CHARGES));
//             MyCart.payAmount.setText("₹" + (PRICE_IN_CART_GROCERY + DELIVERY_CHARGES));
//         } else {
//             DBquaries.PRICE_IN_CART_GROCERY = DBquaries.PRICE_IN_CART_GROCERY - Integer.parseInt(amount);
//             MyCart.priceIncart.setText(String.valueOf(PRICE_IN_CART_GROCERY));
//             MyCart.tax.setText(String.valueOf(DELIVERY_CHARGES));
//             MyCart.payAmount.setText("₹" + (PRICE_IN_CART_GROCERY + DELIVERY_CHARGES));
//             MyCart.grandTotal.setText(String.valueOf(PRICE_IN_CART_GROCERY + DELIVERY_CHARGES));
        if (todo.equals("+")) {
            DBquaries.PRICE_IN_CART_GROCERY = DBquaries.PRICE_IN_CART_GROCERY + Integer.parseInt(amount);
            MyCart.priceIncart.setText(String.valueOf(PRICE_IN_CART_GROCERY));
            MyCart.tax.setText(String.valueOf(DELIVERY_CHARGES));
            MyCart.grandTotal.setText(String.valueOf(PRICE_IN_CART_GROCERY + DELIVERY_CHARGES));
            MyCart.payAmount.setText("₹" + (PRICE_IN_CART_GROCERY + DELIVERY_CHARGES));


        } else {
            DBquaries.PRICE_IN_CART_GROCERY = DBquaries.PRICE_IN_CART_GROCERY - Integer.parseInt(amount);
            MyCart.priceIncart.setText(String.valueOf(PRICE_IN_CART_GROCERY));
            MyCart.tax.setText(String.valueOf(DELIVERY_CHARGES));
            MyCart.payAmount.setText("₹" + (PRICE_IN_CART_GROCERY + DELIVERY_CHARGES));
            MyCart.grandTotal.setText(String.valueOf(PRICE_IN_CART_GROCERY + DELIVERY_CHARGES));
        }


    }

//    public static void setUserData(){
//        Map<String,Object> userData=new HashMap<>(  );
//        userData.put( "list_size",0 );
//        userData.put( "store_id","" );
//        FirebaseFirestore.getInstance().collection( "USERS" ).document( FirebaseAuth.getInstance().getCurrentUser().getUid() ).collection( "USER_DATA")
//                .document( "MY_ADDRESS" ).set( userData ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful()){
//
//                }
//            }
//        } );
//        FirebaseFirestore.getInstance().collection( "USERS" ).document( FirebaseAuth.getInstance().getCurrentUser().getUid() ).collection( "USER_DATA")
//                .document( "MY_GROCERY_CARTITEMCOUNT" ).set( userData ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if(task.isSuccessful()){
//
//                }
//            }
//        } );
//        FirebaseFirestore.getInstance().collection( "USERS" ).document( FirebaseAuth.getInstance().getCurrentUser().getUid() ).collection( "USER_DATA")
//                .document( "MY_GROCERY_ORDERS" ).set( userData ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if(task.isSuccessful()){
//
//                        }
//                    }
//                } );
//        FirebaseFirestore.getInstance().collection( "USERS" ).document( FirebaseAuth.getInstance().getCurrentUser().getUid() ).collection( "USER_DATA")
//                .document( "MY_GROCERY_WISHLIST" ).set( userData ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if(task.isSuccessful()){
//
//                        }
//                    }
//                } );
//        FirebaseFirestore.getInstance().collection( "USERS" ).document( FirebaseAuth.getInstance().getCurrentUser().getUid() ).collection( "USER_DATA")
//                .document( "MY_GROCERY_CARTLIST" ).set( userData ).addOnCompleteListener( new OnCompleteListener<Void>( ) {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if(task.isSuccessful()){
//
//                        }
//                    }
//                } );
//    }

    public static void calculateTotalSave(String todo, String price, String cutPrice) {

        if (todo.equals("+")) {
            TOTAL_SAVE = TOTAL_SAVE + Integer.parseInt(cutPrice) - Integer.parseInt(price);
            MyCart.totalSave.setText("₹" + TOTAL_SAVE);
        } else {
            TOTAL_SAVE = TOTAL_SAVE - Integer.parseInt(cutPrice) + Integer.parseInt(price);
            MyCart.totalSave.setText("₹" + TOTAL_SAVE);

        }
    }

    public static void setShop() {
        shopModelList.clear();
        nearbyShopIds.clear();
        nearbyShopIdsDistance.clear();
        setallTags();
        FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("MY_NEAR_STORES").orderBy("distance", Query.Direction.ASCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                String id = documentSnapshot.getId();
                                nearbyShopIds.add(id);
                                final long distance = (long) documentSnapshot.get("distance");
                                nearbyShopIdsDistance.add( String.valueOf( distance ) );
                                FirebaseFirestore.getInstance().collection("STORES").document(id).get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {

                                                    shopModelList.add(new ShopModel(task.getResult().get("image").toString(),
                                                            task.getResult().get("name").toString(),
                                                            task.getResult().get("category").toString(),
                                                            String.valueOf(distance)+ "km away from you",
                                                            "2.8",
                                                            task.getResult().get("offer").toString(),
                                                            task.getResult().getId()
                                                    ));

                                                }
                                            }
                                        });


                            }


                        }
                    }
                });

    }
    ///allproductTagForstore

    public static void setUserData() {
        Map<String, Object> userData = new HashMap<>();
        userData.put("list_size", 0);
        userData.put("store_id", "");
        FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("USER_DATA")
                .document("MY_ADDRESS").set(userData).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

            }
        });
        FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("USER_DATA")
                .document("MY_GROCERY_CARTITEMCOUNT").set(userData).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

            }
        });
        FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("USER_DATA")
                .document("MY_GROCERY_ORDERS").set(userData).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

            }
        });
        FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("USER_DATA")
                .document("MY_GROCERY_WISHLIST").set(userData).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

            }
        });
        FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("USER_DATA")
                .document("MY_GROCERY_CARTLIST").set(userData).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {

            }
        });
    }
    ///addAdminData

    public static void setallTags() {
        allTags.clear();
        FirebaseFirestore.getInstance().collection("USERS").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).collection("MY_NEAR_STORES").orderBy("distance", Query.Direction.ASCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                                String id = documentSnapshot.getId();
                                FirebaseFirestore.getInstance().collection("STORES").document(id).collection("PRODUCTS").get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                for (QueryDocumentSnapshot documentSnapshot1 : task.getResult()) {

                                                    String name = documentSnapshot1.get("name").toString();

                                                    if (!allTags.contains(name)) {
                                                        allTags.add(name);
                                                    }


                                                }

                                            }
                                        });


                            }

                        }
                    }
                });


    }
    ///addAdminData
    public static void setAdminDATA(Context context) {

        DocumentReference ref = FirebaseFirestore.getInstance().collection("STORES").document();
        String store_id = ref.getId();

        Map<String, Object> adminData = new HashMap<>();
        adminData.put("store_id", store_id);

        FirebaseFirestore.getInstance().collection("ADMINS").document(FirebaseAuth.getInstance().getCurrentUser().getUid()).set(adminData)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(context, storeDetails.class);
                            intent.putExtra("store_id", store_id);
                            context.startActivity(intent);
                        }
                    }
                });


    }

    ////Identifying_User
    public static void chechUSERS() {
        users_list.clear();
        admins_list.clear();
        FirebaseFirestore.getInstance().collection("USERS").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        users_list.add(documentSnapshot.getId());
                        if(documentSnapshot.getId().equals(  FirebaseAuth.getInstance().getCurrentUser().getUid())){
                            IS_USER=true;
                            IS_ADMIN=false;
                        }
                    }
                }
            }
        });
        FirebaseFirestore.getInstance().collection("ADMINS").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        admins_list.add(documentSnapshot.getId());
                        if(documentSnapshot.getId().equals(  FirebaseAuth.getInstance().getCurrentUser().getUid())){
                            IS_USER=false;
                            IS_ADMIN=true;
                        }
                    } }
            }
        });


    }
    ////Identifying_User


}
