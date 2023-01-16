cmake_minimum_required(VERSION 3.23)
project(smartcalc)

find_package(JNI REQUIRED)
include_directories(${JNI_INCLUDE_DIRS})

set(CMAKE_CXX_STANDARD 17)

add_library(smartcalc SHARED Model/Model.cpp Controller/Controller.cpp edu_school21_smartcalc_presenter_NativeLibraryCalls.cpp)