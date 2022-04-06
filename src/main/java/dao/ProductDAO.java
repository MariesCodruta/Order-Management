package dao;

import connection.ConnectionFactory;
import model.Product;
import presentation.Controller;

import javax.swing.table.DefaultTableModel;
import java.sql.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductDAO {
    protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO product (idproduct,name,quantity,price)" + " VALUES (?,?,?,?)";
    private final static String findStatementString = "SELECT * FROM product WHERE name = ?";
    private final static String viewStatementString = "SELECT * FROM product";
    private final static String deleteStatementString = "DELETE FROM product WHERE idproduct = ?";
    private final static String updateStatementString = "UPDATE product SET name = ?, quantity = ?, price = ? WHERE idproduct = ?";

    /*
     * Insert a product in the database.
     * @param p product object whose data must be inserted
     * @return the index in which it was inserted
     */

    public static int insert(int id,Product product) {
        Connection connection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        try {
            insertStatement = connection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1,id);
            insertStatement.setString(2, product.getName());
            insertStatement.setInt(3, product.getQuantity());
            insertStatement.setDouble(4, product.getPrice());
            insertStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(connection);
        }
        return 0;
    }

    /*
     * @return the data to fill in the JTable
     * @throws SQLException
     */

    public static DefaultTableModel buildTableModel() throws SQLException {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement viewStatement = null;
        ResultSet rs = null;
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        Vector<String> columnNames = null;
        try {
            viewStatement = connection.prepareStatement(viewStatementString);
            rs = viewStatement.executeQuery();
            ResultSetMetaData metaData = rs.getMetaData();
            columnNames = new Vector<String>();
            int columnCount = metaData.getColumnCount();
            for (int column = 1; column <= columnCount; column++) {
                columnNames.add(metaData.getColumnName(column));
            }

            // data of the table
            while (rs.next()) {
                Vector<Object> vector = new Vector<Object>();
                for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                    vector.add(rs.getObject(columnIndex));
                }
                data.add(vector);
            }
        } catch(Exception e){

        }
        // names of columns

        return new DefaultTableModel(data, columnNames);

    }

    public static void fillComboBox(){
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement viewStatement = null;
        ResultSet rs = null;
        try {
            viewStatement = connection.prepareStatement(viewStatementString);
            rs = viewStatement.executeQuery();
            while(rs.next()){
                Controller.productbox.addItem(rs.getString(2));
            }
        }catch(SQLException e){
        }finally {
            ConnectionFactory.close(viewStatement);
            ConnectionFactory.close(connection);
        }
    }

    /*
     * Updates the fields in a specific row.
     * @param id the index in which the field must be updated
     * @param product product object with whose data the field must be updated
     */

    public static void update(int id, Product product){
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement updateStatement = null;
        try {
            updateStatement = connection.prepareStatement(updateStatementString);
            updateStatement.setString(1, product.getName());
            updateStatement.setInt(2, product.getQuantity());
            updateStatement.setDouble(3, product.getPrice());
            updateStatement.setInt(4, id);
            System.out.println(updateStatement);
            updateStatement.execute();

        }catch(SQLException e){
            LOGGER.log(Level.WARNING, "ClientDAO : update " + e.getMessage());
        }finally {
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(connection);
        }
    }

    /*
     * Deletes the product with id given.
     * @param id the id by which we select which row to delete
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

    /*
     * Selects the product with the name given.
     * @param name the name by which we search the product object
     * @return the product object with name given
     */

    public static Product findByName(String name) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null; Product p = null;
        try {
            findStatement = connection.prepareStatement(findStatementString);
            findStatement.setString(1, name);
            rs = findStatement.executeQuery();
            rs.next();

            int id = rs.getInt(1);
            String name2 = rs.getString(2);
            int quantity = rs.getInt(3);
            double price = rs.getDouble(4);
            p = new Product(id, name2,price,quantity);

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"ClientDAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(connection);
        }
        return p;
    }

    public static List<Product> findAll()
    {
        List<Product> list = new ArrayList<Product>();
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement selectAllStatement = null;
        ResultSet rs = null;
        try{
            selectAllStatement = dbConnection.prepareStatement(viewStatementString,Statement.RETURN_GENERATED_KEYS);
            selectAllStatement.executeQuery();
            rs = selectAllStatement.executeQuery();

            while(rs.next())
            {
                int id = rs.getInt(1);
                int quantity = rs.getInt(3);
                int price = rs.getInt(4);
                String name = rs.getString(2);

                list.add(new Product(id,name,price,quantity));
            }


        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"ProductDAO:findAll");
        }
        finally {
            ConnectionFactory.close(selectAllStatement);
            ConnectionFactory.close(dbConnection);
        }
        return list;
    }
}
