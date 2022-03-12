package model;

/**
 * Clasa Client este folosita pentru a reprezenta un membru al tabelei Clients. Aceasta prezinta atribute
 * similare cu coloanele tabelei si contine metode de tip getter si setter pentru a obtine si returna
 * informatia stocata de instanta clasei
 */
public class Client {
    private int id;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    private String bank;

    public Client(String fName, String lName, String phone, String address, String bank){
        id=0;
        firstName =fName;
        lastName=lName;
        this.phone=phone;
        this.address=address;
        this.bank=bank;
    }
    /** returneaza atributul id*/
    public int getId() {
        return id;
    }
    /** returneaza atributul firstName*/
    public String getFirstName() {
        return firstName;
    }
    /** returneaza atributul lastName*/
    public String getLastName() {
        return lastName;
    }
    /** returneaza atributul phone*/
    public String getPhone() {
        return phone;
    }
    /** returneaza atributul address*/
    public String getAddress() {
        return address;
    }
    /** returneaza atributul bank*/
    public String getBank() {
        return bank;
    }
    /** seteaza o noua valoare pentru atributul id*/
    public void setId(int id) {
        this.id = id;
    }

}
