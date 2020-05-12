package android.example.fireapp;
/*
This class creates objects of promotion. This facilitates adding promotions to firebase.
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

    //GET & SET MEHODS
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
