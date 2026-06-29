cmake_minimum_required(VERSION 4.0)
project(ProgrammingLanguagesLab1 C)

set(CMAKE_C_STANDARD 23)

add_executable(ProgrammingLanguagesLab1 main.c
        domain.c
        collection.c
        ui.c)
