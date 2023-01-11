package edu.school21.smartcalc;

public class NativeCalls {
    static {
        System.loadLibrary("smartcalc");
    }

    native public static void print();
}