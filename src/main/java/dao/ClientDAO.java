package dao;

import connection.ConnectionFactory;
import model.Client;
import presentation.Controller;

import javax.swing.table.DefaultTableModel;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientDAO {
    protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());


    private static final String insertStatementString = "INSERT INTO client (id,name,address,email)" + " VALUES (?,?,?,?)";
    private final static String findStatementString = "SELECT * FROM client WHERE name = ?";
    private final static String viewStatementString = "SELECT * FROM client";
    private final static String deleteStatementString = "DELETE FROM client WHERE id = ?";
    private final static String updateStatementString = "UPDATE client SET name = ?, address = ?, email = ? WHERE id = ?";

    /*
     * @param client client to be inserted in the database
     * @param id the index where the insert must take place
     * @return the index in which it was introduced
     */


    public static int insert(int id, Client client) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement insertStatement = null;
        try {
            insertStatement = connection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1, id);
            insertStatement.setString(2, client.getName());
            insertStatement.setString(3, client.getAddress());
            insertStatement.setString(4, client.getEmail());
            insertStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(connection);
        }
        return id;
    }

    /*
     * @param name name of the client selected in combobox that needs to be searched for in the database in order to use its data
     * @return an Client object with the name selected
     */


    public static Client findByName(String name) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null; Client client = null;
        try {
            findStatement = connection.prepareStatement(findStatementString);
            findStatement.setString(1, name);
            rs = findStatement.executeQuery();
            rs.next();

            int id = rs.getInt(1);
            String name2 = rs.getString(2);
            String address = rs.getString(3);
            String email = rs.getString(4);
            client = new Client(id, name2, address, email);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"ClientDAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(connection);
        }
        return client;
    }

    /*
     * @return the data to be inserted in the table
     * @throws SQLException
     */

    public static DefaultTableModel buildTableModel() throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement viewStatement = null;
        ResultSet resultSet = null;
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        Vector<String> columnNames = null;
        try {
            viewStatement = connection.prepareStatement(viewStatementString);
            resultSet = viewStatement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            columnNames = new Vector<String>();
            int columnCount = metaData.getColumnCount();
            for (int column = 1; column <= columnCount; column++) {
                columnNames.add(metaData.getColumnName(column));
            }
        for(String s : columnNames){
            System.out.println(s);
        }
            // data of the table
            while (resultSet.next()) {
                Vector<Object> vector = new Vector<Object>();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    vector.add(resultSet.getObject(columnIndex));
                }
                data.add(vector);
            }
        } catch(Exception e){

        }
        return new DefaultTableModel(data, columnNames);
    }


    public static List<Client> findAll()
    {
        List<Client> list = new ArrayList<Client>();
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement selectAllStatement = null;
        ResultSet rs = null;
        try{
            selectAllStatement = dbConnection.prepareStatement(insertStatementString,Statement.RETURN_GENERATED_KEYS);
            selectAllStatement.executeQuery();
            rs =  selectAllStatement.executeQuery();

            while(rs.next())
            {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String address = rs.getString(4);
                String email = rs.getString(3);
                list.add(new Client(id,name,address,email));
            }


        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"ClientDAO");
        }
        finally {
            ConnectionFactory.close(selectAllStatement);
            ConnectionFactory.close(dbConnection);
            ConnectionFactory.close(rs);
        }
        return list;
    }

    public static void fillComboBox(){
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement viewStatement = null;
        ResultSet resultSet = null;
        try {
            viewStatement = connection.prepareStatement(viewStatementString);
            resultSet = viewStatement.executeQuery();
            while(resultSet.next()){
                Controller.clientbox.addItem(resultSet.getString(2));
            }
        }catch(SQLException e){
        }finally {
            ConnectionFactory.close(viewStatement);
            ConnectionFactory.close(connection);
        }
    }

    /*
     * @param id the index where the update must take place
     * @param client the data from this client must be added in the database at index id
     */

    public static void update(int id, Client client){
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement updateStatement = null;
        try {
            updateStatement = connection.prepareStatement(updateStatementString);
            updateStatement.setString(1, client.getName());
            updateStatement.setString(2, client.getAddress());
            updateStatement.setString(3, client.getEmail());
            updateStatement.setInt(4, id);
            System.out.println(updateStatement);
            updateStatement.execute();

        }catch(SQLException e){
            LOGGER.log(Level.WARNING, "ClientDAO:update " + e.getMessage());
        }finally {
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(connection);
        }
    }

    /*
     * @param id the client with this id must be deleted
     */

    public static void delete(int id){
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement deleteStatement = null;
        try {
            deleteStatement = connection.prepareStatement(deleteStatementString);
            deleteStatement.setInt(1, id);
            System.out.println(deleteStatement);
            deleteStatement.execute();

        }catch(SQLException e){
            LOGGER.log(Level.WARNING, "ClientDAO:delete " + e.getMessage());
        }finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(connection);
        }
    }
}

