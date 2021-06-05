import java.util.Comparator;

public class Restaurant implements Comparable<Restaurant>, Comparator<Restaurant> {
    private String name;
    private double rating;
    private String cuisine;
    private int deliveryTime;

    public Restaurant(String restaurantName, Double restaurantRating, String cuisine, int deliveryTime){
        this.name=restaurantName;
        this.rating=restaurantRating;
        this.cuisine=cuisine;
        this.deliveryTime=deliveryTime;
    }
    public String rName(){
        return name;
    }
    public Double rRating(){ return rating; }
    public String cuisine(){
        return cuisine;
    }
    public int rDeliveryTime(){
        return deliveryTime;
    }

    public void updateCuisine(String category){ }
    public void updateRating(double score){ }
    public void updateDeliveryTime(int deliveryTime){ }

    public int compareTo(Restaurant o) {
        if(rating<o.rRating())
            return 1;
        else if(rating>o.rRating())
            return -1;
        return 0;

    }


    @Override
    public int compare(Restaurant o1, Restaurant o2) {
        return 0;
    }
}
