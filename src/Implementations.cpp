#include "Bomba.h"
#include "Bomberman.h"
#include "Mob.h"
#include "Tablero.h"
#include "Factories.h"
#include <SDL2/SDL.h>
#include <cstdlib>

Bomba::Bomba(int startX, int startY, int r, int t) {
    x = startX; y = startY; radio = r; 
    tiempoExplosion = SDL_GetTicks() + t; explotada = false;
}

BSuper::BSuper(int startX, int startY) : Bomba(startX, startY, 3, 3000) {}
void BSuper::explotar(Tablero* tablero) {
    if (!explotada && SDL_GetTicks() >= tiempoExplosion) {
        explotada = true;
        tablero->procesarExplosion(x / 64, y / 49, radio);
    }
}

BUltra::BUltra(int startX, int startY) : Bomba(startX, startY, 5, 2000) {}
void BUltra::explotar(Tablero* tablero) {
    if (!explotada && SDL_GetTicks() >= tiempoExplosion) {
        explotada = true;
        tablero->procesarExplosion(x / 64, y / 49, radio);
    }
}

Bomberman::Bomberman(int startX, int startY) {
    x = startX; y = startY; hp = 1; speed = 4;
}

void Bomberman::move(int dx, int dy, const Tablero* tablero) {
    if(!active) return;
    int nextX = x + (dx * speed);
    int nextY = y + (dy * speed);
    SDL_Rect rectP = {nextX, nextY, width, height};
    bool collision = false;
    for (int i = 0; i < tablero->getWidth(); i++) {
        for (int j = 0; j < tablero->getHeight(); j++) {
            Bloque* b = tablero->getCell(i, j);
            if (!b->esAtravesable()) {
                SDL_Rect rectCell = {i * 64, j * 49, 64, 49};
                SDL_Rect res;
                if (SDL_IntersectRect(&rectP, &rectCell, &res)) collision = true;
            }
        }
    }
    if (!collision) { x = nextX; y = nextY; }
}

BomberManBlanco::BomberManBlanco(int startX, int startY) : Bomberman(startX, startY) { speed = 5; tipoBomba = 1; }
void BomberManBlanco::habilidadEspecial() {}

BomberManNegro::BomberManNegro(int startX, int startY) : Bomberman(startX, startY) { speed = 4; tipoBomba = 2; }
void BomberManNegro::habilidadEspecial() {}

Mob::Mob(int startX, int startY, int health, int dmg, int spd) {
    x = startX; y = startY; hp = health; damage = dmg; speed = spd;
}

Enemigo::Enemigo(int startX, int startY) : Mob(startX, startY, 1, 1, 2) {}
void Enemigo::updateIA(Tablero* tablero) {
    if(!active) return;
    int dir = rand() % 4;
    int dx = 0, dy = 0;
    if(dir==0) dx=1; else if(dir==1) dx=-1; else if(dir==2) dy=1; else dy=-1;
    int nx = x + dx*speed; int ny = y + dy*speed;
    SDL_Rect r = {nx, ny, width, height};
    bool col = false;
    for(int i=0; i<tablero->getWidth(); i++) {
        for(int j=0; j<tablero->getHeight(); j++) {
            if(!tablero->getCell(i,j)->esAtravesable()) {
                SDL_Rect bc={i*64,j*49,64,49}; SDL_Rect res;
                if(SDL_IntersectRect(&r,&bc,&res)) col=true;
            }
        }
    }
    if(!col){ x=nx; y=ny; }
}

Boss::Boss(int startX, int startY) : Mob(startX, startY, 5, 2, 3) { width=64; height=64; }
void Boss::updateIA(Tablero* tablero) {
    if(!active) return;
    int dx = (rand()%3)-1; int dy = (rand()%3)-1;
    x += dx*speed; y += dy*speed;
}

Tablero::Tablero(int w, int h, int tipoMapa) : width(w), height(h) {
    grid.resize(w * h, nullptr);
    player1 = BombermanFactory::crear(1, 64, 49);
    player2 = BombermanFactory::crear(2, (w-2)*64, (h-2)*49);
    
    mobs.push_back(new Enemigo(256, 256));
    mobs.push_back(new Enemigo(600, 150));
    mobs.push_back(new Boss(400, 300));

    for(int i = 0; i < w; i++) {
        for(int j = 0; j < h; j++) {
            int t = 1;
            if(i == 0 || j == 0 || i == w-1 || j == h-1) t = 4;
            else if (i % 2 == 0 && j % 2 == 0) t = 2;
            else if (rand() % 100 < 30) t = 3;
            grid[j * w + i] = BloqueFactory::crearBloque(t);
        }
    }
    setCell(1,1,1); setCell(2,1,1); setCell(1,2,1);
    setCell(w-2,h-2,1); setCell(w-3,h-2,1); setCell(w-2,h-3,1);
}

Bloque* Tablero::getCell(int cx, int cy) const {
    if(cx < 0 || cy < 0 || cx >= width || cy >= height) return grid[0];
    return grid[cy * width + cx];
}

void Tablero::setCell(int cx, int cy, int tipo) {
    if(cx >= 0 && cy >= 0 && cx < width && cy < height) {
        delete grid[cy * width + cx];
        grid[cy * width + cx] = BloqueFactory::crearBloque(tipo);
    }
}

void Tablero::checkEntityCollisions(int ex, int ey, int w, int h) {
    SDL_Rect expl = {ex, ey, w, h};
    SDL_Rect res;
    SDL_Rect p1 = {player1->x, player1->y, player1->width, player1->height};
    if(SDL_IntersectRect(&expl, &p1, &res)) { player1->hp--; if(player1->hp<=0) player1->active=false; }
    
    SDL_Rect p2 = {player2->x, player2->y, player2->width, player2->height};
    if(SDL_IntersectRect(&expl, &p2, &res)) { player2->hp--; if(player2->hp<=0) player2->active=false; }
    
    for(auto m : mobs) {
        SDL_Rect mr = {m->x, m->y, m->width, m->height};
        if(SDL_IntersectRect(&expl, &mr, &res)) { m->hp--; if(m->hp<=0) m->active=false; }
    }
}

void Tablero::procesarExplosion(int cx, int cy, int radio) {
    int dx[] = {1, -1, 0, 0};
    int dy[] = {0, 0, 1, -1};
    
    setCell(cx, cy, 1);
    checkEntityCollisions(cx*64, cy*49, 64, 49);
    explosiones.push_back(new Explosion(cx*64, cy*49, 500));

    for (int dir = 0; dir < 4; dir++) {
        for (int r = 1; r <= radio; r++) {
            int nx = cx + (dx[dir] * r);
            int ny = cy + (dy[dir] * r);
            if (nx < 0 || ny < 0 || nx >= width || ny >= height) break;
            
            Bloque* b = getCell(nx, ny);
            if (!b->esDestructible() && !b->esAtravesable()) break;
            
            checkEntityCollisions(nx*64, ny*49, 64, 49);
            explosiones.push_back(new Explosion(nx*64, ny*49, 500));
            
            if (b->esDestructible()) { 
                setCell(nx, ny, 1); 
                break; 
            }
        }
    }
}

void Tablero::updateState() {
    for(auto m : mobs) m->updateIA(this);
    
    for(auto it = bombas.begin(); it != bombas.end();) {
        (*it)->explotar(this);
        if((*it)->estaExplotada()) { 
            delete *it; 
            it = bombas.erase(it); 
        } else { 
            ++it; 
        }
    }
    
    for(auto it = explosiones.begin(); it != explosiones.end();) {
        if((*it)->isDone()) {
            delete *it;
            it = explosiones.erase(it);
        } else {
            ++it;
        }
    }
    
    notifyObservers();
}

Tablero::~Tablero() {
    delete player1; delete player2;
    for(auto m : mobs) delete m;
    for(auto b : bombas) delete b;
    for(auto e : explosiones) delete e;
    for(auto g : grid) delete g;
}

void Tablero::addBomba(Bomba* b) { bombas.push_back(b); }
