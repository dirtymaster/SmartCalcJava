cmake_minimum_required(VERSION 3.23)
project(test)

find_package(JNI REQUIRED)
include_directories(${JNI_INCLUDE_DIRS})

set(CMAKE_CXX_STANDARD 17)

add_library(test SHARED library.cpp)
