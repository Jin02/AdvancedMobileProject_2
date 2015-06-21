//
//  Status.cpp
//  Test
//
//  Created by 박 진 on 13. 10. 8..
//
//

#include "Status.h"
#include "Utility.h"
#include <string>

using namespace cocos2d;

Status::Status()
{
    for(int i=0; i<2; ++i)
        circleStatus[i] = NULL;
    
    gageBar = NULL;
}

Status::~Status()
{
    removeAllChildrenWithCleanup(true);
    
    circleStatus[0]->removeAllChildrenWithCleanup(true);
    
    
    for(int i=0; i<2; ++i)
        SAFE_DELETE(circleStatus[i]);
    
    SAFE_DELETE(circleBack);
    SAFE_DELETE(gageBar);
}

bool Status::init(int maxSpeed)
{
    if( CCNode::init() == false )
        return false;
    
    for(int i=0; i<2; ++i)
        circleStatus[i] = new CCSprite;
    
    circleStatus[0]->initWithFile("StatusFrame.png");
    circleStatus[1]->initWithFile("StatusGage.png");
    
//    circleStatus[0]->addChild(circleStatus[1], -1);
    circleStatus[1]->setAnchorPoint(ccp(0,0));
    circleStatus[1]->setColor(ccc3(255, 0, 0));
    circleStatus[1]->setOpacity(255);
        
    addChild(circleStatus[0], 1);
    
    circleStatus[0]->setPosition(ccp(0, 0));
    
    setAnchorPoint(ccp(0, 1));
    setPosition(ccp(80, -80));
    
    circleBack = new CCSprite;
    circleBack->initWithFile("StatusBack.png");
    circleBack->setAnchorPoint(ccp(0,0));
    circleBack->setColor(ccc3(0, 0, 0));
    circleStatus[0]->addChild(circleBack, -2);
    
    label = new CCLabelTTF;
    label->initWithString("0", "Marker Felt", 26);
    label->setColor(ccc3(0, 0, 0));
    label->setAnchorPoint(ccp(0, 0));
    label->setPosition(ccp(60, 50));

    circleStatus[0]->addChild(label, 1);
    
    gageBar = new GageBar;
    gageBar->init("SpeedGage.png", true);
    addChild(gageBar);
    
    this->maxSpeed = maxSpeed;
    
    timer = new CCProgressTimer;
    timer->initWithSprite(circleStatus[1]);
    timer->setType(kCCProgressTimerTypeRadial);
    timer->setReverseDirection(true);
    timer->setAnchorPoint(CCPointZero);
    circleStatus[0]->addChild(timer, -1);
    
    return true;
}

void Status::SetSpeed(int percentage)
{
    if(percentage > maxSpeed)
        percentage = maxSpeed;
    
    char str[6];
    sprintf(str, "%d", percentage * 9);
    
    label->setString(str);
    
    float f = (float)percentage / (float)maxSpeed * 100.0f;
    timer->setPercentage(f*6.25);
    gageBar->SetGageValue(f/100.0f);
}