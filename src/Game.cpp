#include "Game.h"
#include "Factories.h"
#include <SDL2/SDL_image.h>

Game::Game() : window(nullptr), renderer(nullptr), isRunning(false), tablero(nullptr) {}

Game::~Game() {
    for(auto& pair : textures) SDL_DestroyTexture(pair.second);
    if(tablero) delete tablero;
    SDL_DestroyRenderer(renderer);
    SDL_DestroyWindow(window);
    SDL_Quit();
    
    delete animP1Up;
    delete animP1Down;
    delete animP1Left;
    delete animP1Right;
    delete animP1Death;
    delete animP2Up;
    delete animP2Down;
    delete animP2Left;
    delete animP2Right;
    delete animP2Death;
    delete animBlast;
}

SDL_Texture* Game::loadTexture(const char* path) {
    SDL_Surface* surface = IMG_Load(path);
    if (!surface) return nullptr;
    SDL_Texture* tex = SDL_CreateTextureFromSurface(renderer, surface);
    SDL_FreeSurface(surface);
    return tex;
}

bool Game::init() {
    if (SDL_Init(SDL_INIT_VIDEO | SDL_INIT_JOYSTICK) < 0) return false;
    window = SDL_CreateWindow("Bomberman", SDL_WINDOWPOS_CENTERED, SDL_WINDOWPOS_CENTERED, 960, 544, 0);
    renderer = SDL_CreateRenderer(window, -1, SDL_RENDERER_ACCELERATED | SDL_RENDERER_PRESENTVSYNC);
    SDL_JoystickOpen(0);

    textures["enemy"] = loadTexture("app0:/assets/baloon1.png");
    textures["boss"] = loadTexture("app0:/assets/boss.png");
    textures["bomb"] = loadTexture("app0:/assets/bomb1.png");
    textures["vacio"] = loadTexture("app0:/assets/1.png");
    textures["duro"] = loadTexture("app0:/assets/2.png");
    textures["blando"] = loadTexture("app0:/assets/3.png");
    textures["barrera"] = loadTexture("app0:/assets/4.png");
    textures["title"] = loadTexture("app0:/assets/title.png");
    textures["sel"] = loadTexture("app0:/assets/seleccion.png");

    animP1Up = new Animation(100, true);
    animP1Up->addFrame(loadTexture("app0:/assets/whiteup1.png"));
    animP1Up->addFrame(loadTexture("app0:/assets/whiteup2.png"));
    animP1Up->addFrame(loadTexture("app0:/assets/whiteup3.png"));
    animP1Up->addFrame(loadTexture("app0:/assets/whiteup4.png"));
    animP1Up->addFrame(loadTexture("app0:/assets/whiteup5.png"));

    animP1Down = new Animation(100, true);
    animP1Down->addFrame(loadTexture("app0:/assets/whitedown1.png"));
    animP1Down->addFrame(loadTexture("app0:/assets/whitedown2.png"));
    animP1Down->addFrame(loadTexture("app0:/assets/whitedown3.png"));
    animP1Down->addFrame(loadTexture("app0:/assets/whitedown4.png"));
    animP1Down->addFrame(loadTexture("app0:/assets/whitedown5.png"));

    animP1Left = new Animation(100, true);
    animP1Left->addFrame(loadTexture("app0:/assets/whiteleft1.png"));
    animP1Left->addFrame(loadTexture("app0:/assets/whiteleft2.png"));
    animP1Left->addFrame(loadTexture("app0:/assets/whiteleft3.png"));
    animP1Left->addFrame(loadTexture("app0:/assets/whiteleft4.png"));
    animP1Left->addFrame(loadTexture("app0:/assets/whiteleft5.png"));

    animP1Right = new Animation(100, true);
    animP1Right->addFrame(loadTexture("app0:/assets/whiteright1.png"));
    animP1Right->addFrame(loadTexture("app0:/assets/whiteright2.png"));
    animP1Right->addFrame(loadTexture("app0:/assets/whiteright3.png"));
    animP1Right->addFrame(loadTexture("app0:/assets/whiteright4.png"));
    animP1Right->addFrame(loadTexture("app0:/assets/whiteright5.png"));

    animP1Death = new Animation(150, false);
    animP1Death->addFrame(loadTexture("app0:/assets/whiteOnFire1.png"));
    animP1Death->addFrame(loadTexture("app0:/assets/whiteOnFire2.png"));

    animP2Up = new Animation(100, true);
    animP2Up->addFrame(loadTexture("app0:/assets/blackup1.png"));
    animP2Up->addFrame(loadTexture("app0:/assets/blackup2.png"));
    animP2Up->addFrame(loadTexture("app0:/assets/blackup3.png"));
    animP2Up->addFrame(loadTexture("app0:/assets/blackup4.png"));
    animP2Up->addFrame(loadTexture("app0:/assets/blackup5.png"));

    animP2Down = new Animation(100, true);
    animP2Down->addFrame(loadTexture("app0:/assets/blackdown1.png"));
    animP2Down->addFrame(loadTexture("app0:/assets/blackdown2.png"));
    animP2Down->addFrame(loadTexture("app0:/assets/blackdown3.png"));
    animP2Down->addFrame(loadTexture("app0:/assets/blackdown4.png"));
    animP2Down->addFrame(loadTexture("app0:/assets/blackdown5.png"));

    animP2Left = new Animation(100, true);
    animP2Left->addFrame(loadTexture("app0:/assets/blackleft1.png"));
    animP2Left->addFrame(loadTexture("app0:/assets/blackleft2.png"));
    animP2Left->addFrame(loadTexture("app0:/assets/blackleft3.png"));
    animP2Left->addFrame(loadTexture("app0:/assets/blackleft4.png"));
    animP2Left->addFrame(loadTexture("app0:/assets/blackleft5.png"));

    animP2Right = new Animation(100, true);
    animP2Right->addFrame(loadTexture("app0:/assets/blackright1.png"));
    animP2Right->addFrame(loadTexture("app0:/assets/blackright2.png"));
    animP2Right->addFrame(loadTexture("app0:/assets/blackright3.png"));
    animP2Right->addFrame(loadTexture("app0:/assets/blackright4.png"));
    animP2Right->addFrame(loadTexture("app0:/assets/blackright5.png"));

    animP2Death = new Animation(150, false);
    animP2Death->addFrame(loadTexture("app0:/assets/blackOnFire1.png"));
    animP2Death->addFrame(loadTexture("app0:/assets/blackOnFire2.png"));
    
    // Ejemplo para cargar los 6 frames que has creado para cada animacion de blast
    // Asumiendo que ahora tienes: miniBlast1_1.png, miniBlast1_2.png, ..., miniBlast1_6.png
    animBlast = new Animation(100, true);
    for (int i = 1; i <= 6; ++i) {
      std::string path = "app0:/assets/miniBlast1_" + std::to_string(i) + ".png";
      animBlast->addFrame(loadTexture(path.c_str()));
    }

    currentP1Anim = animP1Down;
    p1State = PlayerState::IDLE;
    
    currentP2Anim = animP2Down;
    p2State = PlayerState::IDLE;

    isRunning = true;
    return true;
}

void Game::handleEvents() {
    SDL_Event event;
    while (SDL_PollEvent(&event)) {
        if (event.type == SDL_QUIT) isRunning = false;
        if (event.type == SDL_JOYBUTTONDOWN) {
            if (ui.currentState == GameState::PANTALLA_INICIO) {
                ui.currentState = GameState::SELECCION_TABLERO;
            } else if (ui.currentState == GameState::SELECCION_TABLERO) {
                tablero = new Tablero(15, 11, ui.mapSelection);
                tablero->addObserver(this);
                ui.currentState = GameState::JUGANDO;
            } else if (ui.currentState == GameState::JUGANDO) {
                if (event.jbutton.button == 8) isRunning = false;
                if (event.jbutton.button == 2 && tablero->getPlayer1()->active) {
                    tablero->addBomba(BombaFactory::crear(tablero->getPlayer1()->tipoBomba, tablero->getPlayer1()->x, tablero->getPlayer1()->y));
                }
                if (event.jbutton.button == 3 && tablero->getPlayer2()->active) {
                    tablero->addBomba(BombaFactory::crear(tablero->getPlayer2()->tipoBomba, tablero->getPlayer2()->x, tablero->getPlayer2()->y));
                }
            }
        }
    }

    if (ui.currentState == GameState::JUGANDO) {
        int lx = SDL_JoystickGetAxis(SDL_JoystickOpen(0), 0);
        int ly = SDL_JoystickGetAxis(SDL_JoystickOpen(0), 1);
        int rx = SDL_JoystickGetAxis(SDL_JoystickOpen(0), 2);
        int ry = SDL_JoystickGetAxis(SDL_JoystickOpen(0), 3);
        
        int dx1 = 0, dy1 = 0, dx2 = 0, dy2 = 0;
        if (lx < -10000) dx1 = -1; if (lx > 10000) dx1 = 1;
        if (ly < -10000) dy1 = -1; if (ly > 10000) dy1 = 1;
        if (rx < -10000) dx2 = -1; if (rx > 10000) dx2 = 1;
        if (ry < -10000) dy2 = -1; if (ry > 10000) dy2 = 1;

        if (dx1 != 0 || dy1 != 0) tablero->getPlayer1()->move(dx1, dy1, tablero);
        if (dx2 != 0 || dy2 != 0) tablero->getPlayer2()->move(dx2, dy2, tablero);
        tablero->updateState();

        bool p1Moved = false;
        if (dy1 == -1) {
            p1State = PlayerState::UP;
            currentP1Anim = animP1Up;
            p1Moved = true;
        } else if (dy1 == 1) {
            p1State = PlayerState::DOWN;
            currentP1Anim = animP1Down;
            p1Moved = true;
        } else if (dx1 == -1) {
            p1State = PlayerState::LEFT;
            currentP1Anim = animP1Left;
            p1Moved = true;
        } else if (dx1 == 1) {
            p1State = PlayerState::RIGHT;
            currentP1Anim = animP1Right;
            p1Moved = true;
        }

        if (!p1Moved) {
            p1State = PlayerState::IDLE;
            currentP1Anim->reset();
        }

        bool p2Moved = false;
        if (dy2 == -1) {
            p2State = PlayerState::UP;
            currentP2Anim = animP2Up;
            p2Moved = true;
        } else if (dy2 == 1) {
            p2State = PlayerState::DOWN;
            currentP2Anim = animP2Down;
            p2Moved = true;
        } else if (dx2 == -1) {
            p2State = PlayerState::LEFT;
            currentP2Anim = animP2Left;
            p2Moved = true;
        } else if (dx2 == 1) {
            p2State = PlayerState::RIGHT;
            currentP2Anim = animP2Right;
            p2Moved = true;
        }

        if (!p2Moved) {
            p2State = PlayerState::IDLE;
            currentP2Anim->reset();
        }
    }
}

void Game::update() { render(); }

void Game::renderInicio() {
    SDL_RenderClear(renderer);
    SDL_RenderCopy(renderer, textures["title"], NULL, NULL);
    SDL_RenderPresent(renderer);
}

void Game::renderSeleccion() {
    SDL_RenderClear(renderer);
    SDL_RenderCopy(renderer, textures["sel"], NULL, NULL);
    SDL_RenderPresent(renderer);
}

void Game::renderJuego() {
    SDL_RenderClear(renderer);
    for(int i = 0; i < tablero->getWidth(); i++) {
        for(int j = 0; j < tablero->getHeight(); j++) {
            SDL_Rect dest = {i * 64, j * 49, 64, 49};
            int t = tablero->getCell(i, j)->getTipo();
            if (t==1) SDL_RenderCopy(renderer, textures["vacio"], NULL, &dest);
            if (t==2) SDL_RenderCopy(renderer, textures["duro"], NULL, &dest);
            if (t==3) SDL_RenderCopy(renderer, textures["blando"], NULL, &dest);
            if (t==4) SDL_RenderCopy(renderer, textures["barrera"], NULL, &dest);
        }
    }
    
    for(auto b : tablero->getBombas()) {
        SDL_Rect bDest = {b->x, b->y, 48, 48};
        SDL_RenderCopy(renderer, textures["bomb"], NULL, &bDest);
    }
    
    for(auto m : tablero->getMobs()) {
        if(m->active) {
            SDL_Rect mDest = {m->x, m->y, m->width, m->height};
            SDL_Texture* tex = (m->hp > 1) ? textures["boss"] : textures["enemy"];
            SDL_RenderCopy(renderer, tex, NULL, &mDest);
        }
    }

    if(tablero->getPlayer1()->active) {
        if (p1State != PlayerState::IDLE) {
            currentP1Anim->update();
        }
    } else {
        currentP1Anim = animP1Death;
        currentP1Anim->update();
    }
    SDL_Rect p1Dest = {tablero->getPlayer1()->x, tablero->getPlayer1()->y, 48, 64};
    SDL_RenderCopy(renderer, currentP1Anim->getCurrentFrame(), NULL, &p1Dest);

    if(tablero->getPlayer2()->active) {
        if (p2State != PlayerState::IDLE) {
            currentP2Anim->update();
        }
    } else {
        currentP2Anim = animP2Death;
        currentP2Anim->update();
    }
    SDL_Rect p2Dest = {tablero->getPlayer2()->x, tablero->getPlayer2()->y, 48, 64};
    SDL_RenderCopy(renderer, currentP2Anim->getCurrentFrame(), NULL, &p2Dest);
    
    if (!tablero->getExplosiones().empty()) {
        animBlast->update();
        for(auto e : tablero->getExplosiones()) {
            SDL_Rect eDest = {e->x, e->y, 64, 49};
            SDL_RenderCopy(renderer, animBlast->getCurrentFrame(), NULL, &eDest);
        }
    }
    
    SDL_RenderPresent(renderer);
}

void Game::render() {
    if(ui.currentState == GameState::PANTALLA_INICIO) renderInicio();
    else if(ui.currentState == GameState::SELECCION_TABLERO) renderSeleccion();
    else renderJuego();
}
