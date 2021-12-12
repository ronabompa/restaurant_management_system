package PresentationLayer;

import BusinessLayer.Order;
import BusinessLayer.Restaurant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created by Rona Dumitrescu on 23.05.2019.
 */
public class ChefGraphicalUserInterface extends JFrame implements ActionListener, Observer {

    //PANELS
    private  JPanel panel1;
    private  JPanel panel2;

    //BUTTONS
    private final JButton back;

    //JTABELS
     private JTable tableOrders;

    //SETTERS AND GETTERS
    public JTable getTableOrders() {
        return tableOrders;
    }

    public void setTableOrders(JTable tableOrders) {
        this.tableOrders = tableOrders;
    }

    public ChefGraphicalUserInterface()
    {
        super("CHEF");
        setLayout(new FlowLayout());
        this.setSize(1050, 600);
        this.setLocationRelativeTo(null);  // to center it

        //PANELS
        panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        panel1.setBounds(0,0,800,500);
        panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        panel2.setBounds(0,501,800,95);

        //BUTTONS
        back = new JButton();
        back.setText("Back");

        //Orders

        java.util.List<Order> orderList = new LinkedList<Order>();

        String[] columnNamesOrders = {"Order ID","Date","Table","Ordered products"};
        Object[][] allOrder = new Object[orderList.size()][4];

        tableOrders = new JTable(allOrder, columnNamesOrders);

        Restaurant.restaurant.refreshTableDataOrders(tableOrders);
        tableOrders.setPreferredScrollableViewportSize(new Dimension(1000,480));

        // punem tableClients in scroll pane
        JScrollPane scrollPaneOrders = new JScrollPane(tableOrders);

        add(panel1);
        add(panel2);

        panel1.add(scrollPaneOrders);
        panel2.add(back);

        final ChefGraphicalUserInterface chefFrameThis = this;
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chefFrameThis.setVisible(false);
            }
        });

    }


    public void update(Observable o, Object arg) {

        if (o instanceof Restaurant)
        {
            Restaurant.restaurant.refreshTableDataOrders(tableOrders);
        }

    }

    public void actionPerformed(ActionEvent e) {

    }

}
