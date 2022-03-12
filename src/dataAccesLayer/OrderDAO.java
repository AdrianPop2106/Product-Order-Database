package dataAccesLayer;

import model.Order;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Clasa OrderDAO extinde clasa GenericDAO<Order></Order>, aceasta continand metode folosite pentru interogari
 * si operatii pentru tabela Orders
 */
public class OrderDAO extends GenericDAO<Order>{
    public OrderDAO(){ super(); }
    /**
     * se apeleaza operatia de introducere a unei noi tuple care are ca si informatie continutul array-ului de String-uri
     * arguments
     * @param arguments
     */
    public Order insertOrder(String[] arguments){
        Order in=null;
        try {
            in=type.getConstructor(int.class,int.class,int.class,int.class).newInstance(Integer.parseInt(arguments[0]),Integer.parseInt(arguments[1]),Integer.parseInt(arguments[2]),Integer.parseInt(arguments[3]));
            Connection conn= ConnectionFactory.getConnection();
            PreparedStatement insertStatement=null;
            try{
                int id=this.max()+1;
                in.setId(id);
                insertStatement=conn.prepareStatement("insert into orders(id,clientId,productId,quantity,total) values("+in.getId()+",\""+in.getClientId()+"\",\""+in.getProductId()+"\","+in.getQuantity()+","+in.getTotal()+");");
                insertStatement.executeUpdate();
                ConnectionFactory.close(insertStatement);
                ConnectionFactory.close(conn);
            }catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (NoSuchMethodException | IllegalAccessException  | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return in;
    }
}
