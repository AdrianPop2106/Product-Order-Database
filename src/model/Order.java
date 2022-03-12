package model;

/**
 * Clasa Order este folosita pentru a reprezenta un membru al tabelei Orders. Aceasta prezinta atribute
 * similare cu coloanele tabelei si contine metode de tip getter si setter pentru a obtine si returna
 * informatia stocata de instanta clasei
 */
public class Order {
    int id;
    int clientId;
    int productId;
    int quantity;
    int total;

    public Order(int clientId,int productId,int quantity, int total){
        this.clientId=clientId;
        this.productId=productId;
        this.quantity=quantity;
        this.total=total;
    }
    /**returneaza valoarea atributului id*/
    public int getId() {
        return id;
    }
    /**returneaza valoarea atributului clientId*/
    public int getClientId() {
        return clientId;
    }
    /**returneaza valoarea atributului total*/
    public int getTotal() {
        return total;
    }
    /**seteaza o noua valoare atributului id*/
    public void setId(int id) {
        this.id = id;
    }
    /**returneaza valoarea atributului productId*/
    public int getProductId() { return productId; }
    /**returneaza valoarea atributului quantity*/
    public int getQuantity() { return quantity; }

}
