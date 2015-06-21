//
//  BotRight.h
//  Test
//
//  Created by 박 진 on 13. 10. 7..
//
//

#pragma once

#include <cocos2d.h>
#include "LayerButton.h"

class BotRight : public cocos2d::CCNode
{
private:    
    LayerButton *layerButton;
    
public:
    BotRight();
    ~BotRight();
    
public:
    virtual bool init(cocos2d::CCObject *target, cocos2d::SEL_MenuHandler boostBtHandle);
};
