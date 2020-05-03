package android.example.fireapp;

public class Restaurant {
    //Properties
    String uid;
    String email;
    Boolean isRestaurant;

    public Restaurant(){}

    public Restaurant(String uid, String email) {
        this.uid = uid;
        this.email = email;
        isRestaurant = true;
    }
}
