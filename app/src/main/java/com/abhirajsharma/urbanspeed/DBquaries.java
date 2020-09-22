package com.abhirajsharma.urbanspeed;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DBquaries {

    public static List<String> grocery_CartList_product_id = new ArrayList<>();
    public static List<String> grocery_CartList_product_count = new ArrayList<>();
    public static List<String> grocery_CartList_product_OutOfStock = new ArrayList<>();
    public static List<String> grocery_OrderList = new ArrayList<>();


    public static int PRICE_IN_CART_GROCERY = 0;
    public static int TOTAL_SAVE = 0;
    public static int DELIVERY_CHARGES = 20;
    public static boolean IS_ADMIN = false;
    public static int MIN_ORDER_AMOUNT = 0;


    public static void removeFromGroceryCartList(final String id, final Context context) {
        grocery_CartList_product_id.remove(id);

        final int size = grocery_CartList_product_id.size();

        if (size == 0) {

            Map<String, Object> Size = new HashMap<>();
            Size.put("list_size", 0);


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

    public static void calcualtePriceGrocery(String todo, String amount) {
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

    public static void calculateTotalSave(String todo, String price, String cutPrice) {

        if (todo.equals("+")) {
            TOTAL_SAVE = TOTAL_SAVE + Integer.parseInt(cutPrice) - Integer.parseInt(price);
            MyCart.totalSave.setText("₹" + TOTAL_SAVE);
        } else {
            TOTAL_SAVE = TOTAL_SAVE - Integer.parseInt(cutPrice) + Integer.parseInt(price);
            MyCart.totalSave.setText("₹" + TOTAL_SAVE);

        }
    }


}
