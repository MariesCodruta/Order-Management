package bll;

import bll.validators.Validator;
import model.Client;

import java.util.ArrayList;
import java.util.List;

import bll.validators.EmailValidator;
import dao.ClientDAO;

public class ClientBLL {

    private List<Validator<Client>> validators;

    /*
     * Contructor of the business layer for client.
     */

    public ClientBLL(){
        validators = new ArrayList<Validator<Client>>();
        validators.add(new EmailValidator());
    }

    /*
     * Checks before inserting a client if all its fields are valid.
     * @param client client to be checked if valid
     * @param id int to be checked if valid
     * @return
     */

    public int insert(int id,Client client) {
        for (Validator<Client> validator : validators) {
            validator.validate(client);
        }
        return ClientDAO.insert(id,client);
    }
    /*public Client findClientByName(String name) {
        Client client = ClientDAO.findByName(name);
        if (client == null) {
            throw new NoSuchElementException("Clientul cu numele =" + name + " nu a fost gasit!");
        }
        return client;
    }*/
}
