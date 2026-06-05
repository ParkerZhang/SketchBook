cmake_minimum_required(VERSION 3.10)
project(bootcamp)
set(CMAKE_CXX_STANDARD 17)

# Add all exercise modules. Comment out any module you don't want to build.
add_subdirectory(exercises/01-basics)
add_subdirectory(exercises/02-oo)
add_subdirectory(exercises/03-stl)
add_subdirectory(exercises/04-modern-cpp)
add_subdirectory(exercises/05-algorithms)
add_subdirectory(exercises/06-data-structures)
add_subdirectory(exercises/07-graphs)
add_subdirectory(exercises/08-dp)
add_subdirectory(exercises/09-math)
