package model;

/*
 * The class Client instatiates object of this type in order to be added to the database.
 * @param id client id
 * @param email email address of the client
 * @param name  name of the client
 * @param address  address of the client
 */

public class Client {


    private int id;
    private String email;
    private String name;
    private String address;


    public Client(int id, String name, String address, String email){
        this.id = id;
        this.email = email;
        this.name = name;
        this.address = address;
    }

    public Client(String name, String address, String email){
        this.email = email;
        this.name = name;
        this.address = address;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

}
