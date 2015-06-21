//
//  TopLeft.cpp
//  Test
//
//  Created by 박 진 on 13. 10. 7..
//
//

#include "TopLeft.h"

using namespace cocos2d;

TopLeft::TopLeft()
{
    status = NULL;
}

TopLeft::~TopLeft()
{
    removeAllChildrenWithCleanup(true);

    SAFE_DELETE(status);
}

bool TopLeft::init()
{
    if( CCNode::init() == false )
        return false;
    
    status = new Status;
    status->init();
    
    addChild(status);
    setPosition(ccp(0,CCDirector::sharedDirector()->getWinSize().height));
    
    status->SetSpeed(60);
    
    return true;
}

Status* TopLeft::GetStatus()
{
    return status;
}