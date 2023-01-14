#include <iostream>
#include "library.h"

JNIEXPORT void JNICALL Java_edu_school21_smartcalc_NativeCalls_print
        (JNIEnv *, jclass) {
    std::cout << "test" << std::endl;
}