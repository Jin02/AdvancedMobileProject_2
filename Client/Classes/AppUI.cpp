//
//  AppUI.cpp
//  Test
//
//  Created by 박 진 on 13. 10. 7..
//
//

#include "AppUI.h"

#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID )
#include <platform/android/jni/JniHelper.h>
#endif


#include "CommData.h"
#include "ItemInfo.h"
#include "NetWork.h"

using namespace cocos2d;

AppUI::AppUI()
{
    tr = NULL;
    tl = NULL;
    br = NULL;
    bl = NULL;
    bc = NULL;
    
    background = NULL;
    arrive = NULL;
}

AppUI::~AppUI()
{
    removeAllChildren();
    
    SAFE_DELETE(tr);
    SAFE_DELETE(tl);
    SAFE_DELETE(br);
    SAFE_DELETE(bl);
    SAFE_DELETE(bc);
    SAFE_DELETE(background);
    SAFE_DELETE(status);
    SAFE_DELETE(boostGage);
    SAFE_DELETE(left);
    SAFE_DELETE(right);
    SAFE_DELETE(cw);
    SAFE_DELETE(arrive);
}

CCScene* AppUI::scene()
{
    CCScene *scene = CCScene::create();
    AppUI *layer = AppUI::create();
    scene->addChild(layer);
    
    return scene;
}

bool AppUI::init()
{
    if(CCLayer::init() == false)
        return false;    
    
    tr = new TopRight;
    tr->init(this, menu_selector(AppUI::_3), menu_selector(AppUI::_4));
    addChild(tr);
    
    tl = new TopLeft;
    tl->init();
    addChild(tl);
    
    br = new BotRight;
    br->init(this, menu_selector(AppUI::_2));
    addChild(br);
    
    bl = new BotLeft;
    bl->init(this, menu_selector(AppUI::_1));
    addChild(bl);
    
    bc = new BotCenter;
    bc->init();
    bc->setAnchorPoint(CCPointMake(0.5, 0.5));
    addChild(bc);
    
    boostGage = bc->GetGageBar();
    status = tl->GetStatus();
    left = tr->GetItemsUI()->GetLeft();
    right = tr->GetItemsUI()->GetRight();
    
    this->schedule(schedule_selector(AppUI::Checking), 0.0f, -1, 0.0f);
    
    cw = new ConnectWindow;
    cw->init();
    cw->setAnchorPoint(ccp(0.5, 0.5));
    CCSize size = CCDirector::sharedDirector()->getWinSize();
    cw->setPosition(ccp(size.width / 2, size.height / 2));
    addChild(cw);
    
    isStart = false;
    
//    CCMessageBox("기기 설정가서 Admin기기의 이름을 Admin으로 설정 및 페어링 좀 해줄래요?", "알림");

    CCMessageBox("좋은 말씀 전해드리러 왔습니다.", "문 좀 열어주세요.");

//    ItemData *it = ItemData::GetInstance();
//    
//    it->update = true;
//    it->left = 0;
//    it->right = 1;
//    
//    this->schedule(schedule_selector(AppUI::Checking), 0, -1, 0);
    
    background = new CCSprite;
    background->initWithFile("Background.jpg");
    background->setAnchorPoint(ccp(0.5, 0.5));
    CCSize screenSize = CCDirector::sharedDirector()->getWinSize();
    CCPoint screenCenter = ccp(screenSize.width/2, screenSize.height/2);
    background->setPosition(screenCenter);
    addChild(background, -10);
    
    
    arrive = new CCSprite;
    arrive->initWithFile("Arrive.png");
    arrive->setAnchorPoint(ccp(0.5, 0.5));
    arrive->setPosition(screenCenter);
    arrive->setVisible(false);
    
    return true;
}

void AppUI::Checking(cocos2d::CCObject* pSender)
{
    static bool arriveFlag = false;
    CommData *com = CommData::GetInstance();
    
    isStart = com->start;
    if(com->start == false)
        return;
    
    if(com->arrive == 1)
    {
        CCLOG("arrive!");
        
        if(arriveFlag == false)
        {
            Network *network = new Network;
            
            CURL_DATA data;
            const char *baseUrl = "dkumse02.appspot.com/user/arrive?botid=%d&time=%d";
            char url[256];
            sprintf(url, baseUrl, com->botId, com->time);
            network->connectHttp(url, &data);
            delete network;
            
            arriveFlag = true;
        }
        
        arrive->setVisible(true);
        return;
    }
    
    if(com->update)
    {
        boostGage->SetGageValue((float)com->boost / 100.0f);
        status->SetSpeed(com->speed);
        
        //left, right
        
        cw->setVisible(false);
        com->update = false;
    }
    
    ItemData *it = ItemData::GetInstance();
    
    if(it->update)
    {
        CCLOG("ITEM IN %d %d", it->left, it->right);
        left->AddItem(it->left);
        right->AddItem(it->right);
        
        it->update = false;
    }
}

void AppUI::_1(cocos2d::CCObject *pSender)
{
#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID )
    JniMethodInfo info;
    
    if(JniHelper::getStaticMethodInfo(info, "dku/mse/TestApp/Test", "Boost", "()V"))
    {
        info.env->CallStaticVoidMethod(info.classID, info.methodID);
        info.env->DeleteLocalRef(info.classID);
    }
    
#endif
}

void AppUI::_2(cocos2d::CCObject *pSender)
{
#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID )
    JniMethodInfo info;
    
    if(JniHelper::getStaticMethodInfo(info, "dku/mse/TestApp/Test", "Break", "()V"))
    {
        info.env->CallStaticVoidMethod(info.classID, info.methodID);
        info.env->DeleteLocalRef(info.classID);
    }
    
#endif

}

void AppUI::_3(cocos2d::CCObject *pSender)
{
    CCLOG("use left item");
    left->UseItem();

    
#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID )
    JniMethodInfo info;

    if(JniHelper::getStaticMethodInfo(info, "dku/mse/TestApp/Test", "LeftItem", "()V"))
    {
        info.env->CallStaticVoidMethod(info.classID, info.methodID);
        info.env->DeleteLocalRef(info.classID);
    }
    
#endif

}

void AppUI::_4(cocos2d::CCObject *pSender)
{
    CCLOG("use right item");
    right->UseItem();
    
#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID )
    JniMethodInfo info;
    
    if(JniHelper::getStaticMethodInfo(info, "dku/mse/TestApp/Test", "RightItem", "()V"))
    {
        info.env->CallStaticVoidMethod(info.classID, info.methodID);
        info.env->DeleteLocalRef(info.classID);
    }
    
#endif

}