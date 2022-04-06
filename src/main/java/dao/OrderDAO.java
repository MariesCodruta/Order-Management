package dao;

import connection.ConnectionFactory;
import model.Order;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Insert an order orders table from database.
 * @param order the order to be added in the database
 * @param id the index where the insert must take place
 * @return the index in which it was inserted
 */

public class OrderDAO {

    protected static final Logger LOGGER = Logger.getLogger(OrderDAO.class.getName());

    private static final String insertStatementString = "INSERT INTO orders (id,clientname,productname,quantity)" + " VALUES (?,?,?,?)";
    private static final String selectStatementString = "SELECT * FROM orders ORDER BY id DESC LIMIT 1";

    public static int insert(int id,Order order) {
        Connection connection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        try {
            insertStatement = connection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setInt(1,id);
            insertStatement.setString(2, order.getIdClient());
            insertStatement.setString(3, order.getIdProduct());
            insertStatement.setInt(4, order.getQuantity());
            insertStatement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(connection);
        }
        return 0;
    }
}
