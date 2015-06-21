//
//  JniComm.cpp
//  Test
//
//  Created by 박 진 on 13. 11. 5..
//
//

#include "JniComm.h"
#include "CommData.h"
#include "Connecting.h"
#include "ItemInfo.h"

#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID )

void Java_dku_mse_TestApp_Test_UIRecive(JNIEnv *env, jobject thiz, jint speed, jint boost)
{
    CommData *com = CommData::GetInstance();
    
    com->speed = speed;
    com->boost = boost;
    
    com->update = true;
}

void Java_dku_mse_TestApp_Test_Arrive(JNIEnv *env, jobject thiz, jint time)
{
    CommData *com = CommData::GetInstance();
    com->time = time;
    com->arrive = true;
}

void Java_dku_mse_TestApp_Test_Item(JNIEnv *env, jobject thiz, jint left, jint right)
{
    ItemData *it = ItemData::GetInstance();
    
    it->left = left;
    it->right = right;
    it->update = true;
}

void Java_dku_mse_TestApp_Test_BotConnect(JNIEnv *env, jobject thiz)
{
    ConnectData *c = ConnectData::GetInstance();
    c->bot = true;
}

#endif