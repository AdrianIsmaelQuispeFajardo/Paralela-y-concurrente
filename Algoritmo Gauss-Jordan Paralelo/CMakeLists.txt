cmake_minimum_required(VERSION 3.26)
project(Paralelo C)

set(CMAKE_C_STANDARD 11)

# Encuentra y habilita OpenMP
find_package(OpenMP REQUIRED)

add_executable(Paralelo main.c)

# Enlaza tu proyecto con OpenMP
target_link_libraries(Paralelo PRIVATE OpenMP::OpenMP_C)
