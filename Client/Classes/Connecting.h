//
//  Connecting.h
//  Test
//
//  Created by 박 진 on 13. 11. 13..
//
//

#pragma once

#include "SingleTon.h"

class ConnectData : public SingleTon<ConnectData>
{
public:
    bool bot, admin;

    
public:
    ConnectData()
    {
        bot = admin = false;
    }
    
    ~ConnectData()
    {
        
    }
};