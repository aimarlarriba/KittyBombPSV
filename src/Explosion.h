#pragma once
#include "Core.h"
#include <SDL2/SDL.h>

class Explosion : public Entity {
public:
    Uint32 endTime;
    
    Explosion(int startX, int startY, Uint32 duration) {
        x = startX;
        y = startY;
        endTime = SDL_GetTicks() + duration;
    }
    
    bool isDone() const { 
        return SDL_GetTicks() >= endTime; 
    }
};
