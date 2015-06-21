//
//  GageBar.h
//  Test
//
//  Created by 박 진 on 13. 10. 8..
//
//

#pragma once

#include <cocos2d.h>
#include "Utility.h"

class GageBar : public cocos2d::CCNode
{
private:
    cocos2d::CCSprite *back;
    cocos2d::CCSprite *front;
    cocos2d::CCProgressTimer *timer;
    
    cocos2d::CCSize originalSize;
    
    bool isHorizontal;
    
public:
    GageBar();
    ~GageBar();
    
public:
    virtual bool init(const char *textureName, bool isHorizontal);
    
public:
    void SetGageValue(float f);
    float GetGageValue();
    
    cocos2d::CCSize GetSize();
};