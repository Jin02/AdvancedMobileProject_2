//
//  AppUI.h
//  Test
//
//  Created by 박 진 on 13. 10. 7..
//
//

#pragma once

#include "cocos2d.h"
#include "TopRight.h"
#include "TopLeft.h"
#include "BotRight.h"
#include "BotLeft.h"
#include "BotCenter.h"
#include "JniComm.h"
#include "ConnectWindow.h"

class AppUI : public cocos2d::CCLayer
{
private:
    enum BUTTON_ID { BOOST, BREAK, LEFT_ITEM, RIGHT_ITEM };
    
private:
    TopRight    *tr;
    TopLeft     *tl;
    BotRight    *br;
    BotLeft     *bl;
    BotCenter   *bc;
    
    ConnectWindow *cw;
    
    bool isStart;
    
    private:
    GageBar *boostGage;
    Status  *status;
    LayerButton *left, *right;
    
    cocos2d::CCSprite *background;
    cocos2d::CCSprite *arrive;
    
public:
    AppUI();
    ~AppUI();

public:
    virtual bool init();

public:
    static cocos2d::CCScene* scene();
    CREATE_FUNC(AppUI);
    
public:
    void Checking(cocos2d::CCObject* pSender);
    
public:
    void _1(cocos2d::CCObject *pSender);
    void _2(cocos2d::CCObject *pSender);
    void _3(cocos2d::CCObject *pSender);
    void _4(cocos2d::CCObject *pSender);
    
    private:
    void Check(int btID);
};