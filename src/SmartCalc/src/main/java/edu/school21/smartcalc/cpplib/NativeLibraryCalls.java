package edu.school21.smartcalc.cpplib;

public class NativeLibraryCalls {
    static {
        System.loadLibrary("smartcalc");
    }

    native public static boolean calculate(String inputExpression,
                                                String inputExpressionX);

    native public static boolean calculate(String inputExpression,
                                           double x);

    native public static double getResult();

    native public static boolean checkGraphicParameters(double xMin,
                                                        double xMax,
                                                        double yMin,
                                                        double yMax,
                                                        double step);
}