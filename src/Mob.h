#pragma once
#include "Core.h"

class Tablero;

class Mob : public Entity {
public:
    int hp;
    int damage;
    int speed;
    Mob(int startX, int startY, int health, int dmg, int spd);
    virtual ~Mob() = default;
    virtual void updateIA(Tablero* tablero) = 0;
};

class Enemigo : public Mob {
public:
    Enemigo(int startX, int startY);
    void updateIA(Tablero* tablero) override;
};

class Boss : public Mob {
public:
    Boss(int startX, int startY);
    void updateIA(Tablero* tablero) override;
};
