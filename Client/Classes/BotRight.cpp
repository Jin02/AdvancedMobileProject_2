//
//  BotRight.cpp
//  Test
//
//  Created by 박 진 on 13. 10. 7..
//
//

#include "BotRight.h"

using namespace cocos2d;

BotRight::BotRight()
{
    layerButton = NULL;
}

BotRight::~BotRight()
{
    removeAllChildren();
    
    SAFE_DELETE(layerButton);
}

bool BotRight::init(cocos2d::CCObject *target, SEL_MenuHandler boostBtHandle)
{
    if( CCNode::init() == false )
        return false;
    
    CCSize winSize = CCDirector::sharedDirector()->getWinSize();
    setPosition(ccp(winSize.width, 0));
    
    layerButton = new LayerButton;
    layerButton->init(target, boostBtHandle, "ItemBackground.png", "ItemFrame.png");
    layerButton->SetFramePosition( CCPointMake(-5, -5) );
    addChild(layerButton);
    
    layerButton->setPosition(ccp(-80, 50));
    layerButton->AddSprite("Brake.png", ccp(0, 2));
    layerButton->SetFrameColor(0, 0, 255);
    
    return true;
}