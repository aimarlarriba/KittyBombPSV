cmake_minimum_required(VERSION 3.10)
project(KittyBombPSV)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)

if(NOT DEFINED VITASDK)
    message(FATAL_ERROR "VITASDK no esta definido")
endif()

include("${VITASDK}/share/vita.cmake" REQUIRED)

file(GLOB_RECURSE SRC_FILES src/*.cpp)

add_executable(${PROJECT_NAME} ${SRC_FILES})
target_include_directories(${PROJECT_NAME} PRIVATE src)

target_link_libraries(${PROJECT_NAME} 
    SceDisplay_stub SceGxm_stub SceSysmodule_stub 
    SceCtrl_stub SceTouch_stub SceAudio_stub 
    ScePower_stub SceLibc_stub SDL2 SDL2_image m
)

vita_create_self(eboot.bin ${PROJECT_NAME})
vita_create_vpk(${PROJECT_NAME}.vpk ${PROJECT_NAME} eboot.bin
    VERSION 01.00
    NAME "KittyBomb"
    TITLEID "KTBM00001"
    FILE assets assets
)
