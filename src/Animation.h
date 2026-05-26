#pragma once
#include <SDL2/SDL.h>
#include <vector>

class Animation {
private:
    std::vector<SDL_Texture*> frames;
    int currentFrame;
    Uint32 frameDuration;
    Uint32 lastUpdateTime;
    bool loop;
    bool finished;

public:
    Animation(Uint32 duration, bool loopAnim) {
        frameDuration = duration;
        loop = loopAnim;
        currentFrame = 0;
        lastUpdateTime = SDL_GetTicks();
        finished = false;
    }

    void addFrame(SDL_Texture* tex) {
        frames.push_back(tex);
    }

    void update() {
        if (frames.empty() || finished) return;

        Uint32 currentTime = SDL_GetTicks();
        if (currentTime - lastUpdateTime >= frameDuration) {
            currentFrame++;
            if (currentFrame >= frames.size()) {
                if (loop) {
                    currentFrame = 0;
                } else {
                    currentFrame = frames.size() - 1;
                    finished = true;
                }
            }
            lastUpdateTime = currentTime;
        }
    }

    SDL_Texture* getCurrentFrame() const {
        if (frames.empty()) return nullptr;
        return frames[currentFrame];
    }

    void reset() {
        currentFrame = 0;
        lastUpdateTime = SDL_GetTicks();
        finished = false;
    }

    bool isFinished() const { 
        return finished; 
    }
};
