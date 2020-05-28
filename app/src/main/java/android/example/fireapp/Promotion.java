package android.example.fireapp;

/**
 * This class creates promotion objects. This facilitates adding promotions to firebase.
 *@date 26.04.2020
 *@author Group 3C
 */

public class Promotion {
    //Properties
    String name, uid, restaurantName;

    //Constructors
    public Promotion(){}

    public Promotion(String name, String restaurantName,String uid) {
        this.name = name;
        this.uid = uid;
        this.restaurantName = restaurantName;
    }

    //GET & SET METHODS - Methods that either retrieve or change their values.
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }
}
