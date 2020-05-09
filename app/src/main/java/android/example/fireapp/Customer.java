package android.example.fireapp;

public class Customer {
    //Properties
    String uid;
    String email;
    boolean isRestaurant;

    public Customer(String uid, String email) {
        this.email = email;
        this.uid = uid;
        isRestaurant = false;
    }
}
