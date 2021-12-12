package BusinessLayer;

import java.util.List;

/**
 * Created by Rona Dumitrescu on 23.05.2019.
 */
public interface IRestaurantProcessing {

    /**
     *  Creates new Menu Item
     *
     *  @pre
     * @param price greater than 0
     * @param name not null
     *
     * @post
     * @return the new Menu Item Created, the Menu will contain the new Menu Item
     */
    public MenuItem createNewMenuItem(Double price, String name) throws Exception ;

    /**
     *   Deletes Menu Item
     *   @pre
     * @param menuItem has to be a menuItem existing in the Menu
     *
     *   @post
     * the menuItem indicated will be deleted
     */
    void deleteMenuItem(MenuItem menuItem);

    /**
     * @pre
     * @param price has to pe greater than 0
     * @param name has to be a name of an existing MenuItem
     *
     *  @post
     *  This method will edit the product indicated
     * @return message of succes?
     */
    String editMenuItem(Double price, String name);


    /**
     * @pre
     * @param tableNumber should be greater than 0
     * @param menuItemList not null
     *
     * @post
     * @return the new Order made (and added to the orders List)
     */
    public Order createNewOrder(int tableNumber, List<MenuItem> menuItemList);

    /**
     * @pre
     * @param order should be a placed order
     *
     * @post
     * @return the total price of the placed order
     */
     int computePriceForAnOrder(Order order);

    /**
     * @pre
     * @param order should be an order placed
     *
     * @post
     * It will generate the bill in a .TXT file
     */
     void generateBill(Order order);


}
