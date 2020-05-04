package android.example.fireapp;

public class Customer {
    //Properties
    String uid;
    String email;
    Boolean isRestaurant;

    public Customer(String uid, String email) {
        this.email = email;
        this.uid = uid;
        isRestaurant = false;

    }
}
