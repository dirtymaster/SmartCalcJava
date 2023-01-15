package edu.school21.smartcalc.modalwindows;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class History extends JDialog {
    public History(JFrame jFrame, List<String> expressions) {
        super(jFrame, "History", true);
        setLayout(new GridLayout());
        setLocationRelativeTo(null);
        setBounds(0, 0, 200, 200);
        setLocationRelativeTo(jFrame);
        JList<String> jList = new JList(expressions.toArray());
        jList.setLayoutOrientation(JList.VERTICAL);
        JScrollPane jScrollPane = new JScrollPane(jList);
        add(jScrollPane);
    }
}
