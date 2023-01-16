package edu.school21.smartcalc.presenter.history;

import javax.swing.*;
import java.io.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class HistoryFile {
    private String historyFileName;
    private File file;

    public HistoryFile(String historyFileName) {
        this.historyFileName = historyFileName;
        file = new File(historyFileName);
    }

    public Set<String> loadExpressionsFromHistoryFile() {
        Set<String> expressions = new HashSet<>();
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            Scanner fileScanner = new Scanner(fileInputStream);
            while (fileScanner.hasNextLine()) {
                expressions.add(fileScanner.nextLine());
            }
        } catch (IOException e) {
            System.err.println("File \"\"" + historyFileName + "\" not found");
        }
        return expressions;
    }

    public void addExpressionToHistoryFile(String expression) {
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(historyFileName, true))) {
            bw.write(expression);
            bw.newLine();
        } catch (IOException exception) {
            System.err.println(
                    "File \"" + historyFileName + "\" not found");
        }
    }
}
