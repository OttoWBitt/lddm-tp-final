package com.example.otto.trabalhopratico3;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by avenuecode on 01/12/17.
 */

public class Order {
    private String orderId;
    private String productId;
    private String productName;
    private String withDrawalPlace;
    private String productOwner;
    private String productReceiver;
    private String productDescription;
    private String productPhotoUrl;


    public Order() {
        this("", "","","","", "", "", "");
    }

    public Order(String orderId, String productId, String productName, String withDrawalPlace, String productOwner, String productReceiver, String productDescription, String productPhotoUrl) {
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.withDrawalPlace = withDrawalPlace;
        this.productOwner = productOwner;
        this.productReceiver = productReceiver;
        this.productDescription = productDescription;
        this.productPhotoUrl = productPhotoUrl;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getWithDrawalPlace() {
        return withDrawalPlace;
    }

    public void setWithDrawalPlace(String withDrawalPlace) {
        this.withDrawalPlace = withDrawalPlace;
    }

    public String getProductOwner() {
        return productOwner;
    }

    public void setProductOwner(String productOwner) {
        this.productOwner = productOwner;
    }

    public String getProductReceiver() {
        return productReceiver;
    }

    public void setProductReceiver(String productReceiver) {
        this.productReceiver = productReceiver;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String toString() {
        return this.getProductName();
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", orderId);
        result.put("productId", productId);
        result.put("productName", productName);
        result.put("location", withDrawalPlace);
        result.put("productOwner", productOwner);
        result.put("productReceiver", productReceiver);
        result.put("productDescription", productDescription);
        result.put("productPhotoUrl", productPhotoUrl);
        return result;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductPhotoUrl() {
        return productPhotoUrl;
    }

    public void setProductPhotoUrl(String productPhotoUrl) {
        this.productPhotoUrl = productPhotoUrl;
    }
}
