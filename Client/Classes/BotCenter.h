//
//  BotCenter.h
//  Test
//
//  Created by 박 진 on 13. 10. 7..
//
//

#pragma once

#include <cocos2d.h>
#include "GageBar.h"

class BotCenter : public cocos2d::CCNode
{
private:
    GageBar *gageBar;
    
public:
    BotCenter();
    ~BotCenter();
    
public:
    virtual bool init();
    GageBar* GetGageBar();
};