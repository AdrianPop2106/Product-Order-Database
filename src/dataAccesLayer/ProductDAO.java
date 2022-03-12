package dataAccesLayer;

import model.Product;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clasa ProductDAO extinde clasa GenericDAO<Product></Product>, aceasta continand metode folosite pentru interogari
 * si operatii pentru tabela Products
 */
public class ProductDAO extends GenericDAO<Product>{
    public ProductDAO(){
        super();
    }

    /**
     * se apeleaza operatia de introducere a unei noi tuple care are ca si informatie continutul array-ului de String-uri
     * arguments
     * @param arguments
     */
    public void insertProduct(String[] arguments){
        try {
            Product in=type.getConstructor(String.class,String.class,int.class,int.class).newInstance(arguments[0],arguments[1],Integer.parseInt(arguments[2]),Integer.parseInt(arguments[3]));
            Connection conn= ConnectionFactory.getConnection();
            PreparedStatement insertStatement=null;
            try{
                int id=this.max()+1;
                in.setId(id);
                insertStatement=conn.prepareStatement("insert into products(id,product,producer,price,stock) values("+in.getId()+",\""+in.getProduct()+"\",\""+in.getProducer()+"\","+in.getPrice()+","+in.getStock()+");");
                insertStatement.executeUpdate();
                ConnectionFactory.close(insertStatement);
                ConnectionFactory.close(conn);
            }catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } catch (NoSuchMethodException | IllegalAccessException  | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    /**
     * se apeleaza o interogare prin care se afla informatiile de pe coloana product ale fiecarei
     * tuple din tabela Product si se returneaza un array de String-uri ce reprezinta numele fiecarui produs
     * @return
     */
    public String[] getProducts() {
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement nameStatement = null;
        ResultSet rs = null;
        int i = 1;
        String[] names = new String[number()+1];
        names[0]="Select product";
        try {
            nameStatement = conn.prepareStatement("select id,product\n" +
                    "from products;");
            rs = nameStatement.executeQuery();
            while (rs.next()) {
                names[i] = rs.getString(1) + "." + rs.getString(2);
                i++;
            }
            ConnectionFactory.close(nameStatement);
            ConnectionFactory.close(conn);
            ConnectionFactory.close(rs);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return names;
    }

}
