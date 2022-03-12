package presentation;

import businessLayer.InfoValidator;
import dataAccesLayer.ClientDAO;
import dataAccesLayer.OrderDAO;
import dataAccesLayer.ProductDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Clasa OrderPage este folosita pentru creearea unei ferestre grafice din care se vor putea creea comenzi
 * ce vor fi stocate in tabela Orders
 */
public class OrderPage extends JFrame {
    JComboBox clients;
    JComboBox products;
    JLabel stock;
    int stockValue;

    public OrderPage(){
        stockValue=0;
        this.setLocationRelativeTo(null);
        JPanel p=(JPanel)this.getContentPane();

        JPanel clientPan=new JPanel();
        clients=new JComboBox(new ClientDAO().getClients());
        clients.setMaximumRowCount(4);
        clientPan.add(clients);

        JPanel productPan=new JPanel();
        products=new JComboBox(new ProductDAO().getProducts());
        products.setMaximumRowCount(4);
        stock=getProductLabel();
        products.addItemListener(new ItemListener() {
            /**
             * daca se selecteaza un produs, atunci sub acesta va aparea un JLabel care va spune daca mai sunt
             * produse in stoc
             * @param e
             */
            @Override
            public void itemStateChanged(ItemEvent e) {
                productPan.remove(stock);
                stock=getProductLabel();
                productPan.add(stock);
                revalidate();
            }
        });
        productPan.add(products);
        productPan.add(stock);
        productPan.setLayout(new BoxLayout(productPan,BoxLayout.Y_AXIS));

        JTextField input=new JTextField();
        input.setColumns(3);

        JPanel buttonPane=new JPanel();
        JButton order=new JButton("Place order");
        JButton back=new JButton("Back");
        buttonPane.add(order);
        buttonPane.add(Box.createRigidArea(new Dimension(1,5)));
        buttonPane.add(back);
        order.addActionListener(new ActionListener() {
            /**
             * se verifica continutul din caseta text daca este corect scris cu InFoValidator, daca este mai mic
             * sau egal cu cantitatea din produsul selectat si daca este selectat un client si un produs;
             * daca da se va creea o noua tupla in Orders si se va decrementa stocul produsului selectat
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if((products.getSelectedItem()).equals("Select product") || (clients.getSelectedItem()).equals("Select client")
                        || input.getText().length()==0 || !InfoValidator.checkNumber(input.getText()) || Integer.parseInt(input.getText())<=0){
                    JOptionPane.showMessageDialog(new JFrame(), "Invalid inputs!");
                    return;
                }
                String idProduct=(String)products.getSelectedItem();
                int i=idProduct.indexOf('.');
                idProduct=idProduct.substring(0,i);
                int remainder=Integer.parseInt(new ProductDAO().idSearch("stock",Integer.parseInt(idProduct)))-Integer.parseInt(input.getText());
                if(remainder<0){
                    JOptionPane.showMessageDialog(new JFrame(), "Quantity too big!");
                }
                else {
                    String idClient=(String)clients.getSelectedItem();
                    i=idClient.indexOf('.');
                    idClient=idClient.substring(0,i);
                    int total=Integer.parseInt(input.getText())*Integer.parseInt(new ProductDAO().idSearch("price",Integer.parseInt(idProduct)));
                    String[] information={idClient,idProduct,input.getText(),total+""};
                    new OrderDAO().insertOrder(information);
                    new ProductDAO().edit(Integer.parseInt(idProduct),"stock",remainder+"");
                    products.setSelectedIndex(0);
                    clients.setSelectedIndex(0);
                    billing(Integer.parseInt(idClient),Integer.parseInt(idProduct));
                }
            }

            /**
             * se va creea un fisier text care va fi chitanta pentru comanda pusa
             * @param idClient
             * @param idProduct
             */
            private void billing(int idClient,int idProduct){
                try {
                    OrderDAO o=new OrderDAO();
                    int idOrder=o.number();
                    ProductDAO p=new ProductDAO();
                    ClientDAO c=new ClientDAO();

                    FileWriter w=new FileWriter("Order"+idOrder+".txt",true);
                    w.append("Order#"+(new OrderDAO().number())+"\n");
                    w.append("From:"+p.idSearch("producer",idProduct)+"\nTo:"+c.idSearch("firstname",idClient)+" "+c.idSearch("lastname",idClient)+"\n");
                    w.append("Address:"+c.idSearch("address",idClient)+"\nCustomer Bank:"+c.idSearch("bank",idClient)+"\nPhone number:"+c.idSearch("phone",idClient)+"\n");
                    w.append("Product:"+p.idSearch("product",idProduct)+" X "+o.idSearch("quantity",idOrder)+"\n");
                    w.append("Total:"+o.idSearch("total",idOrder));
                    w.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        back.addActionListener(new ActionListener() {
            /**
             * se va deschide o noua fereastra MainPage si se va inchide cea curenta
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                new MainPage();
                dispose();
            }
        });

        buttonPane.setLayout(new BoxLayout(buttonPane,BoxLayout.Y_AXIS));

        p.add(clientPan);
        p.add(productPan);
        p.add(input);
        p.add(buttonPane);
        p.setLayout(new FlowLayout());

        setVisible(true);
        this.setTitle("Order");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,110);
    }

    private JLabel getProductLabel() {
        /**
         * se va returna un JLabel ce va arata daca este sau nu in stoc produsul
         */
        if(!products.getSelectedItem().equals("Select product")){
            String temp="",text=(String)products.getSelectedItem();
            int i=0;
            while(true){
                if(text.charAt(i)=='.')
                    break;
                temp+=text.charAt(i);
                i++;
            }
            if(new ProductDAO().idSearch("stock",Integer.parseInt(temp)).equals("0"))
                return new JLabel("Out of stock");
            else{
                int number=Integer.parseInt(new ProductDAO().idSearch("stock",Integer.parseInt(temp)));
                stockValue=number;
                return new JLabel("In stock:"+number);
            }
        }
        return new JLabel("");
    }
}
