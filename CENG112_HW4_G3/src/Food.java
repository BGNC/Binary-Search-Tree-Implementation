public class Food implements Orderable, Comparable<Food>{
    private String name;
    private double price;
    private int stock;
    private Restaurant restaurant;

    public Food(String foodName, double foodPrice, int foodStock, Restaurant restaurant){
        this.name=foodName;
        this.price=foodPrice;
        this.stock=foodStock;
        this.restaurant=restaurant;


    }

    public String fName() {return name;}

    public Double fPrice(){return price;}

    public int fStock(){return stock;}

    public Restaurant fRestaurant(){return restaurant;}

    @Override
    public void updatePrice(double Price) { price=price*6/5; }

    @Override
    public void updateStock(int Stock) { stock=(Stock/2); }

    @Override
    public int compareTo(Food o) {
        if(price>o.fPrice())
            return 1;
        else if(o.fPrice()>price)
            return -1;

        return 0;
    }



}
