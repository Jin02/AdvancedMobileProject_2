//
//  TopRight.cpp
//  Test
//
//  Created by 박 진 on 13. 10. 7..
//
//

#include "TopRight.h"

using namespace cocos2d;

TopRight::TopRight()
{
    items = NULL;
}

TopRight::~TopRight()
{
    removeAllChildren();
    SAFE_DELETE(items);
}

bool TopRight::init(cocos2d::CCObject *target, cocos2d::SEL_MenuHandler leftItem, cocos2d::SEL_MenuHandler rightItem)
{
    if( CCNode::init() == false )
        return false;

    CCSize winSize;
    winSize.width = CCDirector::sharedDirector()->getWinSize().width;
    winSize.height = CCDirector::sharedDirector()->getWinSize().height;
    
    items = new ItemWindow;
    items->init(target, leftItem, rightItem);
    items->setPosition( -120, -60 );
  
    setPosition(ccp(winSize.width, winSize.height));
    addChild(items);
    
    return true;
}

ItemWindow* TopRight::GetItemsUI() const
{
    return items;
}