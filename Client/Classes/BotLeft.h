//
//  BotLeft.h
//  Test
//
//  Created by 박 진 on 13. 10. 7..
//
//

#pragma once

#include <cocos2d.h>
#include "LayerButton.h"

class BotLeft : public cocos2d::CCNode
{
private:
    LayerButton *layerBt;
    
public:
    BotLeft();
    ~BotLeft();
    
public:
    virtual bool init(cocos2d::CCObject *target, cocos2d::SEL_MenuHandler breakButtonHandle);
};