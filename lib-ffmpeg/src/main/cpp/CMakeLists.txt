cmake_minimum_required(VERSION 3.4.1)

#导入include相关文件
include_directories(ffmpeg)

#加载so库
set(JNI_LIBS_DIR ${CMAKE_SOURCE_DIR}/../jni/${ANDROID_ABI})
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -L${JNI_LIBS_DIR} ")

#设置要编译的cpp文件
file(GLOB SOURCE ${CMAKE_CURRENT_SOURCE_DIR}/*.cpp)
add_library(native-lib SHARED ${SOURCE} )

#日志 so
find_library(log-lib log )

#连接到库
target_link_libraries(native-lib
        avfilter avformat avcodec avutil swresample swscale
        ${log-lib} )