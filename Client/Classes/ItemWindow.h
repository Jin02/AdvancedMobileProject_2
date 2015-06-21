//
//  Item.h
//  Test
//
//  Created by 박 진 on 13. 10. 7..
//
//

#pragma once

#include <cocos2d.h>
#include "Utility.h"
#include "LayerButton.h"

class ItemWindow : public cocos2d::CCNode
{
private:
    LayerButton *leftBt;
    LayerButton *rightBt;
    
public:
    ItemWindow();
    ~ItemWindow();
    
public:
    virtual bool init(cocos2d::CCObject *buttonTarget, cocos2d::SEL_MenuHandler left, cocos2d::SEL_MenuHandler right);
    
    public:
    LayerButton* GetLeft()
    {
        return leftBt;
    }

    LayerButton* GetRight()
    {
        return rightBt;
    }
};