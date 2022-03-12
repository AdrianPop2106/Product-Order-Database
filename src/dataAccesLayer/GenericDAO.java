package dataAccesLayer;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clasa GenericDAO<T></T> are rolul de-a indeplinii rolul de realizare a interogarilor ce pot fi realizate pe oricare
 * dintre tabelele din baza de date. Genericul T poate fi clasele Client, Order sau Product
 * @param <T>
 */
public class GenericDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(GenericDAO.class.getName());
    protected final Class<T> type;
    private static String maxString = "select max(id) as lastMember from ";
    private static String totalString = "select count(id) as total from ";
    private static String columnNumberString="select count(COLUMN_NAME) as total from INFORMATION_SCHEMA.COLUMNS where TABLE_SCHEMA=\"ordermanagement\" AND TABLE_NAME=?";

    @SuppressWarnings("unchecked")
    public GenericDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    /**
     * se va apela o interogare prin care afla numarul total de coloane ale tabelei reprezentate de T si se returneaza
     * numarul total
     * @return
     */
    private int getNumberColumns(){
        int total=0;
        Connection conn=ConnectionFactory.getConnection();
        PreparedStatement totalStatement=null;
        ResultSet rs=null;
        try {
            totalStatement= conn.prepareStatement(columnNumberString);
            totalStatement.setString(1,type.getSimpleName()+"s");
            rs=totalStatement.executeQuery();
            rs.next();
            total=rs.getInt("total");
            ConnectionFactory.close(rs);
            ConnectionFactory.close(totalStatement);
            ConnectionFactory.close(conn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            LOGGER.log(Level.WARNING,type.getName()+"DAO:getNumberColumns"+throwables.getMessage());
        }
        return total;
    }

    /**
     * se apeleaza o interogare prin care se cauta informatia ce se afla pe coloana column din tabela reprezentata
     * de T pentru tupla desemnata de id si se returneaza un String cu informatia respectiva
     * @param column
     * @param id
     * @return
     */
    public String idSearch(String column,int id){
        String value=null;
        Connection conn=ConnectionFactory.getConnection();
        PreparedStatement idStatement=null;
        ResultSet rs=null;
        try {
            idStatement=conn.prepareStatement("Select "+column+" from "+type.getSimpleName()+"s where id="+id+";");
            rs=idStatement.executeQuery();
            rs.next();
            value=rs.getString(column);
            ConnectionFactory.close(rs);
            ConnectionFactory.close(idStatement);
            ConnectionFactory.close(conn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            LOGGER.log(Level.WARNING,type.getName()+"DAO:getNumberColumns"+throwables.getMessage());
        }
        return value;
    }

    /**
     * se apeleaza o operatie de stergere pentru tupla desemnata de id din tabela reprezentata de clasa T
     * @param id
     */
    public void delete(int id) {
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement deleteStatement = null;
        try {
            deleteStatement = conn.prepareStatement("delete from " + type.getSimpleName() + "s where id=" + id);
            deleteStatement.executeUpdate();
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(conn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            LOGGER.log(Level.WARNING,type.getName()+"DAO:getNumberColumns"+throwables.getMessage());
        }
    }

    /**
     * se apeleaza o operatie de inlocuire cu informatia newValue pentru coloana column a tuplei id dim
     * tabela reprezentata de clasa T
     * @param id
     * @param column
     * @param newValue
     */
    public void edit(int id,String column,String newValue){
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement editStatement = null;
        try {
            editStatement = conn.prepareStatement("update "+type.getSimpleName()+"s set "+column+"=\""+newValue+"\" where id="+id+";");
            editStatement.executeUpdate();
            ConnectionFactory.close(editStatement);
            ConnectionFactory.close(conn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            LOGGER.log(Level.WARNING,type.getName()+"DAO:getNumberColumns"+throwables.getMessage());
        }
    }

    /**
     * se apeleaza o interogare pentru aflarea id-ului maxim din tabela reprezentata de clasa T si se returneaza
     * valoarea respectiva
     * @return
     */
    protected int max(){
        int maximum=0;
        Connection conn=ConnectionFactory.getConnection();
        PreparedStatement maximumStatement=null;
        ResultSet rs=null;
        try {
            maximumStatement=conn.prepareStatement(maxString+" "+type.getSimpleName()+"s;");
            rs=maximumStatement.executeQuery();
            rs.next();
            maximum=rs.getInt("lastMember");
            ConnectionFactory.close(rs);
            ConnectionFactory.close(maximumStatement);
            ConnectionFactory.close(conn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            LOGGER.log(Level.WARNING,type.getName()+"DAO:getNumberColumns"+throwables.getMessage());
        }
        return maximum;
    }

    /**
     * se apeleaza o interogare prin care se afla numarul total de tuple ale tabelei reprezentate de clasa T si se
     * returneaza valoarea respectiva
     * @return
     */
    public int number(){
        int total=0;
        Connection conn=ConnectionFactory.getConnection();
        PreparedStatement totalStatement=null;
        ResultSet rs=null;
        try {
            totalStatement=conn.prepareStatement(totalString+" "+type.getSimpleName()+"s;");
            rs=totalStatement.executeQuery();
            rs.next();
            total=rs.getInt("total");
            ConnectionFactory.close(rs);
            ConnectionFactory.close(totalStatement);
            ConnectionFactory.close(conn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            LOGGER.log(Level.WARNING,type.getName()+"DAO:getNumberColumns"+throwables.getMessage());
        }
        return total;
    }

    /**
     * se apeleaza o interogare prin care se afla toate valorile fiecarei tuple din tabela reprezentata de clasa T
     * si se returneaza sub forma unei matrici de String-uri
     * @return
     */
    public String[][] getEverything(){
        Connection conn = ConnectionFactory.getConnection();
        PreparedStatement getStatement = null;
        ResultSet rs=null;
        int columns=getNumberColumns(),rows=number();
        String[][] s=new String[rows][columns];
        try {
            getStatement = conn.prepareStatement("Select * from "+type.getSimpleName()+"s;");
            rs=getStatement.executeQuery();
            for(int i=0;i<rows;i++){
                rs.next();
                String[] temp=new String[columns];
                for(int j=0;j<columns;j++){
                    temp[j]=rs.getString(j+1);
                }
                s[i]=temp;
            }
            ConnectionFactory.close(getStatement);
            ConnectionFactory.close(conn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            LOGGER.log(Level.WARNING,type.getName()+"DAO:getNumberColumns"+throwables.getMessage());
        }
        return s;
    }

}
