cmake_minimum_required(VERSION 3.30)
project(tasksCpp)

set(CMAKE_CXX_STANDARD 20)
find_program(CLANG_FORMAT_EXE clang-format)
add_executable(tasksCpp
        E.cpp
        F.cpp
        G.cpp
)
