//
//  TopLeft.h
//  Test
//
//  Created by 박 진 on 13. 10. 7..
//
//

#pragma once

#include <cocos2d.h>
#include "Status.h"
#include "Utility.h"

class TopLeft : public cocos2d::CCNode
{
private:
    Status *status;
    
public:
    TopLeft();
    ~TopLeft();
    
public:
    virtual bool init();
    
    Status* GetStatus();
};