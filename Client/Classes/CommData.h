//
//  CommData.h
//  Test
//
//  Created by 박 진 on 13. 11. 4..
//
//

#pragma once

#include "SingleTon.h"

class CommData : public SingleTon<CommData>
{
    public:
    
    int speed;
    int boost;
    
    bool update;
    bool start;
    
    int botId;
    
    int arrive;
    int time;
   
    public:
    CommData()
    {
        speed = boost = 0;
        update = false;
        start = false;
        
        botId = 0;
        time = 0;
        arrive = 0;
    }
    
    ~CommData()
    {
        
    }
};