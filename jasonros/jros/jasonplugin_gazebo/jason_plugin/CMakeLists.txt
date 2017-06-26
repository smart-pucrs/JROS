cmake_minimum_required(VERSION 2.8 FATAL_ERROR)
include(CheckCXXCompilerFlag)
# Find Gazebo
find_package(gazebo REQUIRED)
include_directories(${GAZEBO_INCLUDE_DIRS})
link_directories(${GAZEBO_LIBRARY_DIRS})
find_package(catkin REQUIRED COMPONENTS
  roscpp
)
include_directories(
  ${catkin_INCLUDE_DIRS}
)


set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} ${GAZEBO_CXX_FLAGS}")
CHECK_CXX_COMPILER_FLAG("-std=c++11 -rdynamic" COMPILER_SUPPORTS_CXX11)
if(COMPILER_SUPPORTS_CXX11)
    set(CMAKE_CXX_FLAGS "-std=c++11 -rdynamic")
endif()

# Build our plugin
add_library(jason_plugin SHARED jason_plugin.cc)
target_link_libraries(jason_plugin ${GAZEBO_libraries})
target_link_libraries(jason_plugin
  ${catkin_LIBRARIES}
)
target_link_libraries(jason_plugin "/home/iancalaca/jason/projects/JROS/jasonros/jros/cpplib/jros/catkin_ws/devel/lib/libjroslib.so")
