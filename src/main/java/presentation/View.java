package presentation;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

import java.awt.event.ActionListener;

/*
 * The components of the main frame.
 */

public class View extends JFrame{
    private JButton clients;
    private JButton products;
    private JButton orders;
    private JPanel contentPane = new JPanel();

    public View(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 681, 459);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.CENTER);
        panel.setLayout(null);

        clients = new JButton("Meniu clienti");
        clients.setBounds(15, 172, 158, 51);
        clients.setBackground(Color.CYAN);
        panel.add(clients);

        products = new JButton("Meniu produse");
        products.setBounds(233, 172, 167, 51);
        products.setBackground(Color.CYAN);
        panel.add(products);

        orders = new JButton("Meniu comanda");
        orders.setBounds(453, 172, 167, 51);
        orders.setBackground(Color.CYAN);
        panel.add(orders);
        panel.setBackground(Color.magenta);
    }

    public void addClientsListener(ActionListener a){
        clients.addActionListener(a);
    }

    public void addProductsListener(ActionListener a){
        products.addActionListener(a);
    }

    public void addOrderListener(ActionListener a){
        orders.addActionListener(a);
    }

}
