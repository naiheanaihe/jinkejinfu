LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
# 存储您要构建的模块的名称,并指定想生成的 so 叫什么名字。当然生成产物的时候前面会自动拼接上 lib,后面会自动拼接上 .so
LOCAL_MODULE :=MyCshi
LOCAL_SRC_FILES := test.cpp  # 要编译的源文件，多个文件以空格分开即可。当导入 .a 或者 .so 文件的时候一个模块只能添加一个文件
LOCAL_C_INCLUDES += $(LOCAL_PATH)
 #LOCAL_LDLIBS := -llog  # 添加本地依赖库


include $(BUILD_SHARED_LIBRARY)
