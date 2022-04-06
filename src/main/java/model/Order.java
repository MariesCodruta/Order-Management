package model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/*
 * Contructor for Order objects.
 * @param id order if
 * @param idClient name of the client who makes the order
 * @param idProduct name of the product ordered
 * @param quantity quantity of the product ordered
 */

public class Order {

    private int id;
    private String idClient;
    private String idProduct;
    private int quantity;


    public Order(int id, String idClient, String idProduct, int quantity) {
        this.id = id;
        this.idClient = idClient;
        this.idProduct = idProduct;
        this.quantity = quantity;
    }

    public Order(String idClient, String idProduct, int quantity) {
        this.idClient = idClient;
        this.idProduct = idProduct;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void writeFile(){
        String fileName = "Order"+id;
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writer.write(this.toString() + " \nData: " + LocalDateTime.now());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "Client Name ='" + idClient + '\'' +
                ", Product Name ='" + idProduct + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
