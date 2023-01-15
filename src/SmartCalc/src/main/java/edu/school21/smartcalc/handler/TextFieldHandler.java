package edu.school21.smartcalc.handler;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TextFieldHandler {
    private JTextField currentTextField = null;

    public TextFieldHandler(JTextField currentTextField) {
        setCurrentTextField(currentTextField);
    }

    public void setCurrentTextField(JTextField currentTextField) {
        if (this.currentTextField != null) {
            this.currentTextField.setBorder(
                    new LineBorder(new Color(120, 120, 120), 1));
        }
        this.currentTextField = currentTextField;
        currentTextField.setBorder(new LineBorder(new Color(255, 149, 0), 1));
    }

    public void addText(String text) {
        currentTextField.setText(currentTextField.getText() + text);
    }

    public void addUppendingActionListener(JButton button, String text) {
        button.addActionListener(e -> addText(text));
    }

    public void addBackspaceActionListener(JButton button) {
        button.addActionListener(e -> {
            String text = currentTextField.getText();
            currentTextField.setText(text.substring(0, text.length() - 1));
        });
    }

    public void addMouseListener(JTextField textField) {
        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setCurrentTextField(textField);
            }
        });
    }


    public void clear() {
        currentTextField.setText("");
    }
}
