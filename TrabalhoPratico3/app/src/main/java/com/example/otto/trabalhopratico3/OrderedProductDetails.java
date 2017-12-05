package com.example.otto.trabalhopratico3;

import android.content.Intent;
import android.net.Uri;
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
    Button btnOrder, btnVerNoMapa;
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

        btnVerNoMapa = findViewById(R.id.btnVerNoMapa);
        btnVerNoMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse("geo:-19.922930,-43.994348");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
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
