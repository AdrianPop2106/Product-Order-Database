package model;
/**
 * Clasa Product este folosita pentru a reprezenta un membru al tabelei Products. Aceasta prezinta atribute
 * similare cu coloanele tabelei si contine metode de tip getter si setter pentru a obtine si returna
 * informatia stocata de instanta clasei
 */
public class Product {
    int id;
    String product;
    String producer;
    int price;
    int stock;

    public Product(String productName,String producer,int price,int stock){
        id=0;
        this.product =productName;
        this.producer=producer;
        this.price=price;
        this.stock=stock;
    }
    /**seteaza o noua valoare atributului id*/
    public void setId(int id) {
        this.id = id;
    }
    /**returneaza valoarea atributului id*/
    public int getId() {
        return id;
    }
    /**returneaza valoarea atributului price*/
    public int getPrice() {
        return price;
    }
    /**returneaza valoarea atributului stock*/
    public int getStock() {
        return stock;
    }
    /**returneaza valoarea atributului producer*/
    public String getProducer() {
        return producer;
    }
    /**returneaza valoarea atributului product*/
    public String getProduct() {
        return product;
    }
}
