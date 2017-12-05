package com.example.otto.trabalhopratico3;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewProduct extends AppCompatActivity implements LocationListener {

    EditText txtName, txtDescription, txtPhotoUrl, txtLocation;
    Button btnEmprestar;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FusedLocationProviderClient mFusedLocationClient;
    public double newLocation;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    AppCompatActivity context;

    private LocationManager locationManager;
    @Override
    public void onResume() {
        super.onResume();

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
//                moveCamera(mCurrentLocation);
        } else {
            // Display the missing permission error dialog when the fragments resume.
//                mPermissionDenied = true;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(this.getSupportFragmentManager(), "dialog");
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.currentUser = mAuth.getCurrentUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mAuth = FirebaseAuth.getInstance();

        txtName = findViewById(R.id.txtProductName);
        txtDescription = findViewById(R.id.txtDescription);
        txtPhotoUrl = findViewById(R.id.txtPhotoUrl);
        txtLocation = findViewById(R.id.txtLocation);

        btnEmprestar = findViewById(R.id.btnEmprestar);
        btnEmprestar.setEnabled(false);
        btnEmprestar.setText("OBTENDO LOCALIZAÇÃO...");
        AppCompatActivity activity = (AppCompatActivity) this;

        try {

            LocationManager lm = (LocationManager)
                    this.getSystemService(Context.LOCATION_SERVICE);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

            context = this;
            btnEmprestar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (txtName.getText().length() == 0) {
                        txtName.setError("Ponha um nome!");
                    } else if (txtDescription.getText().length() == 0) {
                        txtDescription.setError("Ponha uma descriçao!");
                    } else if (txtPhotoUrl.getText().length() == 0) {
                        txtPhotoUrl.setError("Ponha um link para foto!");
                    } else if (txtLocation.getText().length() == 0) {
                        txtLocation.setError("Ponha um local!");
                    } else {
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
                }
            });
        } catch (SecurityException ex) {
            PermissionUtils.requestPermission(activity, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        txtLocation.setText(String.valueOf(latitude) + " " + String.valueOf(longitude));
        btnEmprestar.setText("EMPRESTAR PRODUTO");
        btnEmprestar.setEnabled(true);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Toast.makeText(getApplicationContext(), "StatusChanged", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(getApplicationContext(), "ProviderEnabled", Toast.LENGTH_LONG).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getApplicationContext(), "ProviderDisabled", Toast.LENGTH_LONG).show();
    }
}
