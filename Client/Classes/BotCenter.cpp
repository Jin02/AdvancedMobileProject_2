//
//  BotCenter.cpp
//  Test
//
//  Created by 박 진 on 13. 10. 7..
//
//

#include "BotCenter.h"

using namespace cocos2d;

BotCenter::BotCenter()
{
    gageBar = NULL;
}

BotCenter::~BotCenter()
{
    removeAllChildren();
    SAFE_DELETE(gageBar);
}

bool BotCenter::init()
{
    if( CCNode::init() == false )
        return false;
    
    setPosition(ccp(CCDirector::sharedDirector()->getWinSize().width/2, 0));


    gageBar = new GageBar;
    gageBar->init("BoostGage.png", true);

    CCSize size = gageBar->GetSize();
    
    gageBar->setPosition(ccp(-size.width/2, 0));
    gageBar->setAnchorPoint(CCPointMake(0.5, 0.5));

    setAnchorPoint(CCPointMake(0.5, 0.5));
    addChild(gageBar);
    
//    gageBar->SetGageValue(0.4);
    
    return true;
}

GageBar* BotCenter::GetGageBar()
{
    return gageBar;
}