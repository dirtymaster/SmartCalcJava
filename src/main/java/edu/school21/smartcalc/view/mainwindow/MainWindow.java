package edu.school21.smartcalc.view.mainwindow;

import edu.school21.smartcalc.presenter.history.HistoryFile;
import edu.school21.smartcalc.view.chart.GraphicsPanel;
import edu.school21.smartcalc.presenter.NativeLibraryCalls;
import edu.school21.smartcalc.view.handler.TextFieldHandler;
import edu.school21.smartcalc.view.modalwindows.History;
import edu.school21.smartcalc.view.modalwindows.Information;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.text.StyleContext;
import java.awt.*;
import java.io.*;
import java.util.*;

public class MainWindow {
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
    private JLabel xValueLabel;
    private JLabel xMinLabel;
    private JLabel xMaxLabel;
    private JLabel yMinLabel;
    private JLabel yMaxLabel;
    private JLabel stepLabel;
    private JPanel mainPanel;
    private JPanel graphicPanelWrapper;
    private JButton eButton;
    private JButton resetButton;
    private JButton historyButton;
    private JButton backspaceButton;
    private JButton button1;
    private final TextFieldHandler handler;
    private Scanner scanner;
    private GraphicsPanel graphicPanel = null;
    private Set<String> expressions = new HashSet<>();
    private final String historyFileName = "history.txt";
    private HistoryFile historyFile;


    public MainWindow() {
        loadHistoryFromFile();
        initTextFields();

        handler = new TextFieldHandler(expressionTextField);
        addMainButtonsLogic();
        addEqualsButtonLogic();
        addClearButtonLogic();
        addResetButtonLogic();
        addGraphicLogic();
        addHistoryLogic();
        graphicPanelWrapper.setLayout(new BorderLayout());
    }

    private void loadHistoryFromFile() {
        historyFile = new HistoryFile(historyFileName);
        expressions = historyFile.loadExpressionsFromHistoryFile();
    }

    private void addMainButtonsLogic() {
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
        addFunctionButtonsActionListener(modButton, "mod");

        handler.addMouseListener(expressionTextField);
        handler.addMouseListener(xValueTextField);
        handler.addMouseListener(xMinTextField);
        handler.addMouseListener(xMaxTextField);
        handler.addMouseListener(yMinTextField);
        handler.addMouseListener(yMaxTextField);
        handler.addMouseListener(stepTextField);
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
            String expression = expressionTextField.getText();
            if (NativeLibraryCalls.calculate(
                    expression, x)) {
                expressions.add(expression);
                historyFile.addExpressionToHistoryFile(expression);

                double result = NativeLibraryCalls.getResult();
                if ((int) result == result) {
                    expressionTextField.setText(String.valueOf((int) result));
                } else {
                    expressionTextField.setText(String.valueOf(
                            (double) Math.round(result * 1_000_000)
                                    / 1_000_000));
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
            try (FileWriter fw = new FileWriter(historyFileName)) {
                fw.write("");
            } catch (IOException exception) {
                System.err.println(
                        "File \"" + historyFileName + "\" not found");
            }
        });
    }

    private void addResetButtonLogic() {
        resetButton.addActionListener(e -> {
            xMinTextField.setText("-10");
            xMaxTextField.setText("10");
            yMinTextField.setText("-10");
            yMaxTextField.setText("10");
            stepTextField.setText("0.1");
            if (graphicPanel != null) {
                graphicPanelWrapper.remove(graphicPanel);
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
            Double xMin, xMax, yMin, yMax,
                    step;

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
            if (graphicPanel != null) {
                graphicPanelWrapper.remove(graphicPanel);
            }
            if (NativeLibraryCalls.checkExpressionCorrectness(
                    expressionTextField.getText())) {
                String expression = expressionTextField.getText();
                expressions.add(expression);
                historyFile.addExpressionToHistoryFile(expression);

                graphicPanel = new GraphicsPanel(graphicPanelWrapper.getWidth(),
                        graphicPanelWrapper.getHeight());
                graphicPanel.setBackground(Color.WHITE);
                graphicPanel.setParams(xMin, xMax, yMin, yMax, step,
                        expressionTextField.getText());
                graphicPanelWrapper.add(graphicPanel, BorderLayout.CENTER);
                graphicPanelWrapper.revalidate();
            } else {
                expressionTextField.setText("Invalid expression");
            }
        });
    }

    private Double parseDouble(JTextField source) {
        try {
            return Double.parseDouble(source.getText());
        } catch (Exception e) {
            return null;
        }
    }

    private void addHistoryLogic() {
        historyButton.addActionListener(e -> {
            History history
                    = new History(frame, expressions, expressionTextField);
            history.setVisible(true);
        });
        button1.addActionListener(e -> {
            Information information = new Information(frame);
            information.setVisible(true);
        });
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
        frame.setContentPane(new MainWindow().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(new Color(-10855846));
        mainPanel.setMaximumSize(new Dimension(750, 320));
        mainPanel.setMinimumSize(new Dimension(750, 320));
        mainPanel.setPreferredSize(new Dimension(750, 320));
        xValueTextField = new JTextField();
        xValueTextField.setBackground(new Color(-10855846));
        Font xValueTextFieldFont = this.$$$getFont$$$("Helvetica", -1, 14, xValueTextField.getFont());
        if (xValueTextFieldFont != null)
            xValueTextField.setFont(xValueTextFieldFont);
        xValueTextField.setForeground(new Color(-1));
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(xValueTextField, gbc);
        xValueLabel = new JLabel();
        Font xValueLabelFont = this.$$$getFont$$$("Helvetica", -1, 16, xValueLabel.getFont());
        if (xValueLabelFont != null) xValueLabel.setFont(xValueLabelFont);
        xValueLabel.setForeground(new Color(-1));
        xValueLabel.setText("x value");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        mainPanel.add(xValueLabel, gbc);
        ACButton = new JButton();
        ACButton.setBackground(new Color(-10855846));
        Font ACButtonFont = this.$$$getFont$$$("Helvetica", -1, 20, ACButton.getFont());
        if (ACButtonFont != null) ACButton.setFont(ACButtonFont);
        ACButton.setText("AC");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(ACButton, gbc);
        a7Button = new JButton();
        a7Button.setBackground(new Color(-10855846));
        Font a7ButtonFont = this.$$$getFont$$$("Helvetica", -1, 20, a7Button.getFont());
        if (a7ButtonFont != null) a7Button.setFont(a7ButtonFont);
        a7Button.setText("7");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(a7Button, gbc);
        a4Button = new JButton();
        a4Button.setBackground(new Color(-10855846));
        Font a4ButtonFont = this.$$$getFont$$$("Helvetica", -1, 20, a4Button.getFont());
        if (a4ButtonFont != null) a4Button.setFont(a4ButtonFont);
        a4Button.setText("4");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(a4Button, gbc);
        a0Button = new JButton();
        a0Button.setBackground(new Color(-10855846));
        Font a0ButtonFont = this.$$$getFont$$$("Helvetica", -1, 20, a0Button.getFont());
        if (a0ButtonFont != null) a0Button.setFont(a0ButtonFont);
        a0Button.setText("0");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(a0Button, gbc);
        a1Button = new JButton();
        a1Button.setBackground(new Color(-10855846));
        Font a1ButtonFont = this.$$$getFont$$$("Helvetica", -1, 20, a1Button.getFont());
        if (a1ButtonFont != null) a1Button.setFont(a1ButtonFont);
        a1Button.setText("1");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 5;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(a1Button, gbc);
        a8Button = new JButton();
        a8Button.setBackground(new Color(-10855846));
        Font a8ButtonFont = this.$$$getFont$$$("Helvetica", -1, 20, a8Button.getFont());
        if (a8ButtonFont != null) a8Button.setFont(a8ButtonFont);
        a8Button.setText("8");
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(a8Button, gbc);
        a5Button = new JButton();
        a5Button.setBackground(new Color(-10855846));
        Font a5ButtonFont = this.$$$getFont$$$("Helvetica", -1, 20, a5Button.getFont());
        if (a5ButtonFont != null) a5Button.setFont(a5ButtonFont);
        a5Button.setText("5");
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(a5Button, gbc);
        a2Button = new JButton();
        a2Button.setBackground(new Color(-10855846));
        Font a2ButtonFont = this.$$$getFont$$$("Helvetica", -1, 20, a2Button.getFont());
        if (a2ButtonFont != null) a2Button.setFont(a2ButtonFont);
        a2Button.setText("2");
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 5;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(a2Button, gbc);
        a9Button = new JButton();
        a9Button.setBackground(new Color(-10855846));
        Font a9ButtonFont = this.$$$getFont$$$("Helvetica", -1, 20, a9Button.getFont());
        if (a9ButtonFont != null) a9Button.setFont(a9ButtonFont);
        a9Button.setText("9");
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(a9Button, gbc);
        a6Button = new JButton();
        a6Button.setBackground(new Color(-10855846));
        Font a6ButtonFont = this.$$$getFont$$$("Helvetica", -1, 20, a6Button.getFont());
        if (a6ButtonFont != null) a6Button.setFont(a6ButtonFont);
        a6Button.setText("6");
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(a6Button, gbc);
        a3Button = new JButton();
        a3Button.setBackground(new Color(-10855846));
        Font a3ButtonFont = this.$$$getFont$$$("Helvetica", -1, 20, a3Button.getFont());
        if (a3ButtonFont != null) a3Button.setFont(a3ButtonFont);
        a3Button.setText("3");
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 5;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(a3Button, gbc);
        dotButton = new JButton();
        dotButton.setBackground(new Color(-10855846));
        Font dotButtonFont = this.$$$getFont$$$("Helvetica", -1, 20, dotButton.getFont());
        if (dotButtonFont != null) dotButton.setFont(dotButtonFont);
        dotButton.setText(".");
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 6;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(dotButton, gbc);
        equalsButton = new JButton();
        equalsButton.setBackground(new Color(-10855846));
        Font equalsButtonFont = this.$$$getFont$$$("Helvetica", -1, 20, equalsButton.getFont());
        if (equalsButtonFont != null) equalsButton.setFont(equalsButtonFont);
        equalsButton.setText("=");
        gbc = new GridBagConstraints();
        gbc.gridx = 7;
        gbc.gridy = 5;
        gbc.gridheight = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(equalsButton, gbc);
        expressionTextField = new JTextField();
        expressionTextField.setBackground(new Color(-10855846));
        Font expressionTextFieldFont = this.$$$getFont$$$("Helvetica", -1, 24, expressionTextField.getFont());
        if (expressionTextFieldFont != null)
            expressionTextField.setFont(expressionTextFieldFont);
        expressionTextField.setForeground(new Color(-1));
        expressionTextField.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.gridwidth = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(expressionTextField, gbc);
        closingParenthesisButton = new JButton();
        closingParenthesisButton.setBackground(new Color(-10855846));
        Font closingParenthesisButtonFont = this.$$$getFont$$$("Helvetica", -1, 20, closingParenthesisButton.getFont());
        if (closingParenthesisButtonFont != null)
            closingParenthesisButton.setFont(closingParenthesisButtonFont);
        closingParenthesisButton.setText(")");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(closingParenthesisButton, gbc);
        openingParenthesisButton = new JButton();
        openingParenthesisButton.setBackground(new Color(-10855846));
        Font openingParenthesisButtonFont = this.$$$getFont$$$("Helvetica", -1, 20, openingParenthesisButton.getFont());
        if (openingParenthesisButtonFont != null)
            openingParenthesisButton.setFont(openingParenthesisButtonFont);
        openingParenthesisButton.setText("(");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(openingParenthesisButton, gbc);
        xMinLabel = new JLabel();
        Font xMinLabelFont = this.$$$getFont$$$("Helvetica", -1, 16, xMinLabel.getFont());
        if (xMinLabelFont != null) xMinLabel.setFont(xMinLabelFont);
        xMinLabel.setForeground(new Color(-1));
        xMinLabel.setText("  x min  ");
        gbc = new GridBagConstraints();
        gbc.gridx = 8;
        gbc.gridy = 0;
        mainPanel.add(xMinLabel, gbc);
        xMinTextField = new JTextField();
        xMinTextField.setBackground(new Color(-10855846));
        Font xMinTextFieldFont = this.$$$getFont$$$("Helvetica", -1, 14, xMinTextField.getFont());
        if (xMinTextFieldFont != null) xMinTextField.setFont(xMinTextFieldFont);
        xMinTextField.setForeground(new Color(-1));
        xMinTextField.setHorizontalAlignment(0);
        xMinTextField.setText("-10");
        gbc = new GridBagConstraints();
        gbc.gridx = 8;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(xMinTextField, gbc);
        xMaxLabel = new JLabel();
        Font xMaxLabelFont = this.$$$getFont$$$("Helvetica", -1, 16, xMaxLabel.getFont());
        if (xMaxLabelFont != null) xMaxLabel.setFont(xMaxLabelFont);
        xMaxLabel.setForeground(new Color(-1));
        xMaxLabel.setText("  x max  ");
        gbc = new GridBagConstraints();
        gbc.gridx = 9;
        gbc.gridy = 0;
        mainPanel.add(xMaxLabel, gbc);
        xMaxTextField = new JTextField();
        xMaxTextField.setBackground(new Color(-10855846));
        Font xMaxTextFieldFont = this.$$$getFont$$$("Helvetica", -1, 14, xMaxTextField.getFont());
        if (xMaxTextFieldFont != null) xMaxTextField.setFont(xMaxTextFieldFont);
        xMaxTextField.setForeground(new Color(-1));
        xMaxTextField.setHorizontalAlignment(0);
        xMaxTextField.setText("10");
        gbc = new GridBagConstraints();
        gbc.gridx = 9;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(xMaxTextField, gbc);
        yMinLabel = new JLabel();
        Font yMinLabelFont = this.$$$getFont$$$("Helvetica", -1, 16, yMinLabel.getFont());
        if (yMinLabelFont != null) yMinLabel.setFont(yMinLabelFont);
        yMinLabel.setForeground(new Color(-1));
        yMinLabel.setText("  y min  ");
        gbc = new GridBagConstraints();
        gbc.gridx = 10;
        gbc.gridy = 0;
        mainPanel.add(yMinLabel, gbc);
        yMinTextField = new JTextField();
        yMinTextField.setBackground(new Color(-10855846));
        Font yMinTextFieldFont = this.$$$getFont$$$("Helvetica", -1, 14, yMinTextField.getFont());
        if (yMinTextFieldFont != null) yMinTextField.setFont(yMinTextFieldFont);
        yMinTextField.setForeground(new Color(-1));
        yMinTextField.setHorizontalAlignment(0);
        yMinTextField.setText("-10");
        gbc = new GridBagConstraints();
        gbc.gridx = 10;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(yMinTextField, gbc);
        yMaxLabel = new JLabel();
        Font yMaxLabelFont = this.$$$getFont$$$("Helvetica", -1, 16, yMaxLabel.getFont());
        if (yMaxLabelFont != null) yMaxLabel.setFont(yMaxLabelFont);
        yMaxLabel.setForeground(new Color(-1));
        yMaxLabel.setText("  y max  ");
        gbc = new GridBagConstraints();
        gbc.gridx = 11;
        gbc.gridy = 0;
        mainPanel.add(yMaxLabel, gbc);
        yMaxTextField = new JTextField();
        yMaxTextField.setBackground(new Color(-10855846));
        Font yMaxTextFieldFont = this.$$$getFont$$$("Helvetica", -1, 14, yMaxTextField.getFont());
        if (yMaxTextFieldFont != null) yMaxTextField.setFont(yMaxTextFieldFont);
        yMaxTextField.setForeground(new Color(-1));
        yMaxTextField.setHorizontalAlignment(0);
        yMaxTextField.setText("10");
        gbc = new GridBagConstraints();
        gbc.gridx = 11;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(yMaxTextField, gbc);
        stepTextField = new JTextField();
        stepTextField.setBackground(new Color(-10855846));
        Font stepTextFieldFont = this.$$$getFont$$$("Helvetica", -1, 14, stepTextField.getFont());
        if (stepTextFieldFont != null) stepTextField.setFont(stepTextFieldFont);
        stepTextField.setForeground(new Color(-1));
        stepTextField.setHorizontalAlignment(0);
        stepTextField.setText("0.1");
        gbc = new GridBagConstraints();
        gbc.gridx = 12;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(stepTextField, gbc);
        stepLabel = new JLabel();
        Font stepLabelFont = this.$$$getFont$$$("Helvetica", -1, 16, stepLabel.getFont());
        if (stepLabelFont != null) stepLabel.setFont(stepLabelFont);
        stepLabel.setForeground(new Color(-1));
        stepLabel.setText("   step   ");
        gbc = new GridBagConstraints();
        gbc.gridx = 12;
        gbc.gridy = 0;
        mainPanel.add(stepLabel, gbc);
        graphicPanelWrapper = new JPanel();
        graphicPanelWrapper.setLayout(new GridBagLayout());
        graphicPanelWrapper.setBackground(new Color(-1));
        gbc = new GridBagConstraints();
        gbc.gridx = 8;
        gbc.gridy = 2;
        gbc.gridwidth = 6;
        gbc.gridheight = 5;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(graphicPanelWrapper, gbc);
        final JSeparator separator1 = new JSeparator();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(separator1, gbc);
        atanButton = new JButton();
        atanButton.setBackground(new Color(-10855846));
        Font atanButtonFont = this.$$$getFont$$$("Helvetica", -1, 20, atanButton.getFont());
        if (atanButtonFont != null) atanButton.setFont(atanButtonFont);
        atanButton.setText("atan");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(atanButton, gbc);
        acosButton = new JButton();
        acosButton.setBackground(new Color(-10855846));
        Font acosButtonFont = this.$$$getFont$$$("Helvetica", -1, 20, acosButton.getFont());
        if (acosButtonFont != null) acosButton.setFont(acosButtonFont);
        acosButton.setText("acos");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(acosButton, gbc);
        asinButton = new JButton();
        asinButton.setBackground(new Color(-10855846));
        Font asinButtonFont = this.$$$getFont$$$("Helvetica", -1, 20, asinButton.getFont());
        if (asinButtonFont != null) asinButton.setFont(asinButtonFont);
        asinButton.setText("asin");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(asinButton, gbc);
        sinButton = new JButton();
        sinButton.setBackground(new Color(-10855846));
        Font sinButtonFont = this.$$$getFont$$$("Helvetica", -1, 20, sinButton.getFont());
        if (sinButtonFont != null) sinButton.setFont(sinButtonFont);
        sinButton.setText("sin");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(sinButton, gbc);
        cosButton = new JButton();
        cosButton.setBackground(new Color(-10855846));
        Font cosButtonFont = this.$$$getFont$$$("Helvetica", -1, 20, cosButton.getFont());
        if (cosButtonFont != null) cosButton.setFont(cosButtonFont);
        cosButton.setText("cos");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(cosButton, gbc);
        sqrtButton = new JButton();
        sqrtButton.setBackground(new Color(-10855846));
        Font sqrtButtonFont = this.$$$getFont$$$("Helvetica", -1, 20, sqrtButton.getFont());
        if (sqrtButtonFont != null) sqrtButton.setFont(sqrtButtonFont);
        sqrtButton.setText("sqrt");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(sqrtButton, gbc);
        tanButton = new JButton();
        tanButton.setBackground(new Color(-10855846));
        Font tanButtonFont = this.$$$getFont$$$("Helvetica", -1, 20, tanButton.getFont());
        if (tanButtonFont != null) tanButton.setFont(tanButtonFont);
        tanButton.setText("tan");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(tanButton, gbc);
        xButton = new JButton();
        xButton.setBackground(new Color(-10855846));
        Font xButtonFont = this.$$$getFont$$$("Helvetica", -1, 20, xButton.getFont());
        if (xButtonFont != null) xButton.setFont(xButtonFont);
        xButton.setText("x");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(xButton, gbc);
        eButton = new JButton();
        eButton.setBackground(new Color(-10855846));
        Font eButtonFont = this.$$$getFont$$$("Helvetica", -1, 20, eButton.getFont());
        if (eButtonFont != null) eButton.setFont(eButtonFont);
        eButton.setText("e");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(eButton, gbc);
        modButton = new JButton();
        modButton.setBackground(new Color(-10855846));
        Font modButtonFont = this.$$$getFont$$$("Helvetica", -1, 20, modButton.getFont());
        if (modButtonFont != null) modButton.setFont(modButtonFont);
        modButton.setText("mod");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(modButton, gbc);
        lnButton = new JButton();
        lnButton.setBackground(new Color(-10855846));
        Font lnButtonFont = this.$$$getFont$$$("Helvetica", -1, 20, lnButton.getFont());
        if (lnButtonFont != null) lnButton.setFont(lnButtonFont);
        lnButton.setText("ln");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(lnButton, gbc);
        logButton = new JButton();
        logButton.setBackground(new Color(-10855846));
        Font logButtonFont = this.$$$getFont$$$("Helvetica", -1, 20, logButton.getFont());
        if (logButtonFont != null) logButton.setFont(logButtonFont);
        logButton.setText("log");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(logButton, gbc);
        powButton = new JButton();
        powButton.setBackground(new Color(-10855846));
        Font powButtonFont = this.$$$getFont$$$("Helvetica", -1, 20, powButton.getFont());
        if (powButtonFont != null) powButton.setFont(powButtonFont);
        powButton.setText("^");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(powButton, gbc);
        divideButton = new JButton();
        divideButton.setBackground(new Color(-10855846));
        Font divideButtonFont = this.$$$getFont$$$("Helvetica", -1, 20, divideButton.getFont());
        if (divideButtonFont != null) divideButton.setFont(divideButtonFont);
        divideButton.setText("/");
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(divideButton, gbc);
        multiplyButton = new JButton();
        multiplyButton.setBackground(new Color(-10855846));
        Font multiplyButtonFont = this.$$$getFont$$$("Helvetica", -1, 20, multiplyButton.getFont());
        if (multiplyButtonFont != null)
            multiplyButton.setFont(multiplyButtonFont);
        multiplyButton.setText("*");
        gbc = new GridBagConstraints();
        gbc.gridx = 7;
        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(multiplyButton, gbc);
        minusButton = new JButton();
        minusButton.setBackground(new Color(-10855846));
        Font minusButtonFont = this.$$$getFont$$$("Helvetica", -1, 20, minusButton.getFont());
        if (minusButtonFont != null) minusButton.setFont(minusButtonFont);
        minusButton.setText("-");
        gbc = new GridBagConstraints();
        gbc.gridx = 7;
        gbc.gridy = 3;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(minusButton, gbc);
        plusButton = new JButton();
        plusButton.setBackground(new Color(-10855846));
        Font plusButtonFont = this.$$$getFont$$$("Helvetica", -1, 20, plusButton.getFont());
        if (plusButtonFont != null) plusButton.setFont(plusButtonFont);
        plusButton.setText("+");
        gbc = new GridBagConstraints();
        gbc.gridx = 7;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(plusButton, gbc);
        backspaceButton = new JButton();
        backspaceButton.setBackground(new Color(-10855846));
        Font backspaceButtonFont = this.$$$getFont$$$("Helvetica", -1, 20, backspaceButton.getFont());
        if (backspaceButtonFont != null)
            backspaceButton.setFont(backspaceButtonFont);
        backspaceButton.setText("⌫");
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(backspaceButton, gbc);
        resetButton = new JButton();
        resetButton.setBackground(new Color(-10855846));
        Font resetButtonFont = this.$$$getFont$$$("Helvetica", -1, 20, resetButton.getFont());
        if (resetButtonFont != null) resetButton.setFont(resetButtonFont);
        resetButton.setText("reset");
        gbc = new GridBagConstraints();
        gbc.gridx = 13;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(resetButton, gbc);
        button1 = new JButton();
        button1.setBackground(new Color(-10855846));
        Font button1Font = this.$$$getFont$$$("Helvetica", -1, 20, button1.getFont());
        if (button1Font != null) button1.setFont(button1Font);
        button1.setText("ⓘ");
        gbc = new GridBagConstraints();
        gbc.gridx = 13;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(button1, gbc);
        historyButton = new JButton();
        historyButton.setBackground(new Color(-10855846));
        Font historyButtonFont = this.$$$getFont$$$("Helvetica", -1, 20, historyButton.getFont());
        if (historyButtonFont != null) historyButton.setFont(historyButtonFont);
        historyButton.setHorizontalTextPosition(0);
        historyButton.setText("History");
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(historyButton, gbc);
        graphicButton = new JButton();
        graphicButton.setBackground(new Color(-10855846));
        Font graphicButtonFont = this.$$$getFont$$$("Helvetica", -1, 20, graphicButton.getFont());
        if (graphicButtonFont != null) graphicButton.setFont(graphicButtonFont);
        graphicButton.setText("Graph");
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(graphicButton, gbc);
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        Font font = new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
        boolean isMac = System.getProperty("os.name", "").toLowerCase(Locale.ENGLISH).startsWith("mac");
        Font fontWithFallback = isMac ? new Font(font.getFamily(), font.getStyle(), font.getSize()) : new StyleContext().getFont(font.getFamily(), font.getStyle(), font.getSize());
        return fontWithFallback instanceof FontUIResource ? fontWithFallback : new FontUIResource(fontWithFallback);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}
