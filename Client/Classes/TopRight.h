//
//  TopRight.h
//  Test
//
//  Created by 박 진 on 13. 10. 7..
//
//

#pragma once

#include <cocos2d.h>
#include "ItemWindow.h"

class TopRight : public cocos2d::CCNode
{
private:
    ItemWindow *items;

public:
    TopRight();
    ~TopRight();
    
public:
    virtual bool init(cocos2d::CCObject *target, cocos2d::SEL_MenuHandler leftItem, cocos2d::SEL_MenuHandler rightItem);
    
    ItemWindow* GetItemsUI() const;
};