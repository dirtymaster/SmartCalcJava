package edu.school21.smartcalc;

import edu.school21.smartcalc.cpplib.NativeLibraryCalls;
import edu.school21.smartcalc.eventlisteners.TextFieldHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class View {
    private JTextField xValueTextField;
    private JButton ACButton;
    private JButton a7Button;
    private JButton a4Button;
    private JButton a0Button;
    private JButton a1Button;
    private JButton a8Button;
    private JButton a5Button;
    private JButton a2Button;
    private JButton powButton;
    private JButton a9Button;
    private JButton a6Button;
    private JButton a3Button;
    private JButton dotButton;
    private JButton divideButton;
    private JButton multiplyButton;
    private JButton minusButton;
    private JButton plusButton;
    private JButton equalsButton;
    private JTextField expressionTextField;
    private JButton xButton;
    private JButton closingParenthesisButton;
    private JButton openingParenthesisButton;
    private JButton tanButton;
    private JButton cosButton;
    private JButton sinButton;
    private JButton atanButton;
    private JButton acosButton;
    private JButton asinButton;
    private JButton modButton;
    private JButton sqrtButton;
    private JButton logButton;
    private JButton graphicButton;
    private JButton lnButton;
    private JTextField xMinTextField;
    private JTextField xMaxTextField;
    private JTextField yMinTextField;
    private JTextField yMaxTextField;
    private JTextField stepTextField;
    private JLabel xValueLable;
    private JLabel xMinLabel;
    private JLabel xMaxLabel;
    private JLabel yMinLabel;
    private JLabel yMaxLabel;
    private JLabel stepLabel;
    private JPanel mainPanel;

    JTextField currentTextField;
    TextFieldHandler handler;


    public View() {
        handler = new TextFieldHandler(expressionTextField);

        handler.addActionListener(a1Button, "1");
        handler.addActionListener(a2Button, "2");
        handler.addActionListener(a3Button, "3");
        handler.addActionListener(a4Button, "4");
        handler.addActionListener(a5Button, "5");
        handler.addActionListener(a6Button, "6");
        handler.addActionListener(a7Button, "7");
        handler.addActionListener(a8Button, "8");
        handler.addActionListener(a9Button, "9");
        handler.addActionListener(a0Button, "0");
        handler.addActionListener(ACButton, "");
        handler.addActionListener(powButton, "^");
        handler.addActionListener(divideButton, "/");
        handler.addActionListener(multiplyButton, "*");
        handler.addActionListener(minusButton, "-");
        handler.addActionListener(plusButton, "+");
        handler.addActionListener(dotButton, ".");
        handler.addActionListener(openingParenthesisButton, "(");
        handler.addActionListener(closingParenthesisButton, ")");
        handler.addActionListener(xButton, "x");
        handler.addActionListener(sinButton, "sin(");
        handler.addActionListener(cosButton, "cos(");
        handler.addActionListener(tanButton, "tan(");
        handler.addActionListener(asinButton, "asin(");
        handler.addActionListener(acosButton, "acos(");
        handler.addActionListener(atanButton, "atan(");
        handler.addActionListener(logButton, "log(");
        handler.addActionListener(lnButton, "ln(");
        handler.addActionListener(sqrtButton, "sqrt(");
        handler.addActionListener(modButton, "mod(");
        handler.addMouseListener(xValueTextField);
        handler.addMouseListener(xMinTextField);
        handler.addMouseListener(xMaxTextField);
        handler.addMouseListener(yMinTextField);
        handler.addMouseListener(yMaxTextField);
        handler.addMouseListener(stepTextField);
        equalsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NativeLibraryCalls.calculate(expressionTextField.getText(), 0);
                expressionTextField.setText(
                        String.valueOf(NativeLibraryCalls.getResult()));
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("SmartCalc");
        frame.setContentPane(new View().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
