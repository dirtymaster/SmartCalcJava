#include "edu_school21_smartcalc_presenter_NativeLibraryCalls.h"

JNIEXPORT jboolean JNICALL
Java_edu_school21_smartcalc_presenter_NativeLibraryCalls_calculate(
    JNIEnv *env, jclass, jstring string, jdouble x) {
    const char *native_string = env->GetStringUTFChars(string, 0);
    return s21::Controller::GetInstance()->Calculate(native_string, x);
}

JNIEXPORT jdouble JNICALL
Java_edu_school21_smartcalc_presenter_NativeLibraryCalls_getResult(JNIEnv *,
                                                                   jclass) {
    return s21::Controller::GetInstance()->GetResult();
}

JNIEXPORT jboolean JNICALL
Java_edu_school21_smartcalc_presenter_NativeLibraryCalls_checkExpressionCorrectness(
    JNIEnv *env, jclass, jstring string) {
    const char *native_string = env->GetStringUTFChars(string, 0);
    return s21::Controller::GetInstance()->CheckExpressionCorrectness(
        native_string);
}