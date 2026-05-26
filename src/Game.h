#pragma once
#include <SDL2/SDL.h>
#include "Core.h"
#include "Tablero.h"
#include "Pantallas.h"
#include <map>
#include <string>
#include "Animation.h"

class Game : public IObserver {
private:
    SDL_Window* window;
    SDL_Renderer* renderer;
    std::map<std::string, SDL_Texture*> textures;
    Tablero* tablero;
    PantallaManager ui;
    bool isRunning;

    SDL_Texture* loadTexture(const char* path);
    void renderInicio();
    void renderSeleccion();
    void renderJuego();
    
    Animation* animP1Up;
    Animation* animP1Down;
    Animation* animP1Left;
    Animation* animP1Right;
    Animation* currentP1Anim;
    Animation* animP1Death;
    Animation* animP2Up;
    Animation* animP2Down;
    Animation* animP2Left;
    Animation* animP2Right;
    Animation* currentP2Anim;
    Animation* animP2Death;
    Animation* animBlast;
    
    enum class PlayerState { IDLE, UP, DOWN, LEFT, RIGHT, DEAD };
    PlayerState p1State;
    PlayerState p2State;

public:
    Game();
    ~Game();
    bool init();
    void handleEvents();
    void render();
    void update() override;
    bool running() const { return isRunning; }
};
