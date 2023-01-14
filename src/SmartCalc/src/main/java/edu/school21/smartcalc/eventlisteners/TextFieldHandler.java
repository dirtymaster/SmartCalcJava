package edu.school21.smartcalc.eventlisteners;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TextFieldHandler {
    private JTextField currentTextField;

    public TextFieldHandler(JTextField currentTextField) {
        this.currentTextField = currentTextField;
    }

    public void setCurrentTextField(JTextField currentTextField) {
        this.currentTextField = currentTextField;
    }

    public void addText(String text) {
        currentTextField.setText(currentTextField.getText() + text);
    }

    public void addActionListener(JButton button, String text) {
        button.addActionListener(e -> addText(text));
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
