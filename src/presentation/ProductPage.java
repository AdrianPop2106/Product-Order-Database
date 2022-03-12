package presentation;

import businessLayer.InfoValidator;
import dataAccesLayer.ProductDAO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Clasa ProductPage este folosita pentu creearea unei ferestre grafice din care se pot face operatii de adaugare
 * modificare, stergere si vizualizare pentru tabela Products
 */
public class ProductPage extends JFrame{
    JComboBox products;
    JLabel[] fields;
    public ProductPage(){
        JTextField[] info=new JTextField[4];
        info[0]=new JTextField();
        info[0].setBounds(20,80,80,25);

        info[1]=new JTextField();
        info[1].setBounds(110,80,80,25);

        info[2]=new JTextField();
        info[2].setBounds(200,80,80,25);

        info[3]=new JTextField();
        info[3].setBounds(290,80,80,25);

        this.setLocationRelativeTo(null);
        JPanel p=(JPanel)this.getContentPane();
        JButton add=new JButton("Add product");
        JButton edit=new JButton("Edit product");
        JButton delete=new JButton("Delete product");
        JButton view=new JButton("View products");
        add.addActionListener(new ActionListener() {
            /**
             * se verifica cu metodele din InfoValidator continutul fiecarei casete de text si, daca sunt toate corecte,
             * se va introduce in tabela Products o noua tupla cu informatia preluata din casete;
             * se deschide o fereastra PopUp daca sunt greseli in casete
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] information=new String[5];
                if(InfoValidator.checkText(info[0].getText()) && InfoValidator.checkText(info[1].getText())
                        && InfoValidator.checkNumber(info[2].getText()) && InfoValidator.checkNumber(info[3].getText())){
                    for(int i=0;i<4;i++){
                        information[i]=info[i].getText();
                        info[i].setText("");
                    }
                    new ProductDAO().insertProduct(information);
                    products.removeAllItems();
                    String[] c=new ProductDAO().getProducts();
                    for(int i=0;i<c.length;i++)
                        products.addItem(c[i]);
                    revalidate();
                    repaint();
                }
                else
                    JOptionPane.showMessageDialog(new JFrame(), "Information written is not valid");
            }
        });
        edit.addActionListener(new ActionListener() {
            /**
             * se verifica cu metodele din InfoValidator continutul fiecarei casete de text si se vor inlocuii in tabela
             * Products doar informatiile corecte pentru produsul selectat in JComboBox;
             * informatiile gresite vor ramane in casete
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!(products.getSelectedItem()).equals("Select product")){
                    String id=(String)products.getSelectedItem();
                    int i=id.indexOf('.');
                    id=id.substring(0,i);
                    for(i=0;i<4;i++){
                        String column;
                        column=fields[i].getText();
                        if(info[i].getText().length()==0 || ((i==2 || i==3) && !InfoValidator.checkNumber(info[i].getText())) || ((i==1 || i==0) && !InfoValidator.checkText(info[i].getText())))
                            continue;
                        new ProductDAO().edit(Integer.parseInt(id),column,info[i].getText());
                        info[i].setText("");
                    }
                    products.removeAllItems();
                    String[] c=new ProductDAO().getProducts();
                    for(i=0;i<c.length;i++)
                        products.addItem(c[i]);
                    revalidate();
                    repaint();
                }
            }
        });
        delete.addActionListener(new ActionListener() {
            /**
             * daca este un produs selectat in JComboBox , atunci acesta va fi sters din tabela Products
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!(products.getSelectedItem()).equals("Select product")) {
                    String id=(String)products.getSelectedItem();
                    int i=id.indexOf('.');
                    id=id.substring(0,i);
                    new ProductDAO().delete(Integer.parseInt(id));
                    products.removeAllItems();
                    String[] c=new ProductDAO().getProducts();
                    for(i=0;i<c.length;i++)
                        products.addItem(c[i]);
                    revalidate();
                    repaint();
                }
                else
                    JOptionPane.showMessageDialog(new JFrame(), "Invalid delete!");
            }
        });
        view.addActionListener(new ActionListener(){
            /**
             * se vor prelua toata informatia stocata in tabela Products si se va prezenta printr-un JTable
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String[][] data=new ProductDAO().getEverything();
                String[] info={"ID","Name","Producer","Price/Piece","Stock"};
                JFrame f=new JFrame();
                JTable j=new JTable(data,info);
                f.setLocationRelativeTo(null);
                j.setBounds(30, 40, 200, 300);
                f.add(j);
                f.add(new JScrollPane(j));
                f.setSize(500,200);
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setVisible(true);
            }
        });
        JButton back=new JButton("Back");
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

        fields=new JLabel[4];
        fields[0]=new JLabel("Product");
        fields[0].setBounds(20,60,80,25);

        fields[1]=new JLabel("Producer");
        fields[1].setBounds(110,60,80,25);

        fields[2]=new JLabel("Price");
        fields[2].setBounds(200,60,80,25);

        fields[3]=new JLabel("Stock");
        fields[3].setBounds(290,60,80,25);

        add.setBounds(20, 20, 150, 30);
        edit.setBounds(20,115,150,30);
        delete.setBounds(20,155,150,30);
        back.setBounds(265,220,110,30);
        view.setBounds(20,195,150,30);

        p.setLayout(null);

        products=new JComboBox(new ProductDAO().getProducts());
        products.setMaximumRowCount(4);
        products.setBounds(180,130,180,30);

        p.add(add);
        p.add(edit);
        p.add(delete);
        p.add(products);
        p.add(view);
        p.add(back);
        p.add(fields[0]);
        p.add(fields[1]);
        p.add(fields[2]);
        p.add(fields[3]);
        p.add(info[0]);
        p.add(info[1]);
        p.add(info[2]);
        p.add(info[3]);
        this.setTitle("Products");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(410,300);
        this.setVisible(true);
    }
}
