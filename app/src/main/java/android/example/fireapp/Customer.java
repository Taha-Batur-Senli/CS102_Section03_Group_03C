package android.example.fireapp;

/*
A class that creates customer objects which facilitates the process of signing customer data up to firebase.
 */
public class Customer {
    //Properties
    String uid, email, name, reservations, notifications,
            favRestaurants, money, points, phone, ranking;

    //Constructors
    public Customer(){}
    public Customer(String name, String email, String phone,String uid ) {
        this.email = email;
        this.uid = uid;
        this.name = name;
        this.phone = phone;
        reservations = "";
        notifications = "";
        favRestaurants = "";
        money = "0";
        points = "0";
        ranking = "0";
    }

    //SET & GET METHODS
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReservations() {
        return reservations;
    }

    public void setReservations(String reservations) {
        this.reservations = reservations;
    }

    public String getNotifications() {
        return notifications;
    }

    public void setNotifications(String notifications) {
        this.notifications = notifications;
    }

    public String getFavRestaurants() {
        return favRestaurants;
    }

    public void setFavRestaurants(String favRestaurants) {
        this.favRestaurants = favRestaurants;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }
}
