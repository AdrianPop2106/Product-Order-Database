package presentation;

import dataAccesLayer.ClientDAO;
import businessLayer.InfoValidator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clasa ClientPage este folosita pentu creearea unei ferestre grafice din care se pot face operatii de adaugare
 * modificare, stergere si vizualizare pentru tabela Clients
 */
public class ClientPage extends JFrame {
    JComboBox clients;
    JLabel[] fields;
    public ClientPage(){
        JTextField[] info=new JTextField[5];
        info[0]=new JTextField();
        info[0].setBounds(20,80,80,25);

        info[1]=new JTextField();
        info[1].setBounds(110,80,80,25);

        info[2]=new JTextField();
        info[2].setBounds(200,80,80,25);

        info[3]=new JTextField();
        info[3].setBounds(290,80,80,25);

        info[4]=new JTextField();
        info[4].setBounds(380,80,80,25);
        this.setLocationRelativeTo(null);
        JPanel p=(JPanel)this.getContentPane();
        JButton add=new JButton("Add client");
        JButton edit=new JButton("Edit client");
        JButton delete=new JButton("Delete client");
        JButton back=new JButton("Back");
        JButton view=new JButton("View clients");
        add.addActionListener(new ActionListener() {
            /**
             * se verifica cu metodele din InfoValidator continutul fiecarei casete de text si, daca sunt toate corecte,
             * se va introduce in tabela Clients o noua tupla cu informatia preluata din casete;
             * se deschide o fereastra PopUp daca sunt greseli in casete
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] information=new String[5];
                if(InfoValidator.checkText(info[0].getText()) && InfoValidator.checkText(info[1].getText())
                        && InfoValidator.checkPhone(info[2].getText()) && InfoValidator.checkText(info[3].getText())
                            && InfoValidator.checkText(info[4].getText())){
                    for(int i=0;i<5;i++){
                        information[i]=info[i].getText();
                        info[i].setText("");
                    }
                    new ClientDAO().insertClient(information);
                    clients.removeAllItems();
                    String[] c=new ClientDAO().getClients();
                    for(int i=0;i<c.length;i++)
                        clients.addItem(c[i]);
                    revalidate();
                    repaint();
                    }
                else JOptionPane.showMessageDialog(new JFrame(), "Information written is not valid");
                }
        });
        edit.addActionListener(new ActionListener() {
            /**
             * se verifica cu metodele din InfoValidator continutul fiecarei casete de text si se vor inlocuii in tabela
             * Clients doar informatiile corecte pentru clientul selectat in JComboBox;
             * informatiile gresite vor ramane in casete
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!(clients.getSelectedItem()).equals("Select client")){
                    String id=(String)clients.getSelectedItem();
                    int i=id.indexOf('.');
                    id=id.substring(0,i);
                    for(i=0;i<5;i++){
                        String column;
                        if(i==0 || i==1){
                            int j=fields[i].getText().indexOf(' ');
                            column=fields[i].getText().substring(0,j)+fields[i].getText().substring(j+1);
                        }
                         else
                             column=fields[i].getText();
                        if(info[i].getText().length()==0 || (i==2 && !InfoValidator.checkPhone(info[i].getText())) || (i!=2 && !InfoValidator.checkText(info[i].getText())))
                            continue;
                        new ClientDAO().edit(Integer.parseInt(id),column,info[i].getText());
                        info[i].setText("");
                    }
                    clients.removeAllItems();
                    String[] c=new ClientDAO().getClients();
                    for(i=0;i<c.length;i++)
                        clients.addItem(c[i]);
                    revalidate();
                    repaint();
                }
            }
        });
        delete.addActionListener(new ActionListener() {
            /**
             * daca este un client selectat in JComboBox , atunci acesta va fi sters din tabela Clients
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!(clients.getSelectedItem()).equals("Select client")) {
                    String id=(String)clients.getSelectedItem();
                    int i=id.indexOf('.');
                    id=id.substring(0,i);
                    new ClientDAO().delete(Integer.parseInt(id));
                    clients.removeAllItems();
                    String[] c=new ClientDAO().getClients();
                    for(i=0;i<c.length;i++)
                        clients.addItem(c[i]);
                    revalidate();
                    repaint();
                }
                else
                    JOptionPane.showMessageDialog(new JFrame(), "Invalid delete!");
            }
        });
        view.addActionListener(new ActionListener(){
            /**
             * se vor prelua toata informatia stocata in tabela Clients si se va prezenta printr-un JTable
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String[][] data=new ClientDAO().getEverything();
                String[] info={"ID","First Name","Last Name","Phone number","Address","Bank"};
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

        fields=new JLabel[5];
        fields[0]=new JLabel("First name");
        fields[0].setBounds(20,60,80,25);

        fields[1]=new JLabel("Last name");
        fields[1].setBounds(110,60,80,25);

        fields[2]=new JLabel("Phone");
        fields[2].setBounds(200,60,80,25);

        fields[3]=new JLabel("Address");
        fields[3].setBounds(290,60,80,25);

        fields[4]=new JLabel("Bank");
        fields[4].setBounds(380,60,80,25);

        add.setBounds(20, 20, 110, 30);
        edit.setBounds(20,115,110,30);
        delete.setBounds(20,155,110,30);
        view.setBounds(20,195,110,30);
        back.setBounds(360,220,110,30);

        p.setLayout(null);

        clients=new JComboBox(new ClientDAO().getClients());
        clients.setMaximumRowCount(4);
        clients.setBounds(140,130,180,30);

        info[0]=new JTextField();
        info[0].setBounds(20,80,80,25);

        info[1]=new JTextField();
        info[1].setBounds(110,80,80,25);

        info[2]=new JTextField();
        info[2].setBounds(200,80,80,25);

        info[3]=new JTextField();
        info[3].setBounds(290,80,80,25);

        info[4]=new JTextField();
        info[4].setBounds(380,80,80,25);

        p.add(add);
        p.add(edit);
        p.add(delete);
        p.add(view);
        p.add(clients);
        p.add(back);
        p.add(fields[0]);
        p.add(fields[1]);
        p.add(fields[2]);
        p.add(fields[3]);
        p.add(fields[4]);
        p.add(info[0]);
        p.add(info[1]);
        p.add(info[2]);
        p.add(info[3]);
        p.add(info[4]);
        this.setTitle("Clients");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,300);
        this.setVisible(true);
    }
}
