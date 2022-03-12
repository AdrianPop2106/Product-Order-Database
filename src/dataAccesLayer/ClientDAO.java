package dataAccesLayer;

import model.Client;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clasa ClientDAO extinde clasa GenericDAO<Client></Client>, aceasta continand metode folosite pentru interogari
 * si operatii pentru tabela Clients
 */
public class ClientDAO extends GenericDAO<Client>{

    public ClientDAO(){
        super();
    }

    /**
     * se apeleaza operatia de introducere a unei noi tuple care are ca si informatie continutul array-ului de String-uri
     * arguments
     * @param arguments
     */
    public void insertClient(String[] arguments){
        try {
            Client in=type.getConstructor(String.class,String.class,String.class,String.class,String.class).newInstance(arguments[0],arguments[1],arguments[2],arguments[3],arguments[4]);
        Connection conn= ConnectionFactory.getConnection();
        PreparedStatement insertStatement=null;
        try{
            int id=this.max()+1;
            in.setId(id);
            insertStatement=conn.prepareStatement("insert into clients(id,FirstName,LastName,phone,address,bank) values("+in.getId()+",\""+in.getFirstName()+"\",\""+in.getLastName()+"\",\""+in.getPhone()+"\",\""+in.getAddress()+"\",\""+in.getBank()+"\");");
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
     * se apeleaza o interogare prin care se afla informatiile de pe coloanele firstName si lastName ale fiecarei
     * tuple din table Clients si se returneaza un array de String-uri ce reprezinta numele complet al fiecarui
     * client
     * @return
     */
    public String[] getClients() {
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement nameStatement = null;
        ResultSet rs = null;
        int i = 1;
        String[] names = new String[number()+1];
        names[0]="Select client";
        try {
            nameStatement = conn.prepareStatement("select id,FirstName,LastName\n" +
                    "from clients;");
            rs = nameStatement.executeQuery();
            while (rs.next()) {
                names[i] = rs.getString(1) + "." + rs.getString(2)+" "+rs.getString(3);
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
