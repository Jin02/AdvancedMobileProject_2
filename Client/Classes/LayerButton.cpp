//
//  LayerButton.cpp
//  Test
//
//  Created by 박 진 on 13. 10. 10..
//
//

#include "LayerButton.h"

using namespace cocos2d;
using namespace std;

LayerButton::LayerButton()
{
    button = NULL;
    menu = NULL;

    buttonFrame = NULL;
    
    itemId = 0;
    
    item = NULL;
}

LayerButton::~LayerButton()
{
    removeAllChildren();
    button->removeAllChildren();
    
    SAFE_DELETE(button);
    SAFE_DELETE(buttonFrame);
    SAFE_DELETE(menu);
}

bool LayerButton::init(cocos2d::CCObject *target, cocos2d::SEL_MenuHandler handler, const char* backgroundImg, const char* frameImg)
{
    if( CCNode::init() == false )
        return false;
    
    button = new CCMenuItemImage;
    menu = new CCMenu;
    buttonFrame = new CCSprite;
    
    button->initWithNormalImage(backgroundImg, backgroundImg, NULL, target, handler);
    
    CCArray *ary = CCArray::createWithObject(button);
    menu->initWithArray(ary);
        
    menu->setPosition(ccp(0,0));
    menu->setAnchorPoint(ccp(0.5, 0.5));
    addChild(menu);
    
    buttonFrame->initWithFile(frameImg);
    buttonFrame->setAnchorPoint(CCPointZero);
    button->addChild(buttonFrame, 2);
    
    return true;
}

void LayerButton::SetFramePosition(CCPoint pos)
{
    buttonFrame->setPosition(pos);
}

void LayerButton::AddItem(int itemId)
{
    string name;

    if(itemId == 1)
        name = "item1.png";
    else if(itemId ==2)
        name = "item2.png";
    else return;

    if(item)
        return;
    
    this->itemId = itemId;
    
    item = CCSprite::create(name.c_str());
    item->setPosition(ccp(5, 5));
    item->setAnchorPoint(ccp(0.0, 0.0));
    button->addChild(item, 1);
}

void LayerButton::AddSprite(const char *textureName, CCPoint pos)
{
    item = CCSprite::create(textureName);
    item->setPosition(pos);
    item->setAnchorPoint(ccp(0.0, 0.0));
    item->setScale(1.1f);
    button->addChild(item, 1);
}

void LayerButton::UseItem()
{
    itemId = 0;
    button->removeChild(item, true);
    item = NULL;
}

void LayerButton::SetFrameColor(GLubyte r, GLubyte g, GLubyte b)
{
    buttonFrame->setColor(ccc3(r, g, b));
}