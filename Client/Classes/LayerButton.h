//
//  LayerButton.h
//  Test
//
//  Created by 박 진 on 13. 10. 10..
//
//

#pragma once

#include <cocos2d.h>
#include "Utility.h"
#include <string>

class LayerButton : public cocos2d::CCNode
{
private:
    cocos2d::CCMenuItemImage    *button;
    cocos2d::CCSprite           *buttonFrame;
    cocos2d::CCMenu             *menu;
    
    cocos2d::CCSprite           *item;
    int itemId;
    
public:
    LayerButton();
    ~LayerButton();
    
public:
    virtual bool init(cocos2d::CCObject *target, cocos2d::SEL_MenuHandler handler, const char* backgroundImg, const char* frameImg);
    
public:
    void SetFramePosition(cocos2d::CCPoint pos);
    void AddItem(int itemId);
    void AddSprite(const char *textureName, cocos2d::CCPoint pos);
    void UseItem();
    void SetFrameColor(GLubyte r, GLubyte g, GLubyte b);
};