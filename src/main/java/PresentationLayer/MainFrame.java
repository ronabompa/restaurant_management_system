package PresentationLayer;

import BusinessLayer.Restaurant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Rona Dumitrescu on 23.05.2019.
 */
public  class MainFrame extends JFrame implements ActionListener{

    private JPanel panel1;

    private JButton administrtorButton;
    private JButton chefButton;
    private JButton waiterButton;

   public static ChefGraphicalUserInterface chefFrame = new ChefGraphicalUserInterface();

    public ChefGraphicalUserInterface getChefFrame() {
        return chefFrame;
    }

    // MAIN FRAME
    public MainFrame()
    {
        super("MAIN WINDOW");
        setLayout(new FlowLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(360, 100);
        this.setLocationRelativeTo(null);  // to center it

        //PANEL
        panel1 = new JPanel();
        panel1.setLayout(new FlowLayout());

        //BUTTONS
        administrtorButton = new JButton();
        administrtorButton.setText("Administrator");

        chefButton = new JButton();
        chefButton.setText("Chef");

        waiterButton = new JButton();
        waiterButton.setText("Waiter");

        // ADD PANEL TO FRAME
        add(panel1);

        // ADD COMPONENTS TO PANEL
        panel1.add(administrtorButton);
        panel1.add(chefButton);
        panel1.add(waiterButton);



        //ACTION LISTENERS

        administrtorButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {

                AdministratorGraphicalUserInterface administratorFrame = new AdministratorGraphicalUserInterface();
                administratorFrame.setVisible(true);


            }
        });

        chefButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {


                chefFrame.setVisible(true);

            }
        });

        waiterButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {


                WaiterGraphicalUserInterface waiterFrame = new WaiterGraphicalUserInterface();
                waiterFrame.setVisible(true);

            }
        });

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {

                Restaurant.restaurant.close();
                JOptionPane.showMessageDialog(null,"Totul a fost salvat");
                e.getWindow().dispose();

            }
        });
    }


    public void actionPerformed(ActionEvent e) {

    }
}
