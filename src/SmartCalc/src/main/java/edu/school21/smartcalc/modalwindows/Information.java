package edu.school21.smartcalc.modalwindows;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Information extends JDialog {
    public Information(JFrame jFrame) {
        super(jFrame, "Information", true);
        setLayout(new GridLayout());
        setLocationRelativeTo(null);
        setBounds(0, 0, 200, 200);
        setLocationRelativeTo(jFrame);
        JLabel text = new JLabel();
        text.setText("<html>This calculator allows " +
                "you to calculate arbitrary " +
                "mathematical expressions, " +
                "substitute x in them and build " +
                "graphs of functions. " +
                "The calculation history " +
                "is saved in the \"History\" tab. " +
                "x and y must be from -1_000_000 " +
                "to 1_000_000, the step must " +
                "be at least 1E-6.</html>");
        add(text);
    }
}
