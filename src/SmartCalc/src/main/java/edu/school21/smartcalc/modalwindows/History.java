package edu.school21.smartcalc.modalwindows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Set;

public class History extends JDialog {
    public History(JFrame jFrame, Set<String> expressions,
                   JTextField expressionTextField) {
        super(jFrame, "History", true);

        setLayout(new GridLayout());
        setLocationRelativeTo(null);
        setBounds(0, 0, 200, 200);
        setLocationRelativeTo(jFrame);
        JList<String> jList = new JList(expressions.toArray());
        jList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    expressionTextField.setText(jList.getSelectedValue());
                }
            }
        });
        jList.setLayoutOrientation(JList.VERTICAL);
        JScrollPane jScrollPane = new JScrollPane(jList);
        add(jScrollPane);
    }
}
