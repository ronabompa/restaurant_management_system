package PresentationLayer;

import BusinessLayer.IRestaurantProcessing;
import BusinessLayer.MenuItem;
import BusinessLayer.Restaurant;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created by Rona Dumitrescu on 23.05.2019.
 */
public class AdministratorGraphicalUserInterface extends JFrame implements ActionListener {

    private JPanel panel1;

    public JPanel getPanel1() {
        return panel1;
    }

    public void setPanel1(JPanel panel1) {
        this.panel1 = panel1;
    }

    private JPanel panel2;

    private JTable tableMenuItems;

    public  JTable getTableMenuItems() {
        return tableMenuItems;
    }

    public void setTableMenuItems(JTable tableMenuItems) {
        this.tableMenuItems = tableMenuItems;
    }

    private final JButton addMenuItemButton;
    private final JButton editMenuItemButton;
    private final JButton deleteMenuItemButton;
    private final JButton viewAllMenuItemsButton;

    private JLabel priceLabel;
    private JLabel nameLabel;


    private final JButton back;

    public AdministratorGraphicalUserInterface()
    {
        super("ADMINISTRATOR");
        this.setLayout(new FlowLayout());
        this.setSize(1200, 600);
        this.setLocationRelativeTo(null);  // to center it

        //PANELS
        panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        panel1.setBounds(0,0,800,500);
        panel2 = new JPanel();
        panel2.setLayout(new GridBagLayout());
        panel2.setBounds(0,501,800,95);

        //BUTTONS
        addMenuItemButton = new JButton();
        addMenuItemButton.setText("add MenuItem");

        editMenuItemButton = new JButton();
        editMenuItemButton.setText("edit MenuItem");

        deleteMenuItemButton = new JButton();
        deleteMenuItemButton.setText("delete MenuItem");

        viewAllMenuItemsButton = new JButton();
        viewAllMenuItemsButton.setText("view all MenuItems");

        back = new JButton();
        back.setText("Back");


        //JLABBELS
        nameLabel = new JLabel("Name:");
        priceLabel = new JLabel("Price:");

        //TEXTFIEDLS
        final JTextField nameTextField = new JTextField(17);
        final JTextField priceTextField = new JTextField(8);

        //TABLE
        List<MenuItem> menuItemList = Restaurant.restaurant.getMenuItemList();

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

        tableMenuItems.setPreferredScrollableViewportSize(new Dimension(700,480));
        //tableMenuItems.setFillsViewportHeight(true);

        // punem tableMenuItems in scroll pane
        JScrollPane scrollPane = new JScrollPane(tableMenuItems);

        // ADDING PANELS TO FRAME
        add(panel1);
        add(panel2);

        // ADDING COMPONENTS TO PANELS
        panel1.add(scrollPane);

        panel2.add(editMenuItemButton);
        panel2.add(deleteMenuItemButton);
      //  panel2.add(viewAllMenuItemsButton);

        panel2.add(nameLabel);
        panel2.add(nameTextField);

        panel2.add(priceLabel);
        panel2.add(priceTextField);

        panel2.add(addMenuItemButton);
        panel2.add(back);

       final AdministratorGraphicalUserInterface adminView = this; // pastram intr-o variabila "this"
       // clientController.setClientsFrame(clientsView);
        //clientController.refreshTableData(new LinkedList<>(clientController.viewAllClients()), clientsView.getTableClients());


        // ADD BUTTON ACTION
        addMenuItemButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                try {
                    Restaurant.restaurant.createNewMenuItem(Double.parseDouble(priceTextField.getText()), nameTextField.getText());
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

                Restaurant.restaurant.refreshTableData(adminView.getTableMenuItems());
                panel1.repaint();

            }
        });

        // EDIT BUTTON ACTION
        editMenuItemButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                int row = adminView.getTableMenuItems().getSelectedRow();

                String[] cells = new String[2];

                for(int i = 0; i < cells.length ; i++)
                {
                    if( adminView.getTableMenuItems().getModel().getValueAt(row,i) != null)
                    {
                        cells[i] = adminView.getTableMenuItems().getModel().getValueAt(row,i).toString();
                    }
                }

                Restaurant.restaurant.editMenuItem(Double.valueOf(cells[1]),cells[0]);

                Restaurant.restaurant.refreshTableData(adminView.getTableMenuItems());
                panel1.repaint();

            }
        });

        // DELETE BUTTON ACTION
        deleteMenuItemButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                int row = adminView.getTableMenuItems().getSelectedRow();
                String nameMenuItem = adminView.getTableMenuItems().getModel().getValueAt(row,0).toString();

                MenuItem menuItem = new MenuItem();
                menuItem.setName(nameMenuItem);

                Restaurant.restaurant.deleteMenuItem(menuItem);
                Restaurant.restaurant.refreshTableData(adminView.getTableMenuItems());
                panel1.repaint();
            }
        });

        // VIEW ALL BUTTON ACTION
        viewAllMenuItemsButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                {

//                    //transmitem tablul nostru ca parametru in clientController, pentru a adauga noii clienti si apoi il setam
//                    clientController.refreshTableData(new LinkedList<>(clientController.viewAllClients()), clientsView.getTableClients());
                    panel1.repaint();

                }
            }
        });


        // BACK BUTTON ACTION
        back.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                MainFrame mainFrame = new MainFrame();
                adminView.setVisible(false);
            }
        });

        panel2.revalidate();
        panel2.updateUI();
        panel2.repaint();
    }



    public void actionPerformed(ActionEvent e) {

    }
}
