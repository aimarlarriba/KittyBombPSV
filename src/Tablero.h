#pragma once
#include "Core.h"
#include "Bloques.h"
#include "Bomberman.h"
#include "Mob.h"
#include "Bomba.h"
#include "Explosion.h"
#include <vector>

class Tablero : public IObservable {
private:
    int width;
    int height;
    std::vector<Bloque*> grid;
    Bomberman* player1;
    Bomberman* player2;
    std::vector<Mob*> mobs;
    std::vector<Bomba*> bombas;
    std::vector<Explosion*> explosiones;

public:
    Tablero(int w, int h, int tipoMapa);
    ~Tablero();
    
    void updateState();
    Bloque* getCell(int cx, int cy) const;
    void setCell(int cx, int cy, int tipo);
    int getWidth() const { return width; }
    int getHeight() const { return height; }
    
    void procesarExplosion(int cx, int cy, int radio);
    void checkEntityCollisions(int ex, int ey, int w, int h);
    
    Bomberman* getPlayer1() const { return player1; }
    Bomberman* getPlayer2() const { return player2; }
    const std::vector<Mob*>& getMobs() const { return mobs; }
    const std::vector<Bomba*>& getBombas() const { return bombas; }
    const std::vector<Explosion*>& getExplosiones() const { return explosiones; }
    
    void addBomba(Bomba* b);
};
