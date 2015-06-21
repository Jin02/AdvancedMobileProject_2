#pragma once


#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID )

#include <platform/android/jni/JniHelper.h>

        #ifdef __cplusplus
        extern "C" {
        #endif

            void Java_dku_mse_TestApp_Test_UIRecive(JNIEnv *env, jobject thiz, jint speed, jint boost);

            void Java_dku_mse_TestApp_Test_BotConnect(JNIEnv *env, jobject thiz);

            void Java_dku_mse_TestApp_Test_Item(JNIEnv *env, jobject thiz, jint left, jint right);
            
            void Java_dku_mse_TestApp_Test_Arrive(JNIEnv *env, jobject thiz, jint time);

        #ifdef __cplusplus
        }
        #endif

#endif