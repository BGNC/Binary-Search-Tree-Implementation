import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class Main {

    // the path of the text file from we will get the data
    public static final String filename="src/CENG112_HW4.csv";


    public static void main(String[] args) throws IOException {

        FoodBst<Food> foodBst = new FoodBst<>();// to use methods of foodbst we have created an object
        RestaurantBST<Restaurant> restaurantBST = new RestaurantBST<>();// to use methods of restaurantbst we have created an object


        //// we use it to find the restaurant that min delivery time
        ResturantBSTByDeliveryTime<Restaurant> restaurantBSTDeliveryTime = new ResturantBSTByDeliveryTime<>();

        // to use methods of foodbstbystock we have created an object
        // and find the food that max stock
        FoodBSTByStock<Food> foodBstByStock= new FoodBSTByStock<>();


        String line;
        String[][] allProducts = new String[21][7];
        BufferedReader br = new BufferedReader(new FileReader(filename));// read file
        String[] toSplitProducts;
        int counter = -1;
        while ((line = br.readLine()) != null) {

            toSplitProducts = line.split(",");
            counter += 1;
            for (int i = 0; i < 7; i++) {

                allProducts[counter][i] = toSplitProducts[i];

            }
            String rName = allProducts[counter][3];
            if (!rName.equals("rName")) {

                // The data handed in the text file was transferred to the array.
                // then we separated the data transferred to the array with their names and variables.
                // We used the data type integer for numeric values,
                // string for textual expressions,
                // double for decimal values.

                String restaurantName = allProducts[counter][3].substring(1);
                double restaurantRating = Double.parseDouble(allProducts[counter][4]);
                String restaurantCuisine=allProducts[counter][5].substring(1);
                String foodName = allProducts[counter][0];
                double foodPrice = Double.parseDouble(allProducts[counter][1]);
                int foodStock = Integer.parseInt(allProducts[counter][2]);
                int restaurantDeliveryTime =Integer.parseInt(allProducts[counter][6]);

                // We are creating an object from the restaurant object
                // because the data type I need to add to the restaurantbst will be restaurant.
                // and we use it in food class to access restaurant class.
                Restaurant restaurants = new Restaurant(restaurantName,restaurantRating,restaurantCuisine,restaurantDeliveryTime);

                // The reason why
                // we produce objects from food class is
                // we needed foods object to add the products given in the text file
                // to the foodbst.

                Food foods = new Food(foodName,foodPrice,foodStock,restaurants);
                foodBst.add(foods);
                foodBstByStock.add(foods);// TO FIND MAX STOCK IN FOOD BST BY STOCK
                if(!restaurantBST.contains(restaurants)) {
                    restaurantBST.add(restaurants);
                    restaurantBSTDeliveryTime.add(restaurants); // TO FIND MIN DELIVERYTIME AMONG RESTAURANTS USING RESTAURANTBSTBYDELIVERYTIME.
                }

            }

        }

        // where the desired outputs begin to appear on the screen.

        //// piece of code that print sorts descending by rating of restaurant  on the screen
        Restaurant tempRestaurantIt;
        String iteratorRestaurantName;
        double iteratorRestaurantRating;
        Iterator<Restaurant> restaurantIt = restaurantBST.iterator();
        System.out.println("------------------ 1 -----------------");
        while(restaurantIt.hasNext()){
            tempRestaurantIt=restaurantIt.next();
            iteratorRestaurantName=tempRestaurantIt.rName();
            iteratorRestaurantRating=tempRestaurantIt.rRating();
            System.out.printf("%-30s %7.1f\n",iteratorRestaurantName,iteratorRestaurantRating);


        }

        // piece of code that print sorts ascending by price of food  on the screen.
        Food tempFoodIterator;
        String iteratorFoodName;
        double iteratorFoodPrice;
        int iteratorFoodStock;
        System.out.println("------------------ 2 -----------------");
        Iterator<Food> it= foodBst.iterator();
        while (it.hasNext()){
            tempFoodIterator=it.next();
            iteratorFoodName=tempFoodIterator.fName();
            iteratorFoodPrice=tempFoodIterator.fPrice();
            iteratorFoodStock=tempFoodIterator.fStock();
            System.out.printf("%-20s %10.2f  %5s\n",iteratorFoodName,iteratorFoodPrice,iteratorFoodStock);
        }

        // The piece of code that
        // gives the name of the restaurant that
        // brought the order as soon as possible and the delivery time.
        System.out.println("----------------- 3 ------------------");
        System.out.printf("%-30s %7s\n",restaurantBSTDeliveryTime.findSmallest().rName(),restaurantBSTDeliveryTime.findSmallest().rDeliveryTime());
        //The piece of code that
        // gives the name of the food that has max stock
        // brought food and the food stock .

        System.out.println("----------------- 4 ------------------");
        System.out.printf("%-30s %7s\n",foodBstByStock.findLargest().fName(),foodBstByStock.findLargest().fStock());


        // It deletes products with a price above 80 and prints the names and prices of the deleted products on the screen.
        System.out.println("----------------- 5 ------------------");
        Iterator<Food> foodIterator = foodBst.iterator();
        while(foodIterator.hasNext()){
            Food deletedFood =foodIterator.next();
            if(deletedFood.fPrice()>80){
                if(foodBst.remove(deletedFood))
                    System.out.printf("%-23s %6.2f Removed \n",deletedFood.fName(),deletedFood.fPrice());
            }
        }


        // It deletes restaurants with a rating below 8.0 and prints the names and prices of the deleted restaurants on the screen.
        System.out.println("----------------- 6 ------------------");

        Iterator<Restaurant> restaurantDeletedIterator = restaurantBST.iterator();
        while(restaurantDeletedIterator.hasNext()){

            Restaurant rest =restaurantDeletedIterator.next();

            if(rest.rRating()<8.0){
                System.out.printf("%-23s %5.1f  %5s\n",rest.rName() , rest.rRating() , "Removed");
                restaurantBST.remove(rest);
            }
        }
        



        // UPDATE PRICE
        System.out.println("----------------- 7 ------------------");
        Iterator<Food> updatePriceIterator = foodBst.iterator();
        Food updatedPrice;
        while (updatePriceIterator.hasNext()){
            updatedPrice=updatePriceIterator.next();
            updatedPrice.updatePrice(updatedPrice.fPrice());
        }
        System.out.println("Prices in FoodBSTs are updated.");


        // UPDATE STOCK
        System.out.println("----------------- 8 ------------------");
        Iterator<Food> updateStockIterator = foodBst.iterator();
        Food updatedStock;
        while (updateStockIterator.hasNext()){
            updatedStock=updateStockIterator.next();
            updatedStock.updateStock(updatedStock.fStock());
        }
        System.out.println("Stocks in FoodBSTs are updated.");


        // updated and ordered restaurant list
        System.out.println("----------------- 9 ------------------");
        Iterator<Restaurant> restOfRestaurantIterator = restaurantBST.iterator();
        while(restOfRestaurantIterator.hasNext()){
            tempRestaurantIt=restOfRestaurantIterator.next();
            iteratorRestaurantName=tempRestaurantIt.rName();
            iteratorRestaurantRating=tempRestaurantIt.rRating();
            System.out.printf("%-30s %7.1f\n",iteratorRestaurantName,iteratorRestaurantRating);
        }

        // updated and ordered food list
        System.out.println("----------------- 10 -----------------");
        Iterator<Food> foodIterator2= foodBst.iterator();
        while (foodIterator2.hasNext()){
            tempFoodIterator=foodIterator2.next();
            iteratorFoodName=tempFoodIterator.fName();
            iteratorFoodPrice=tempFoodIterator.fPrice();
            iteratorFoodStock=tempFoodIterator.fStock();
            System.out.printf("%-25s %5.2f  %5s\n",iteratorFoodName,iteratorFoodPrice,iteratorFoodStock);

        }

    }
}