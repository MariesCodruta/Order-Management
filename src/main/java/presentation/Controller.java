package presentation;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import bll.ProductBLL;
import dao.ClientDAO;
import dao.OrderDAO;
import dao.ProductDAO;
import model.Client;
import model.Order;
import model.Product;

/*
 * Adds the action listeners for the component created in the view.
 * @param view
 */

public class Controller {
    private View view;
    public static JComboBox clientbox;
    public static JComboBox productbox;

    public Controller(View view){
        this.view = view;
        view.addClientsListener(new actionClients());
        view.addProductsListener(new actionProducts());
        view.addOrderListener(new actionOrder());
    }

    class actionProducts implements ActionListener{
        public void actionPerformed(ActionEvent a) {
            JFrame frame = new JFrame();
            frame.setBounds(100, 100, 815, 554);
            JPanel contentPane = new JPanel();
            contentPane.setBorder(new EmptyBorder(7, 7, 7, 7));
            contentPane.setLayout(new BorderLayout(0, 0));
            frame.setContentPane(contentPane);

            JPanel panel = new JPanel();
            contentPane.add(panel, BorderLayout.CENTER);
            panel.setLayout(null);

            JScrollPane table = new JScrollPane();
            table.setBounds(15, 62, 300, 200);
            panel.add(table);
            JLabel namestr = new JLabel("Nume:");
            namestr.setBounds(487, 105, 69, 20);
            panel.add(namestr);

            JTextField name = new JTextField();
            name.setBounds(564, 100, 204, 26);
            panel.add(name);
            name.setColumns(10);

            JLabel amountstr = new JLabel("Cantitate:");
            amountstr.setBounds(487, 140, 69, 20);
            panel.add(amountstr);

            JTextField amount = new JTextField();
            amount.setBounds(564, 140, 204, 26);
            panel.add(amount);
            amount.setColumns(10);

            JLabel pricestr = new JLabel("Pret:");
            pricestr.setBounds(487, 180, 69, 20);
            panel.add(pricestr);

            JTextField price = new JTextField();
            price.setBounds(564, 180, 204, 26);
            panel.add(price);
            price.setColumns(10);

            JButton insert = new JButton("Inserare produs");
            insert.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent arg0) {
                    String pname = name.getText();
                    int pamount = Integer.parseInt(amount.getText());
                    double pprice = Double.parseDouble(price.getText());
                    Product p = new Product(pname, pprice,pamount);
                    ProductBLL pbll = new ProductBLL();
                    if(pbll.insertProduct(p) < 0){
                        JOptionPane.showMessageDialog(frame, "Cantitatea nu poate fi negativa, introduceti un numar > 0!");
                    }
                    else{
                        ProductDAO.insert(p.getId(),p);
                        JOptionPane.showMessageDialog(frame, "Produsul a fost inserat cu succes");
                    }
                }
            });
            insert.setBounds(554, 241, 162, 29);
            insert.setBackground(Color.cyan);
            panel.add(insert);

            JButton viewp = new JButton("Afisati toate produsele");
            viewp.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent arg0) {
                    JTable table2;
                    try {
                        table2 = new JTable(ProductDAO.buildTableModel());
                        JScrollPane scrollpane = new JScrollPane(table2);
                        scrollpane.setBounds(15, 62, 448, 290);
                        panel.add(scrollpane);

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
            viewp.setBounds(148, 401, 162, 29);
            viewp.setBackground(Color.cyan);
            panel.add(viewp);

            productbox = new JComboBox();
            ProductDAO.fillComboBox();
            productbox.addItemListener(new ItemListener(){
                public void itemStateChanged(ItemEvent e) {
                    String name2 = (String)productbox.getSelectedItem();
                    Product p = ProductDAO.findByName(name2);
                    name.setText(p.getName());
                    amount.setText(String.valueOf(p.getQuantity()));
                    price.setText(String.valueOf(p.getPrice()));

                }
            });
            productbox.setBounds(528, 16, 220, 26);
            productbox.setBackground(Color.cyan);
            panel.add(productbox);

            JButton delete = new JButton("Stergere produs");
            delete.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent arg0) {
                    String name = (String)productbox.getSelectedItem();
                    Product p = ProductDAO.findByName(name);
                    ProductDAO.delete(p.getId());
                }
            });
            delete.setBounds(554, 401, 162, 29);
            delete.setBackground(Color.cyan);
            panel.add(delete);

            JButton update = new JButton("Actualizare produs");
            update.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent arg0) {
                    String pname = name.getText();
                    int pamount = Integer.parseInt(amount.getText());
                    double pprice = Double.parseDouble(price.getText());
                    Product p = new Product(pname, pprice,pamount);
                    String name = (String)productbox.getSelectedItem();
                    Product p2 = ProductDAO.findByName(name);
                    ProductBLL pbll = new ProductBLL();
                    if(pbll.insertProduct(p) < 0){
                        JOptionPane.showMessageDialog(frame, "Cantitatea nu poate fi negativa! Introduceti un numar >0!");
                    }
                    else {
                        ProductDAO.update(p2.getId(), p);
                        JOptionPane.showMessageDialog(frame, "Produsul a fost actualizat cu succes");
                    }

                }
            });
            update.setBounds(554, 323, 162, 29);
            update.setBackground(Color.cyan);
            panel.add(update);
            panel.setBackground(Color.magenta);
            frame.setVisible(true);
        }
    }
    class actionClients implements ActionListener{
        public void actionPerformed(ActionEvent a) {
            JFrame frame = new JFrame();
            frame.setBounds(100, 100, 815, 554);
            JPanel contentPane = new JPanel();
            contentPane.setBorder(new EmptyBorder(7, 7, 7, 7));
            contentPane.setLayout(new BorderLayout(0, 0));
            frame.setContentPane(contentPane);

            JPanel panel = new JPanel();
            contentPane.add(panel, BorderLayout.CENTER);
            panel.setLayout(null);

            JScrollPane table = new JScrollPane();
            table.setBounds(15, 62, 300, 200);
            panel.add(table);

            JLabel namestr = new JLabel("Nume:");
            namestr.setBounds(487, 105, 69, 20);
            panel.add(namestr);

            JTextField name = new JTextField();
            name.setBounds(564, 100, 204, 26);
            panel.add(name);
            name.setColumns(10);

            JLabel addressstr = new JLabel("Adresa:");
            addressstr.setBounds(487, 140, 69, 20);
            panel.add(addressstr);

            JTextField address = new JTextField();
            address.setBounds(564, 140, 204, 26);
            panel.add(address);
            address.setColumns(10);

            JLabel emailstr = new JLabel("Email:");
            emailstr.setBounds(487, 180, 69, 20);
            panel.add(emailstr);

            JTextField email = new JTextField();
            email.setBounds(564, 180, 204, 26);
            panel.add(email);
            email.setColumns(10);

            JButton insert = new JButton("Adaugati clientul ");
            insert.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent arg0) {
                    String cname = name.getText();
                    String caddress = address.getText();
                    String cemail = email.getText();
                    Client client = new Client(cname, caddress, cemail);
                    ClientDAO.insert(client.getId(),client);
                    JOptionPane.showMessageDialog(frame, "Clientul a fost adaugat cu succes!");
                }
            });
            insert.setBounds(554, 340, 162, 29);
            insert.setBackground(Color.cyan);
            panel.add(insert);

            JButton viewc = new JButton("Afisati toti clientii");
            viewc.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent arg0) {
                    JTable table2;
                    try {
                        table2 = new JTable(ClientDAO.buildTableModel());
                        JScrollPane scrollpane = new JScrollPane(table2);
                        scrollpane.setBounds(15, 62, 350, 270);
                        panel.add(scrollpane);

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            });
            viewc.setBounds(148, 401, 162, 29);
            viewc.setBackground(Color.cyan);
            panel.add(viewc);

            clientbox = new JComboBox();
            ClientDAO.fillComboBox();
            clientbox.addItemListener(new ItemListener(){
                public void itemStateChanged(ItemEvent e) {
                    String name2 = (String)clientbox.getSelectedItem();
                    Client client = ClientDAO.findByName(name2);
                    name.setText(client.getName());
                    address.setText(client.getAddress());
                    email.setText(client.getEmail());

                }
            });
            clientbox.setBounds(500, 16, 220, 26);
            clientbox.setBackground(Color.cyan);
            panel.add(clientbox);

            JButton delete = new JButton("Stergere client");
            delete.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent arg0) {
                    String name = (String)clientbox.getSelectedItem();
                    Client client = ClientDAO.findByName(name);
                    ClientDAO.delete(client.getId());
                }
            });
            delete.setBounds(554, 465, 162, 29);
            delete.setBackground(Color.cyan);
            panel.add(delete);

            JButton update = new JButton("Actualizare client");
            update.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent arg0) {
                    String cname = name.getText();
                    String caddress = address.getText();
                    String cemail = email.getText();
                    Client client = new Client(cname, caddress, cemail);
                    String name = (String)clientbox.getSelectedItem();
                    Client client2= ClientDAO.findByName(name);

                    ClientDAO.update(client2.getId(), client);
                    JOptionPane.showMessageDialog(frame, "Clientul a fost actualizat cu succes");
                }
            });
            update.setBounds(554, 400, 162, 29);
            update.setBackground(Color.cyan);
            panel.setBackground(Color.magenta);
            panel.add(update);

            frame.setVisible(true);
        }
    }

    class actionOrder implements ActionListener{
        public void actionPerformed(ActionEvent a) {
            JFrame frame = new JFrame();
            frame.setBounds(100, 100, 790, 509);
            JPanel contentPane = new JPanel();
            contentPane.setBorder(new EmptyBorder(7, 7, 7, 7));
            contentPane.setLayout(new BorderLayout(0, 0));
            frame.setContentPane(contentPane);

            JPanel panel = new JPanel();
            contentPane.add(panel, BorderLayout.CENTER);
            panel.setLayout(null);

            JLabel quantitystr = new JLabel("Cantitate:");
            quantitystr.setBounds(268, 203, 69, 20);
            panel.add(quantitystr);

            JTextField amount = new JTextField();
            amount.setBounds(354, 200, 204, 26);
            panel.add(amount);
            amount.setColumns(10);

            productbox = new JComboBox();
            ProductDAO.fillComboBox();
            productbox.setBounds(413, 99, 271, 26);
            productbox.setBackground(Color.cyan);
            panel.add(productbox);

            clientbox = new JComboBox();
            ClientDAO.fillComboBox();
            clientbox.setBounds(76, 99, 271, 26);
            clientbox.setBackground(Color.cyan);
            panel.add(clientbox);

            JButton sorder = new JButton("Plasare comanda!");
            sorder.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    String idClient = (String) clientbox.getSelectedItem();
                    String idProduct = (String) productbox.getSelectedItem();
                    int amount2 = Integer.parseInt(amount.getText());
                    Product p = ProductDAO.findByName(idProduct);
                    Product p2 = new Product(p.getName(), p.getPrice(),p.getQuantity() - amount2);
                    ProductBLL pbll = new ProductBLL();
                    if(pbll.insertProduct(p2) < 0 || amount2 < 0)
                        JOptionPane.showMessageDialog(frame, "Nu sunt suficiente produse in stoc");
                    else{
                        Order o = new Order(idClient, idProduct, amount2);
                        OrderDAO.insert(o.getId(),o);
                        o.writeFile();
                        JOptionPane.showMessageDialog(frame, "Comanda a fost plasata cu succes!");
                        ProductDAO.update(p.getId(), p2);
                    }
                }
            });
            sorder.setBounds(306, 307, 173, 51);
            sorder.setBackground(Color.cyan);
            panel.add(sorder);

            JLabel lblSelectClient = new JLabel("Selectati clientul :");
            lblSelectClient.setBounds(167, 63, 117, 20);
            lblSelectClient.setBackground(Color.cyan);
            panel.add(lblSelectClient);

            JLabel lblSelectProduct = new JLabel("Selectati produsul :");
            lblSelectProduct.setBounds(495, 63, 117, 20);
            lblSelectProduct.setBackground(Color.cyan);
            panel.add(lblSelectProduct);
            panel.setBackground(Color.magenta);
            frame.setVisible(true);
        }
    }
}
