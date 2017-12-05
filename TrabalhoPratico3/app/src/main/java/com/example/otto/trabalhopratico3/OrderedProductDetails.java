package com.example.otto.trabalhopratico3;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class OrderedProductDetails extends AppCompatActivity {

    String id, name, author, locator, location, photoUrl, description;
    TextView txtName, txtAuthor, txtReceiver, txtDescription, txtLocation, txtPhotoUrl;
    Button btnOrder;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ordered_product_details);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();

        Bundle getInfo = getIntent().getExtras();
        assert getInfo != null;

        id = getInfo.getString("productId");
        name = getInfo.getString("productName");
        author = getInfo.getString("productAuthor");
        locator = getInfo.getString("productReceiver");
        location = getInfo.getString("productLocation");
        photoUrl = getInfo.getString("productPhotoUrl");
        description = getInfo.getString("productDescription");

        txtName = findViewById(R.id.txtName);
        txtName.setText(name);
        txtAuthor = findViewById(R.id.txtAuthor);
        txtAuthor.setText("Autor:" + author);
        txtDescription = findViewById(R.id.txtDescription);
        txtDescription.setText("Descrição: " + description);
        txtLocation = findViewById(R.id.txtLoc);
        txtLocation.setText("Local de retirada: " + location);
        txtPhotoUrl = findViewById(R.id.txtPhotoUrl);
        txtPhotoUrl.setText("URL da foto: " + photoUrl);
        txtReceiver = findViewById(R.id.txtReceiver);
        txtReceiver.setText("Locador: " + locator);

        btnOrder = findViewById(R.id.btnOrder);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();

                DatabaseReference dbRef = database.getReference();
                DatabaseReference ordersRef = database.getReference("orders/");

                String key = dbRef.child("orders").push().getKey();
                Order order = new Order(key, id, name, location, author, FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), description, photoUrl);
                Map<String, Object> productValues = order.toMap();

                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put(key, productValues);

                ordersRef.updateChildren(childUpdates);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Empty", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
    }
}
