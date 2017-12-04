package com.example.otto.trabalhopratico3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class NewProduct extends AppCompatActivity {

    EditText txtName, txtDescription, txtPhotoUrl, txtLocation;
    Button btnEmprestar;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        mAuth = FirebaseAuth.getInstance();

        txtName = findViewById(R.id.txtProductName);
        txtDescription = findViewById(R.id.txtDescription);
        txtPhotoUrl = findViewById(R.id.txtPhotoUrl);
        txtLocation = findViewById(R.id.txtLocation);

        btnEmprestar = findViewById(R.id.btnEmprestar);
        btnEmprestar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = mAuth.getCurrentUser();
                FirebaseDatabase database = FirebaseDatabase.getInstance();

                DatabaseReference dbRef = database.getReference();
                DatabaseReference productRef = database.getReference("products/");

                String key = dbRef.child("products").push().getKey();
                Product product = new Product(
                        key,
                        txtName.getText().toString(),
                        txtDescription.getText().toString(),
                        txtPhotoUrl.getText().toString(),
                        currentUser.getDisplayName(),
                        currentUser.getUid(),
                        txtLocation.getText().toString());
                Map<String, Object> productValues = product.toMap();

                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put(key, productValues);

                productRef.updateChildren(childUpdates);

                Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainActivity);
                Toast.makeText(getApplicationContext(), "You just added it!", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.currentUser = mAuth.getCurrentUser();
    }
}
