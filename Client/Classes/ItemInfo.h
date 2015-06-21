//
//  ItemInfo.h
//  Test
//
//  Created by 박 진 on 13. 11. 27..
//
//


#pragma once

#include "SingleTon.h"

class ItemData : public SingleTon<ItemData>
{

public:
    int left;
    int right;
    bool update;    
    
public:
    ItemData()
    {
        left = 0;
        right = 0;
        update = false;
    }
    
    ~ItemData()
    {
        
    }
};