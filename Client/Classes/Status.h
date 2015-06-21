//
//  Status.h
//  Test
//
//  Created by 박 진 on 13. 10. 8..
//
//

#pragma once

#include <cocos2d.h>
#include "GageBar.h"

class Status : public cocos2d::CCNode
{
private:
    cocos2d::CCSprite *circleBack;
    cocos2d::CCSprite *circleStatus[2];
    cocos2d::CCProgressTimer *timer;
    
    GageBar *gageBar;
    
    cocos2d::CCLabelTTF *label;
    
    int maxSpeed;
    
public:
    Status();
    ~Status();

public:
    virtual bool init(int maxSpeed = 100);
    
public:
    void SetSpeed(int percentage);
};