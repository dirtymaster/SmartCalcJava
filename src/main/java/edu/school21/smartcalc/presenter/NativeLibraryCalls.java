package edu.school21.smartcalc.presenter;

public class NativeLibraryCalls {
    static {
        System.loadLibrary("smartcalc");
    }

    native public static boolean calculate(String inputExpression,
                                           double x);

    native public static double getResult();

    native public static boolean checkExpressionCorrectness(
            String inputExpression);
}