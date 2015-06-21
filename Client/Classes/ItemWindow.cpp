//
//  Item.cpp
//  Test
//
//  Created by 박 진 on 13. 10. 7..
//
//

#include "ItemWindow.h"

using namespace cocos2d;

ItemWindow::ItemWindow()
{
    leftBt = NULL;
    rightBt = NULL;
}

ItemWindow::~ItemWindow()
{
    removeAllChildrenWithCleanup(true);
    
    SAFE_DELETE(leftBt);
    SAFE_DELETE(rightBt);
}

bool ItemWindow::init(cocos2d::CCObject *buttonTarget, cocos2d::SEL_MenuHandler left, cocos2d::SEL_MenuHandler right)
{
    if(CCNode::init() == false)
        return false;
    
    leftBt  = new LayerButton;
    rightBt = new LayerButton;
    
    leftBt->init(buttonTarget, left, "ItemBackground.png", "ItemFrame.png");
    leftBt->SetFramePosition(CCPointMake(-5, -5));
    
    rightBt->init(buttonTarget, right, "ItemBackground.png", "ItemFrame.png");
    rightBt->SetFramePosition(CCPointMake(-5, -5));
    
    addChild(leftBt);
    addChild(rightBt);
    
    leftBt->setPosition(ccp(-45, 0));
    rightBt->setPosition(ccp(45, 0));

    leftBt->SetFrameColor(255, 0, 0);
    rightBt->SetFrameColor(255, 0, 0);
    
    return true;
}