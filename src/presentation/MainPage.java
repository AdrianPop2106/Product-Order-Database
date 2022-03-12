package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clasa MainPage este folosita pentru creearea unei ferestre grafice prin care se poate alege fereastra de operatii
 * pentru clientii, produse sau comenzi
 */
public class MainPage extends JFrame {
    public MainPage(){
        this.setLocationRelativeTo(null);

        Font f=new Font("Serif",Font.BOLD,20);

        JLabel title=new JLabel("Choose action");
        title.setFont(f);

        JButton client=new JButton("Client");
        client.addActionListener(new ActionListener() {
            /**
             * se va deschide o fereastra ClientPage si se inchide fereastra curenta
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                new ClientPage();
                dispose();
            }
        });
        JButton product=new JButton("Product");
        product.addActionListener(new ActionListener() {
            /**
             * se va deschide o fereastra ProductPage si se inchide fereastra curenta
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                new ProductPage();
                dispose();
            }
        });
        JButton order=new JButton("Order");
        order.addActionListener(new ActionListener() {
            /**
             * se va deschide o fereastra OrderPage si se inchide fereastra curenta
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                new OrderPage();
                dispose();
            }
        });

        JPanel buttons=new JPanel();
        buttons.add(client);
        buttons.add(Box.createRigidArea(new Dimension(10,1)));
        buttons.add(product);
        buttons.add(Box.createRigidArea(new Dimension(10,1)));
        buttons.add(order);

        JPanel p=(JPanel)this.getContentPane();

        p.add(Box.createRigidArea(new Dimension(1,5)));
        p.add(title);
        title.setAlignmentX(CENTER_ALIGNMENT);
        p.add(Box.createRigidArea(new Dimension(1,15)));
        p.add(buttons);
        p.setLayout(new BoxLayout(p,BoxLayout.Y_AXIS));
        setVisible(true);
        this.setTitle("Order Management");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(300,150);

    }
}
