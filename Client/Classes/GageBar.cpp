//
//  GageBar.cpp
//  Test
//
//  Created by 박 진 on 13. 10. 8..
//
//

#include "GageBar.h"

using namespace cocos2d;

GageBar::GageBar()
{
    back = NULL;
    front = NULL;

}

GageBar::~GageBar()
{
    removeAllChildrenWithCleanup(true);
    
    if(back)
        delete back;
    if(front)
        delete front;
}

bool GageBar::init(const char *textureName, bool isHorizontal)
{
    if( CCNode::init() == false )
        return false;
    
    back = new CCSprite;
    front = new CCSprite;
    
    back->initWithFile(textureName);
    front->initWithFile(textureName);
    
    back->setAnchorPoint(CCPointZero);
    front->setAnchorPoint(CCPointZero);
    
    back->setColor(ccc3(100, 100, 100));
    
    this->isHorizontal = isHorizontal;
    
    originalSize = front->getTextureRect().size;
    
    addChild(back);
    addChild(front);
    
    return true;
}

void GageBar::SetGageValue(float f)
{
    CCRect rect = front->getTextureRect();

    float &value = isHorizontal ? rect.size.width : rect.size.height;
    value = isHorizontal ? originalSize.width * f : originalSize.height * f;
    
    front->setTextureRect(rect);
}

float GageBar::GetGageValue()
{
    CCSize size = front->getTextureRect().size;
    float f = isHorizontal ? size.width / originalSize.width : size.height / originalSize.height;
    
    return f;
}

cocos2d::CCSize GageBar::GetSize()
{
    return front->getContentSize();
}