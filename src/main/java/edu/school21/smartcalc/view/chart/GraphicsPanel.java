package edu.school21.smartcalc.view.chart;

import edu.school21.smartcalc.presenter.NativeLibraryCalls;

import javax.swing.*;
import java.awt.*;

public class GraphicsPanel extends JPanel {
    private final int width;
    private final int height;
    private double xMin;
    private double xMax;
    private double yMin;
    private double yMax;
    private double step;
    private String expression;
    private double xCoefficient;
    private double yCoefficient;
    private double startX;
    private double startY;

    public GraphicsPanel(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public void setParams(double xMin, double xMax, double yMin, double yMax,
                          double step, String expression) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = -yMax;
        this.yMax = -yMin;
        this.step = step;
        this.expression = expression;
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        paintCoordinateField(graphics2D);
        paintGraphic(graphics2D);
    }

    public void paintCoordinateField(Graphics2D graphics2D) {
        double xWidth = xMax - xMin;
        double yHeight = yMax - yMin;
        xCoefficient = width / xWidth;
        yCoefficient = height / yHeight;

        // оси координат
        graphics2D.setColor(Color.GRAY);
        startX = width / 2;
        if (xMax <= 0) {
            startX = width + (width / xWidth * (-xMax));
        }
        if (xMin >= 0) {
            startX = -(width / xWidth * xMin);
        }
        if (xMin < 0 && xMax > 0) {
            startX = Math.abs(xMin) / (Math.abs(xMin) + xMax) * width;
            graphics2D.draw(new Line(new edu.school21.smartcalc.view.chart.Point(startX, 0), new edu.school21.smartcalc.view.chart.Point(startX, height)));
        }
        startY = height / 2;
        if (yMax <= 0) {
            startY = height + (height / yHeight * (-yMax));
        }
        if (yMin >= 0) {
            startY = -(height / yHeight * yMin);
        }
        if (yMin < 0 && yMax > 0) {
            startY = Math.abs(yMin) / (Math.abs(yMin) + yMax) * height;
            graphics2D.draw(new Line(new edu.school21.smartcalc.view.chart.Point(0, startY), new edu.school21.smartcalc.view.chart.Point(width, startY)));
        }
        // засечки по x
        for (int i = (int) xMin; i < (int) xMax; ++i) {
            if (i != 0) {
                edu.school21.smartcalc.view.chart.Point startPoint = new edu.school21.smartcalc.view.chart.Point(width / xWidth * i + startX, 0);
                edu.school21.smartcalc.view.chart.Point endPoint = new edu.school21.smartcalc.view.chart.Point(width / xWidth * i + startX, 3);
                graphics2D.draw(new Line(startPoint, endPoint));
            }
        }
        // засечки по y
        for (int i = (int) yMin; i < (int) yMax; ++i) {
            if (i != 0) {
                edu.school21.smartcalc.view.chart.Point startPoint = new edu.school21.smartcalc.view.chart.Point(0, height / yHeight * i + startY);
                edu.school21.smartcalc.view.chart.Point endPoint = new edu.school21.smartcalc.view.chart.Point(3, height / yHeight * i + startY);
                graphics2D.draw(new Line(startPoint, endPoint));
            }
        }
    }

    private void paintGraphic(Graphics2D graphics2D) {
        graphics2D.setColor(new Color(255, 149, 0));
        boolean shoultWePaint = NativeLibraryCalls.calculate(expression, xMin);
        edu.school21.smartcalc.view.chart.Point startPoint = null;
        if (shoultWePaint) {
            startPoint = new edu.school21.smartcalc.view.chart.Point(xMin * xCoefficient + startX,
                    -NativeLibraryCalls.getResult() * yCoefficient + startY);
        }
        for (double i = xMin + step; i <= xMax; i += step) {
            shoultWePaint = NativeLibraryCalls.calculate(expression, i);
            if (shoultWePaint) {
                if (startPoint == null) {
                    startPoint = new edu.school21.smartcalc.view.chart.Point(i * xCoefficient + startX,
                            -NativeLibraryCalls.getResult()
                                    * yCoefficient + startY);
                } else {
                    edu.school21.smartcalc.view.chart.Point endPoint = new Point(i * xCoefficient + startX,
                            -NativeLibraryCalls.getResult() * yCoefficient + startY);
                    graphics2D.draw(new Line(startPoint, endPoint));
                    startPoint = endPoint;
                }
            } else {
                startPoint = null;
            }
        }
    }
}
