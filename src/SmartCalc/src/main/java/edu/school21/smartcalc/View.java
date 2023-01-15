package edu.school21.smartcalc;

import edu.school21.smartcalc.chart.GraphicsPanel;
import edu.school21.smartcalc.cpplib.NativeLibraryCalls;
import edu.school21.smartcalc.handler.TextFieldHandler;
import edu.school21.smartcalc.modalwindows.History;
import edu.school21.smartcalc.modalwindows.Information;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class View {
    static JFrame frame = new JFrame("SmartCalc");
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
    private JPanel chartPanel;
    private JButton eButton;
    private JButton resetButton;
    private JButton historyButton;
    private JButton backspaceButton;
    private JButton button1;

    private JTextField currentTextField;
    private TextFieldHandler handler;
    private Scanner scanner;
    private GraphicsPanel graphicsPanel = null;
    private List<String> expressions = new ArrayList<>();


    public View() {
        initTextFields();
        handler = new TextFieldHandler(expressionTextField);

        handler.addUppendingActionListener(a1Button, "1");
        handler.addUppendingActionListener(a2Button, "2");
        handler.addUppendingActionListener(a3Button, "3");
        handler.addUppendingActionListener(a4Button, "4");
        handler.addUppendingActionListener(a5Button, "5");
        handler.addUppendingActionListener(a6Button, "6");
        handler.addUppendingActionListener(a7Button, "7");
        handler.addUppendingActionListener(a8Button, "8");
        handler.addUppendingActionListener(a9Button, "9");
        handler.addUppendingActionListener(a0Button, "0");
        handler.addUppendingActionListener(dotButton, ".");
        handler.addUppendingActionListener(minusButton, "-");
        handler.addUppendingActionListener(plusButton, "+");
        handler.addUppendingActionListener(eButton, "e");

        handler.addBackspaceActionListener(backspaceButton);

        addFunctionButtonsActionListener(powButton, "^");
        addFunctionButtonsActionListener(divideButton, "/");
        addFunctionButtonsActionListener(multiplyButton, "*");
        addFunctionButtonsActionListener(openingParenthesisButton, "(");
        addFunctionButtonsActionListener(closingParenthesisButton, ")");
        addFunctionButtonsActionListener(xButton, "x");
        addFunctionButtonsActionListener(sinButton, "sin(");
        addFunctionButtonsActionListener(cosButton, "cos(");
        addFunctionButtonsActionListener(tanButton, "tan(");
        addFunctionButtonsActionListener(asinButton, "asin(");
        addFunctionButtonsActionListener(acosButton, "acos(");
        addFunctionButtonsActionListener(atanButton, "atan(");
        addFunctionButtonsActionListener(logButton, "log(");
        addFunctionButtonsActionListener(lnButton, "ln(");
        addFunctionButtonsActionListener(sqrtButton, "sqrt(");
        addFunctionButtonsActionListener(modButton, "mod(");

        handler.addMouseListener(expressionTextField);
        handler.addMouseListener(xValueTextField);
        handler.addMouseListener(xMinTextField);
        handler.addMouseListener(xMaxTextField);
        handler.addMouseListener(yMinTextField);
        handler.addMouseListener(yMaxTextField);
        handler.addMouseListener(stepTextField);

        addEqualsButtonLogic();
        addClearButtonLogic();
        addResetButtonLogic();
        addGraphicLogic();
        chartPanel.setLayout(new BorderLayout());

        historyButton.addActionListener(e -> {
            History history = new History(frame, expressions);
            history.setVisible(true);
        });
        button1.addActionListener(e -> {
            Information information = new Information(frame);
            information.setVisible(true);
        });
    }

    private void addEqualsButtonLogic() {
        equalsButton.addActionListener(e -> {
            double x;

            if (expressionTextField.getText().contains("x")) {
                scanner = new Scanner(xValueTextField.getText());
                try {
                    x = scanner.nextDouble();
                } catch (Exception exception) {
                    xValueTextField.setText("Invalid");
                    return;
                }
            } else {
                x = 0.0;
            }
            if (NativeLibraryCalls.calculate(
                    expressionTextField.getText(), x)) {
                expressions.add(expressionTextField.getText());
                System.out.println(expressions);
                double result = NativeLibraryCalls.getResult();
                if ((int) result == result) {
                    expressionTextField.setText(String.valueOf((int) result));
                } else {
                    expressionTextField.setText(String.valueOf(result));
                }
            } else {
                expressionTextField.setText("Invalid expression");
            }
        });
    }

    private void addClearButtonLogic() {
        ACButton.addActionListener(e -> {
            expressionTextField.setText("");
            xValueTextField.setText("");
            handler.setCurrentTextField(expressionTextField);
        });
    }

    private void addResetButtonLogic() {
        resetButton.addActionListener(e -> {
            xMinTextField.setText("-10");
            xMaxTextField.setText("10");
            yMinTextField.setText("-10");
            yMaxTextField.setText("10");
            stepTextField.setText("0.1");
            if (graphicsPanel != null) {
                chartPanel.remove(graphicsPanel);
            }
        });
    }

    private void addFunctionButtonsActionListener(JButton button, String text) {
        button.addActionListener(e -> expressionTextField.setText(
                expressionTextField.getText() + text));
        handler.setCurrentTextField(expressionTextField);
    }

    private void addGraphicLogic() {
        graphicButton.addActionListener(e -> {
            Double xMin = null, xMax = null, yMin = null, yMax = null,
                    step = null;

            xMin = parseDouble(xMinTextField);
            if (xMin == null || xMin < -1_000_000) {
                xMinTextField.setText("Invalid");
                return;
            }

            xMax = parseDouble(xMaxTextField);
            if (xMax == null || xMax > 1_000_000 || xMax <= xMin) {
                xMaxTextField.setText("Invalid");
                return;
            }

            yMin = parseDouble(yMinTextField);
            if (yMin == null || yMin < -1_000_000) {
                yMinTextField.setText("Invalid");
                return;
            }

            yMax = parseDouble(yMaxTextField);
            if (yMax == null || yMax <= yMin) {
                yMaxTextField.setText("Invalid");
                return;
            }

            step = parseDouble(stepTextField);
            if (step == null || step < 0.000001 || xMax - xMin <= step) {
                stepTextField.setText("Invalid");
                return;
            }
            if (graphicsPanel != null) {
                chartPanel.remove(graphicsPanel);
            }
            graphicsPanel = new GraphicsPanel(chartPanel.getWidth(),
                    chartPanel.getHeight());
            graphicsPanel.setBackground(Color.WHITE);
            graphicsPanel.setParams(xMin, xMax, yMin, yMax, step,
                    expressionTextField.getText());
            chartPanel.add(graphicsPanel, BorderLayout.CENTER);
            chartPanel.revalidate();
        });
    }

    private Double parseDouble(JTextField source) {
        try {
            return Double.parseDouble(source.getText());
        } catch (Exception e) {
            return null;
        }
    }

    private void initTextFields() {
        expressionTextField.setHorizontalAlignment(SwingConstants.RIGHT);
        xValueTextField.setHorizontalAlignment(SwingConstants.CENTER);
        xMinTextField.setHorizontalAlignment(SwingConstants.CENTER);
        xMaxTextField.setHorizontalAlignment(SwingConstants.CENTER);
        yMinTextField.setHorizontalAlignment(SwingConstants.CENTER);
        yMaxTextField.setHorizontalAlignment(SwingConstants.CENTER);
        stepTextField.setHorizontalAlignment(SwingConstants.CENTER);
        LineBorder grayBorder = new LineBorder(new Color(120, 120, 120), 1);
        expressionTextField.setBorder(grayBorder);
        xValueTextField.setBorder(grayBorder);
        xMinTextField.setBorder(grayBorder);
        xMaxTextField.setBorder(grayBorder);
        yMinTextField.setBorder(grayBorder);
        yMaxTextField.setBorder(grayBorder);
        stepTextField.setBorder(grayBorder);
    }

    public static void main(String[] args) {
        frame.setContentPane(new View().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }
}
