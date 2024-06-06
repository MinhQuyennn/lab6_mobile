@echo off
"E:\\mobile\\cmake\\3.22.1\\bin\\cmake.exe" ^
  "-HC:\\Users\\NGOC HUYEN\\Downloads\\Lab_Exercise_06-main\\Lab_Exercise_06-main\\removeshadow\\sdk\\libcxx_helper" ^
  "-DCMAKE_SYSTEM_NAME=Android" ^
  "-DCMAKE_EXPORT_COMPILE_COMMANDS=ON" ^
  "-DCMAKE_SYSTEM_VERSION=21" ^
  "-DANDROID_PLATFORM=android-21" ^
  "-DANDROID_ABI=x86" ^
  "-DCMAKE_ANDROID_ARCH_ABI=x86" ^
  "-DANDROID_NDK=E:\\mobile\\ndk\\25.1.8937393" ^
  "-DCMAKE_ANDROID_NDK=E:\\mobile\\ndk\\25.1.8937393" ^
  "-DCMAKE_TOOLCHAIN_FILE=E:\\mobile\\ndk\\25.1.8937393\\build\\cmake\\android.toolchain.cmake" ^
  "-DCMAKE_MAKE_PROGRAM=E:\\mobile\\cmake\\3.22.1\\bin\\ninja.exe" ^
  "-DCMAKE_LIBRARY_OUTPUT_DIRECTORY=C:\\Users\\NGOC HUYEN\\Downloads\\Lab_Exercise_06-main\\Lab_Exercise_06-main\\removeshadow\\sdk\\build\\intermediates\\cxx\\Debug\\k636t504\\obj\\x86" ^
  "-DCMAKE_RUNTIME_OUTPUT_DIRECTORY=C:\\Users\\NGOC HUYEN\\Downloads\\Lab_Exercise_06-main\\Lab_Exercise_06-main\\removeshadow\\sdk\\build\\intermediates\\cxx\\Debug\\k636t504\\obj\\x86" ^
  "-DCMAKE_BUILD_TYPE=Debug" ^
  "-BC:\\Users\\NGOC HUYEN\\Downloads\\Lab_Exercise_06-main\\Lab_Exercise_06-main\\removeshadow\\sdk\\.cxx\\Debug\\k636t504\\x86" ^
  -GNinja ^
  "-DANDROID_STL=c++_shared"
