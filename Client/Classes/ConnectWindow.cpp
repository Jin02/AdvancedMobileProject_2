//
//  ConnectWindow.cpp
//  Test
//
//  Created by 박 진 on 13. 11. 12..
//
//

#include "ConnectWindow.h"
#include "CommData.h"
#include "Connecting.h"

#include <curl/curl.h>

#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID )
#include <platform/android/jni/JniHelper.h>
#endif

#include <string>

using namespace std;
using namespace cocos2d;

ConnectWindow::ConnectWindow()
{
    
}

ConnectWindow::~ConnectWindow()
{
    removeAllChildren();
    background->removeAllChildren();
    
    SAFE_DELETE(background);

    menu->removeAllChildren();
    
    SAFE_DELETE(bot);
    SAFE_DELETE(admin);
    
    SAFE_DELETE(menu);
    
    SAFE_DELETE(network);
}

bool ConnectWindow::init()
{
    if(CCNode::init() == false)
        return false;
    
    network = new Network;
    
    menu = new CCMenu;
    bot = new CCMenuItemImage;
    admin = new CCMenuItemImage;
    ready = new CCMenuItemImage;
    
    checkAdmin = new CCSprite;
    checkBot = new CCSprite;
    checkReady = new CCSprite;
    
    background = new CCSprite;
    
    background->initWithFile("Panel.png");
    
    bot->initWithNormalImage("BotIcon.png", "BotIcon.png", "", this, menu_selector(ConnectWindow::botfunc));
    admin->initWithNormalImage("AdminIcon.png", "AdminIcon.png", "", this, menu_selector(ConnectWindow::adminfunc));
    ready->initWithNormalImage("Ready.png", "Ready.png", "", this, menu_selector(ConnectWindow::readyfunc));
    
    checkBot->initWithFile("Check.png");
    checkAdmin->initWithFile("Check.png");
    checkReady->initWithFile("Check.png");
    
    CCArray *ary = CCArray::create(bot, admin, ready, NULL);
    menu->initWithArray(ary);
    
    CCSize size = background->getContentSize();
    
    menu->setPosition(ccp(size.width / 2, size.height / 2));
    menu->setAnchorPoint(ccp(0.5, 0.5));
    
    bot->setAnchorPoint(ccp(0.5, 0.5));
    admin->setAnchorPoint(ccp(0.5, 0.5));
    
    bot->setPosition(ccp(-100, 0));
    bot->setScale(2.0f);
    admin->setPosition(ccp(100, 0));
    admin->setScale(2.0f);
    
    ready->setAnchorPoint(ccp(0.5, 0.5));
    ready->setPosition(ccp(0, -175));
    ready->setOpacity(128);
    
    addChild(background);
    background->addChild(menu);
    
    botDes = new CCLabelTTF;
    adminDes = new CCLabelTTF;
    
    botDes->initWithString("Bot Connect", "Marker Felt", 32);
    adminDes->initWithString("Admin Connect", "Marker Felt", 32);
    
    adminDes->setAnchorPoint(ccp(0.5, 0.5));
    botDes->setAnchorPoint(ccp(0.5, 0.5));
 
    botDes->setPosition(ccp(-100,  -90));
    adminDes->setPosition(ccp(100, -90));
    
    botDes->setColor(ccc3(0, 0, 0));
    adminDes->setColor(ccc3(0, 0, 0));
    
    addChild(botDes);
    addChild(adminDes);
    
    checkBot->setAnchorPoint(ccp(0.5, 0.5));
    checkBot->setPosition(ccp(-100, 0));
    checkBot->setScale(2.0f);
    addChild(checkBot);
    checkBot->setVisible(false);
    
    checkAdmin->setAnchorPoint(ccp(0.5, 0.5));
    checkAdmin->setPosition(ccp(100, 0));
    checkAdmin->setScale(2.0f);
    addChild(checkAdmin);
    checkAdmin->setVisible(false);
    
    checkReady->setAnchorPoint(ccp(0.5, 0.5));
    checkReady->setPosition(ccp(0, -175));
    checkReady->setScale(2.0f);
    addChild(checkReady);
    checkReady->setVisible(false);

    readyDone = false;
    connectAdmin = false;
    connectBot = false;
    
    this->schedule(schedule_selector(ConnectWindow::ConnectCheck), 0, -1, 0);
    
    
    return true;
}

void ConnectWindow::ConnectCheck(cocos2d::CCObject* pSender)
{
    ConnectData *c = ConnectData::GetInstance();
    connectBot = c->bot;
    
    checkBot->setVisible(connectBot);
    
    if(connectBot & connectAdmin)
    {
        ready->setOpacity(255);
        this->unschedule(schedule_selector(ConnectWindow::ConnectCheck));
    }
}

void ConnectWindow::readyfunc(cocos2d::CCObject *pSender)
{
    if(ready->getOpacity() != 255)
        return;
    
    if(readyDone)
        return;
    
    CURL_DATA data;
    const char *baseUrl = "http://dkumse02.appspot.com/user/ready?botid=%d";
    char url[256];
    sprintf(url, baseUrl, CommData::GetInstance()->botId);
    
    if(network->connectHttp(url, &data) == CURLE_OK)
    {
        string content = data.pContent;
        
        if(content == "ready fail")
            CCMessageBox("로그인 실패 하셨나 본데요?\n", "알림!");
        else
        {
            checkReady->setVisible(true);
            readyDone = true;
            this->schedule(schedule_selector(ConnectWindow::StartCheck), 0, -1, 0);
        }
    }
}

void ConnectWindow::StartCheck(cocos2d::CCObject *pSender)
{
    CURL_DATA data;
    const char *url = "http://dkumse02.appspot.com/user/start";
    if(network->connectHttp(url, &data) == CURLE_OK)
    {
        string content = data.pContent;
        
        if(content == "start")
        {
            CommData::GetInstance()->start = true;
            this->unschedule(schedule_selector(ConnectWindow::StartCheck));
            
#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID )
            JniMethodInfo info;
            
            if(JniHelper::getStaticMethodInfo(info, "dku/mse/TestApp/Test", "Start", "()V"))
            {
                info.env->CallStaticVoidMethod(info.classID, info.methodID);
                info.env->DeleteLocalRef(info.classID);
            }
#endif
        }
    }
}

void ConnectWindow::botfunc(cocos2d::CCObject *pSender)
{
    if(connectBot)
        return;

#if (CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID )
    JniMethodInfo info;
    
    if(JniHelper::getStaticMethodInfo(info, "dku/mse/TestApp/Test", "ConnectBot", "()V"))
    {
        info.env->CallStaticVoidMethod(info.classID, info.methodID);
        info.env->DeleteLocalRef(info.classID);
    }
#endif
}

void ConnectWindow::adminfunc(cocos2d::CCObject *pSender)
{
    if(connectAdmin)
        return;
    
    CCLOG("ADMIN");
    
    CURL_DATA data;
    const char *url = "http://dkumse02.appspot.com/user/getbotid";
    if(network->connectHttp(url, &data) == CURLE_OK)
    {
        string content = data.pContent;
        
        if(content == "false")
            CCMessageBox("풀방", "알림?");
        else
        {
            CommData::GetInstance()->botId = atoi(content.c_str());
            connectAdmin = true;
            checkAdmin->setVisible(true);
        }
    }
}