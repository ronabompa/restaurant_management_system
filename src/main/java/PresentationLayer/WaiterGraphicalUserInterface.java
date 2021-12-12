package PresentationLayer;

import BusinessLayer.*;
import BusinessLayer.MenuItem;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Created by Rona Dumitrescu on 23.05.2019.
 */
public class WaiterGraphicalUserInterface extends JFrame implements ActionListener{

    //PANELS
    private  JPanel panel1;
    private  JPanel panel2;
    private  JPanel panel3;
    private  JPanel panel4;

    //JTABELS
    private JTable tableMenuItems;
    private JTable tableOrders;
    private JTable tableMenuItemsSelected;

    List<MenuItem> menuItemSelectedList = new LinkedList<MenuItem>();

    public List<MenuItem> getMenuItemSelectedList() {
        return menuItemSelectedList;
    }

    public void setMenuItemSelectedList(List<MenuItem> menuItemSelectedList) {
        this.menuItemSelectedList = menuItemSelectedList;
    }

    //SETTERS AND GETTERS
    public JTable getTableMenuItemsSelected() {
        return tableMenuItemsSelected;
    }

    public void setTableMenuItemsSelected(JTable tableMenuItemsSelected) {
        this.tableMenuItemsSelected = tableMenuItemsSelected;
    }
    public JTable getTableOrders() {
        return tableOrders;
    }

    public void setTableOrders(JTable tableOrders) {
        this.tableOrders = tableOrders;
    }

    public JTable getTableMenuItems() {
        return tableMenuItems;
    }

    public void setTableMenuItems(JTable tableMenuItems) {
        this.tableMenuItems = tableMenuItems;
    }

    //BUTTONS
    private final JButton addMenuItemToOrderButton;
    private final JButton makeOrderButton;
    private final JButton back;
    private final  JButton createBillButton;
    private final  JButton totalButton;

    public WaiterGraphicalUserInterface() {

        super("MAKE ORDER");
        setLayout(new FlowLayout());
        this.setSize(1900, 650);
        this.setLocationRelativeTo(null);  // to center it

        // PANEL
        panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        panel1.setBounds(0,0,701,500);

        panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        panel2.setBounds(750,0,601,500);

        panel3 = new JPanel();
        panel3.setLayout(new GridBagLayout());
        panel3.setBounds(1370,0,1890,500);

        panel4 = new JPanel();
        panel4.setLayout(new GridBagLayout());
        panel4.setBounds(0,502,0,120);

        //BUTTONS
        createBillButton = new JButton();
        createBillButton.setText("Bill");

        back = new JButton();
        back.setText("Back");

        addMenuItemToOrderButton = new JButton();
        addMenuItemToOrderButton.setText("Add to Order");

        makeOrderButton = new JButton();
        makeOrderButton.setText("Order");

        totalButton = new JButton();
        totalButton.setText("Total");

        //JLABELS
        JLabel totalLabel = new JLabel("Total: ");
        JLabel tableLabel = new JLabel("Table: ");

        //JTEXTFIELDS
        final JTextField totalText = new JTextField(10);
        final JTextField tableText = new JTextField(10);

        //JTABELS
        //MenuItems
        final List<BusinessLayer.MenuItem> menuItemList = Restaurant.restaurant.getMenuItemList();

        String[] columnNames = {"Name","Price"};
        Object[][] allMenuItems = new Object[menuItemList != null ? menuItemList.size() : 0][2];

        if(menuItemList != null)
        {
            for(int i=0;i<menuItemList.size(); i++)
            {
                allMenuItems[i][0] = menuItemList.get(i).getName();
                allMenuItems[i][1] = menuItemList.get(i).getPrice() + "";

            }
        }
        tableMenuItems = new JTable(allMenuItems, columnNames);
        //tableMenuItems.addColumn();

        tableMenuItems.setPreferredScrollableViewportSize(new Dimension(400,500));
        JScrollPane scrollPaneMenuItems = new JScrollPane(tableMenuItems);

        // SELECTED MENUITEMS


        Object[][] allMenuItemsSlected = new Object[menuItemSelectedList.size()][2];

        for(int i=0;i<menuItemSelectedList.size(); i++)
        {
            allMenuItemsSlected[i][0] = menuItemSelectedList.get(i).getName();
            //allMenuItemsSlected[i][1] = menuItemSelectedList.get(i).getPrice() + "";

        }
        tableMenuItemsSelected = new JTable(allMenuItemsSlected, columnNames);

        tableMenuItemsSelected.setPreferredScrollableViewportSize(new Dimension(400,500));
        JScrollPane scrollPaneMenuItemsSelected = new JScrollPane(tableMenuItemsSelected);

        //Orders
        List<Order> orderList = new LinkedList<Order>();


        String[] columnNamesOrders = {"Order ID", "Date","Table","Ordered products"};
        Object[][] allOrder = new Object[orderList.size()][4];

        tableOrders = new JTable(allOrder, columnNamesOrders);
        Restaurant.restaurant.refreshTableDataOrders(tableOrders);
        tableOrders.setPreferredScrollableViewportSize(new Dimension(900,500));
        JScrollPane scrollPaneOrders = new JScrollPane(tableOrders);

        //PlacedOrders


        // ADDING PANELS TO FRAME
        add(panel1);
        add(panel2);
        add(panel3);
        add(panel4);

        // ADDING COMPONENTS TO PANELS
        panel1.add(scrollPaneMenuItems);
        panel2.add(scrollPaneMenuItemsSelected);
        panel3.add(scrollPaneOrders);
        panel4.add(addMenuItemToOrderButton);
        panel4.add(tableLabel);
        panel4.add(tableText);
        panel4.add(makeOrderButton);
        panel4.add(totalLabel);
        panel4.add(totalText);
        panel4.add(totalButton);
        panel4.add(createBillButton);
        panel4.add(back);
        panel4.add(back);

        // BACK BUTTON ACTION
       final WaiterGraphicalUserInterface waiterView = this;
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MainFrame mainFrame = new MainFrame();
                waiterView.setVisible(false);
            }
        });

        addMenuItemToOrderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int row = waiterView.getTableMenuItems().getSelectedRow();

                String[] cells = new String[2];

                for(int i = 0; i < cells.length ; i++)
                {
                    if( waiterView.getTableMenuItems().getModel().getValueAt(row,i) != null)
                    {
                        cells[i] = waiterView.getTableMenuItems().getModel().getValueAt(row,i).toString();
                    }
                }

                MenuItem menuItem = new MenuItem();
                menuItem.setName(cells[0]);
                menuItem.setPrice(Double.valueOf(cells[1]));

                menuItemSelectedList.add(menuItem);
                Restaurant.restaurant.refreshCurrentOrderTableData(waiterView.getMenuItemSelectedList(), waiterView.getTableMenuItemsSelected());
                panel2.repaint();
               // Restaurant.restaurant.getOrderMenuItemsList().put()

            }
        });

        makeOrderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int tableNo = Integer.valueOf(tableText.getText());

                Restaurant.restaurant.createNewOrder(tableNo,waiterView.getMenuItemSelectedList());
                Restaurant.restaurant.refreshTableDataOrders(waiterView.getTableOrders());
                panel3.repaint();

                waiterView.setMenuItemSelectedList(new LinkedList<MenuItem>());
                Restaurant.restaurant.refreshCurrentOrderTableData(waiterView.getMenuItemSelectedList(), waiterView.getTableMenuItemsSelected());
                panel2.repaint();
            }
        });

        createBillButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                Order order = new Order();

                int row = waiterView.getTableOrders().getSelectedRow();

                order.setOrderID((Integer)waiterView.getTableOrders().getModel().getValueAt(row,0));
                Restaurant.restaurant.generateBill(order);

            }
        });

        totalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                Order order = new Order();

                int row = waiterView.getTableOrders().getSelectedRow();

                order.setOrderID((Integer)waiterView.getTableOrders().getModel().getValueAt(row,0));

                totalText.setText(String.valueOf(Restaurant.restaurant.computePriceForAnOrder(order)));

            }
        });
    }

    public void actionPerformed(ActionEvent e) {

    }
}
