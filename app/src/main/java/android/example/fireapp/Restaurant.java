package android.example.fireapp;

import android.widget.ImageView;

import java.util.HashMap;

/*
This class creates restaurant objects to facilitate adding restaurants to firebase.
 */
public class Restaurant {
    //Properties
    String uid, email, name, genre, phone, description, reservations, menu, adress, workingHours, openingTime, closingTime, openingTimeHour, openingTimeMinute, closingTimeHour, closingTimeMinute;
    int numOfTimesRated, maxSeatingDuration, minPriceToPreOrder;
    double rating;
    ImageView restaurantImage;
    HashMap<String, Object> seats;

    //Constructors
    public Restaurant(){}

    public Restaurant(String name, String email, String genre, String phone, String uid, String numOfTables, int maxSeatingDuration, int minPriceToPreOrder, String openingTimeHour, String openingTimeMinute, String closingTimeHour, String closingTimeMinute) {
        this.uid = uid;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.genre = genre;
        description = "";
        reservations = "";
        menu = "";
        adress = "";
        this.openingTimeHour = openingTimeHour;
        this.openingTimeMinute = openingTimeMinute;
        this.closingTimeHour = closingTimeHour;
        this.closingTimeMinute = closingTimeMinute;
        openingTime = this.openingTimeHour + ":" + this.openingTimeMinute;
        closingTime = this.closingTimeHour + ":" + this.closingTimeMinute;
        //workingHours = "0:10-23:50";
        workingHours = this.openingTime + "-" + this.closingTime;
        numOfTimesRated = 0;
        this.maxSeatingDuration = maxSeatingDuration;
        this.minPriceToPreOrder = minPriceToPreOrder;
        rating = 0.0;
        seats = new Seats(Integer.parseInt(numOfTables), this);
    }

    //GET & SET METHODS
    public String getOpeningTime() {
        return openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public HashMap<String, Object> getSeats() {
        return seats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReservations() {
        return reservations;
    }

    public void setReservations(String reservations) {
        this.reservations = reservations;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public int getNumOfTimesRated() {
        return numOfTimesRated;
    }

    public void setNumOfTimesRated(int numOfTimesRated) {
        this.numOfTimesRated = numOfTimesRated;
    }

    public int getMaxSeatingDuration() {
        return maxSeatingDuration;
    }

    public void setMaxSeatingDuration(int maxSeatingDuration) {
        this.maxSeatingDuration = maxSeatingDuration;
    }

    public int getMinPriceToPreOrder() {
        return minPriceToPreOrder;
    }

    public void setMinPriceToPreOrder(int minPriceToPreOrder) {
        this.minPriceToPreOrder = minPriceToPreOrder;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    public ImageView getImage()
    {
        return restaurantImage;
    }
}
