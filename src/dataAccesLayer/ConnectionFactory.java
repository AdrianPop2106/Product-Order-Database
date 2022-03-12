package dataAccesLayer;

import java.sql.*;
import java.util.logging.Logger;

/**
 * Clasa ConnectionFactory este folosita pentru a realiza o conexiune cu baza de date. Se foloseste de String-urile
 * din atribute ca sa stabileasca legatura cu baza de date ordermanagement. Deasemenea mai prezinta si metode pentru
 * inchiderea conexiunilor, a instantelor din clasa Statement si a instantelor din clasa ResultSet
 */
public class ConnectionFactory {
    private static final Logger LOGGER=Logger.getLogger(ConnectionFactory.class.getName());
    private static final String DRIVER="com.mysql.cj.jdbc.Driver";
    private static final String DBURL="jdbc:mysql://localhost:3306/ordermanagement";
    private static final String USER="root";
    private static final String PASS="dragon0000";

    private static ConnectionFactory singleInstance=new ConnectionFactory();

    private ConnectionFactory(){
        try{
            Class.forName(DRIVER);
        }catch(ClassNotFoundException e){
            System.err.println("An Exception occured during JDBC Driver loading." +
                    " Details are provided below:");
            e.printStackTrace(System.err);
        }
    }

    /**
     * se creaza o conexiune si se returneaza conexiunea creata
     * @return
     */
    private Connection createConnection(){
        Connection c=null;
        try {
            c= DriverManager.getConnection(DBURL,USER,PASS);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return c;
    }

    /**
     * se returneaza o conexiune nou creata
     * @return
     */
    public static Connection getConnection(){
        Connection c= singleInstance.createConnection();
        return c;
    }

    /**
     * se inchide conexiunea trimisa ca parametru
     * @param connection
     * @throws SQLException
     */
    public static void close(Connection connection) throws SQLException {
        connection.close();
    }

    /**
     * se inchide obiectul din clasa Statement trimis ca parametru
     * @param statement
     * @throws SQLException
     */
    public static void close(Statement statement) throws SQLException {
        statement.close();
    }

    /**
     * se inchide obiectul din clasa ResultSet trimis ca parametru
     * @param resultSet
     * @throws SQLException
     */
    public static void close(ResultSet resultSet) throws SQLException {
        resultSet.close();
    }
}
