//
//  ConnectWindow.h
//  Test
//
//  Created by 박 진 on 13. 11. 12..
//
//

#pragma once

#include <cocos2d.h>
#include "Utility.h"
#include "NetWork.h"

class ConnectWindow : public cocos2d::CCNode
{
private:
    Network *network;
    
    cocos2d::CCSprite *background;

    cocos2d::CCMenu *menu;
    cocos2d::CCMenuItemImage *bot;
    cocos2d::CCMenuItemImage *admin;
    cocos2d::CCMenuItemImage *ready;
    
    cocos2d::CCSprite *checkBot;
    cocos2d::CCSprite *checkAdmin;
    cocos2d::CCSprite *checkReady;
    
    cocos2d::CCLabelTTF *botDes;
    cocos2d::CCLabelTTF *adminDes;
    
    bool connectBot;
    bool connectAdmin;
    bool readyDone;
    
public:
    ConnectWindow();
    ~ConnectWindow();
    
public:
    bool init();
    
private:
    void StartCheck(cocos2d::CCObject *pSender);
    void ConnectCheck(cocos2d::CCObject* pSender);
    void botfunc(cocos2d::CCObject *pSender);
    void adminfunc(cocos2d::CCObject *pSender);
    void readyfunc(cocos2d::CCObject *pSender);
};