package BusinessLayer;

import DataLayer.FileWriter;
import PresentationLayer.ChefGraphicalUserInterface;
import PresentationLayer.MainFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.*;
import java.util.Observable;

/**
 * Created by Rona Dumitrescu on 23.05.2019.
 */
public class Restaurant extends Observable implements Serializable, IRestaurantProcessing {

    public static final Long serialVersionUID = 555584777L;
    public static  Restaurant restaurant;

    // nu mai poate nimeni instantia Restaurant
    private Restaurant()
    {

    }

    public Map<Order,List<MenuItem>> orderMenuItemsList = new LinkedHashMap<Order, List<MenuItem>>();

    public List<MenuItem> menuItemList = new LinkedList<MenuItem>();

    // SETTERS & GETTERS
    public Map<Order, List<MenuItem>> getOrderMenuItemsList() {
        return orderMenuItemsList;
    }

    public void setOrderMenuItemsList(Map<Order, List<MenuItem>> orderMenuItemsList) {
        this.orderMenuItemsList = orderMenuItemsList;
    }

    public List<MenuItem> getMenuItemList() {
        return menuItemList;
    }

    public void setMenuItemList(List<MenuItem> menuItemList) {
        this.menuItemList = menuItemList;
    }

    //  METODE
    private int orderCounter = 1;


    public MenuItem createNewMenuItem(Double price, String name) throws Exception {

        assert price > 0 : "Price is not valid";
        assert name != null : "Name is not valid";

        for(MenuItem menuItem : this.getMenuItemList() )
        {
            assert menuItem.getName().equals(name) == false : "Menu item exists";
            if(menuItem.getName().equals(name))
            {

                throw new Exception("Produsul exista");
            }
        }

        MenuItem menuItem = new MenuItem();
        menuItem.setName(name);
        menuItem.setPrice(price);
        this.getMenuItemList().add(menuItem);


        return menuItem;
    }

    public void deleteMenuItem(MenuItem menuItem) {

        assert menuItem.getName() != null: "Produsul este null";

        for(MenuItem menuItemToDelete : this.getMenuItemList())
        {
            if(menuItemToDelete.getName().equals(menuItem.getName()))
            {
                this.getMenuItemList().remove(menuItemToDelete);
            }
        }


    }

    public String editMenuItem(Double price, String name) {

        assert price > 0 : "Price is not valid";
        assert name != null : "Name is not valid";

        for(MenuItem menuItem : this.getMenuItemList() )
        {
            if(menuItem.getName().equals(name))
            {
                menuItem.setPrice(price);
                return "Produsul a fost editat";
            }
        }

        return "Produsul nu a fost editat";
    }

    public List<MenuItem> viewAllMenuItem() {

        return this.getMenuItemList();

    }

    public void refreshTableData(JTable jTable)
    {
        String[] columnNames = {"Name","Price"};
        Object[][] allMenuItems = new Object[menuItemList.size()][2];

        for(int i=0;i<menuItemList.size(); i++)
        {
            allMenuItems[i][0] = menuItemList.get(i).getName();
            allMenuItems[i][1] = menuItemList.get(i).getPrice() + "";

        }

        // luam tabelul pe care il avem
        DefaultTableModel tableModel = new DefaultTableModel(allMenuItems, columnNames);
        jTable.setModel(tableModel);

    }

    public void refreshTableDataOrders(JTable jTable)
    {
        String[] columnNames = {"Order ID","Date","Table","Ordered products"};
        Object[][] allOrders = new Object[orderMenuItemsList.size()][4];

             int i = 0;
        for(Map.Entry<Order,List<MenuItem>> entry : orderMenuItemsList.entrySet())
        {
            allOrders[i][0] = entry.getKey().getOrderID();
          allOrders[i][1] = entry.getKey().getDate();
            allOrders[i][2]= entry.getKey().getTable();
            String listOfMenuItems = "";

            for(MenuItem menuItem : entry.getValue())
            {
                listOfMenuItems += menuItem.getName() + ", " + menuItem.getPrice()+"; ";
            }
            allOrders[i][3]= listOfMenuItems;
            i++;
        }

        // luam tabelul pe care il avem
        DefaultTableModel tableModel = new DefaultTableModel(allOrders, columnNames);
        jTable.setModel(tableModel);

    }

    public void refreshCurrentOrderTableData(List<MenuItem> menuItemSelectedList, JTable jTable)
    {
        String[] columnNames = {"Name","Price"};
        Object[][] allMenuItems = new Object[menuItemSelectedList.size()][2];

        for(int i=0;i<menuItemSelectedList.size(); i++)
        {
            allMenuItems[i][0] = menuItemSelectedList.get(i).getName();
            allMenuItems[i][1] = menuItemSelectedList.get(i).getPrice() + "";

        }

        // luam tabelul pe care il avem
        DefaultTableModel tableModel = new DefaultTableModel(allMenuItems, columnNames);
        jTable.setModel(tableModel);

    }

    public Order createNewOrder(int tableNumber, List<MenuItem> menuItemList) {

        assert tableNumber > 0: "Tabel no. is not valid";
        assert menuItemList != null: "No menu items ordered";

        Order order = new Order();

        order.setOrderID(orderCounter++);
        order.setDate(new Date());
        order.setTable(tableNumber);

        this.getOrderMenuItemsList().put(order,menuItemList);

        Restaurant.restaurant.setChanged();
        Restaurant.restaurant.notifyObservers(this);
        return order;
    }

    public int computePriceForAnOrder(Order order) {

        assert this.getOrderMenuItemsList().get(order) != null : "Order doesn't exist";

        for(Map.Entry<Order,List<MenuItem>> entry : orderMenuItemsList.entrySet())
        {
            if(order.getOrderID() == entry.getKey().getOrderID())
            {
                menuItemList = entry.getValue();
            }
        }

        int price = 0;

        for(MenuItem menuItem : menuItemList)
        {
            price += menuItem.getPrice();
        }

        return  price;
    }

    public void generateBill(Order order) {

        assert this.getOrderMenuItemsList().get(order) != null : "Order is not placed";

        Order orderToPrint = new Order();
        for(Map.Entry<Order,List<MenuItem>> entry : orderMenuItemsList.entrySet())
        {
            if(order.getOrderID() == entry.getKey().getOrderID())
            {
                orderToPrint = entry.getKey();
                menuItemList = entry.getValue();
            }
        }

        FileWriter fileWriter = new FileWriter();

        fileWriter.deleteFileContent("Bill.txt");

        String introOrder = orderToPrint.toString(orderToPrint);
        fileWriter.writeLineToFile("Bill.txt", introOrder);

        for(MenuItem menuItem : menuItemList)
        {
            fileWriter.writeLineToFile("Bill.txt", menuItem.getName() + " :" + menuItem.getPrice() + "RON" );
        }

    }




    /**
     * Write the bank status to file
     */
    public void close() {
        try {
            FileOutputStream fileOut = new FileOutputStream("restaurant.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);

            out.writeObject(this);
            out.close();
            fileOut.close();
        } catch (IOException ex) {
            System.out.println("Error writing to file 'restaurant.ser'");
        }
    }

    private static void  interfGraf()
    {
        MainFrame interfG = new MainFrame();
        interfG.setVisible(true);
    }

    public static void main(String[] args)
    {

        try {

            FileInputStream fileIn = new FileInputStream("restaurant.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);

            restaurant = (Restaurant) in.readObject();
           // restaurant.getMenuItemList();
            //restaurant.getOrderMenuItemsList();
            in.close();
            fileIn.close();

            Restaurant.restaurant.addObserver(MainFrame.chefFrame); // observerul meu


        } catch (Exception e) {
            restaurant = new Restaurant();
            e.printStackTrace();
        }

        interfGraf();


    }

}
