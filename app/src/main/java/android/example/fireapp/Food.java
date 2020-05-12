package android.example.fireapp;

/*
This class creates food objects. This facilitates adding foods' data to firebase.
 */
public class Food {
    //Properties
    String name, ingredients;
    int price;

    //Constructors
    public Food(){}

    public Food(String name, String ingredients, int price) {
        this.name = name;
        this.ingredients = ingredients;
        this.price = price;
    }

    //GET & SET METHODS
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
