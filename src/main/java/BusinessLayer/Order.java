package BusinessLayer;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Rona Dumitrescu on 23.05.2019.
 */
public class Order implements Serializable{

    private int orderID;
    private Date date;
    private int table;

    // GETTERS & SETTERS
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getTable() {
        return table;
    }

    public void setTable(int table) {
        this.table = table;
    }

    @Override
    public int hashCode()
    {
        SimpleDateFormat originalFormat = new SimpleDateFormat("MMddHHmm");
        String date = originalFormat.format(this.getDate());

        String hashCodeString = "";
        hashCodeString = this.getOrderID() + date + this.getTable();

        return (int)(Long.parseLong(hashCodeString)/100);
    }

    public String toString(Order order)
    {
        return "Data: " + order.getDate().toString() + "  Masa: " + order.getTable();
    }
}
