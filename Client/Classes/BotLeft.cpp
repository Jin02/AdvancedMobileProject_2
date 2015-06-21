//
//  BotLeft.cpp
//  Test
//
//  Created by 박 진 on 13. 10. 7..
//
//

#include "BotLeft.h"

using namespace cocos2d;

BotLeft::BotLeft()
{
    layerBt = NULL;
}

BotLeft::~BotLeft()
{
    removeAllChildren();
    
    SAFE_DELETE(layerBt);
}

bool BotLeft::init(cocos2d::CCObject *target, cocos2d::SEL_MenuHandler breakButtonHandle)
{
    if( CCNode::init() == false )
        return false;
    
    setAnchorPoint(ccp(0,0));
    setPosition(ccp(0, 0));
    
    layerBt = new LayerButton;
    layerBt->init(target, breakButtonHandle, "BoostButtonBack.png", "ItemFrame.png");
    addChild(layerBt);
    
    layerBt->setPosition(ccp(80, 50));
    layerBt->AddSprite("Boost.png", ccp(5, 7));
    layerBt->SetFrameColor(0, 0, 255);
    
    return true;
}