//
//  GlobalData.h
//  Test
//
//  Created by 박 진 on 13. 11. 4..
//
//

#pragma once

#include "Utility.h"
#include <cocos2d.h>

template< class T >
class SingleTon
{
    private:
    static T* instance;
    
    public:
    static T* GetInstance()
    {
        if(instance == NULL)
            instance = new T();
        
        return instance;
    }
    
    static void DeleteSingleTon()
    {
        SAFE_DELETE(instance);
    }
};

template<class T>
T* SingleTon<T>::instance = NULL;