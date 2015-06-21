//
//  Utility.h
//  Test
//
//  Created by 박 진 on 13. 10. 7..
//
//

#pragma once

#define SAFE_DELETE(P)\
    if(P){ delete P; P = NULL; }