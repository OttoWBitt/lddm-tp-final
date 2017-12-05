package com.example.otto.trabalhopratico3;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListOfTakenItens extends AppCompatActivity {

    ArrayAdapter<Order> adapter;
    ArrayList<Order> itemList;

    public ArrayList<Order> getOrderList() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = database.getReference();
        DatabaseReference productRef = database.getReference("orders/");

        ArrayList<Order> orderList = new ArrayList<>();

        productRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                itemList.clear();
                for (DataSnapshot orderSnapshot: dataSnapshot.getChildren()) {
                    Order order1 = orderSnapshot.getValue(Order.class);
                    itemList.add(order1);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("RAF", "loadPost:onCancelled", databaseError.toException());
            }
        });

        return orderList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_taken_itens);

        itemList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.list_item, R.id.txtName, itemList);
        final ListView listV = (ListView) findViewById(R.id.taken_itens);
        listV.setAdapter(adapter);

        itemList.clear();
        itemList.addAll(getOrderList());
        adapter.notifyDataSetChanged();

        listV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent productDetails = new Intent(getApplicationContext(),OrderedProductDetails.class);
                productDetails.putExtra("productName", itemList.get(i).getProductName());
                productDetails.putExtra("productReceiver", itemList.get(i).getProductReceiver());
                productDetails.putExtra("productId", itemList.get(i).getProductId());
                productDetails.putExtra("productAuthor", itemList.get(i).getProductOwner());
                productDetails.putExtra("productLocation", itemList.get(i).getWithDrawalPlace());

                startActivity(productDetails);
            }
        });

    }
}
