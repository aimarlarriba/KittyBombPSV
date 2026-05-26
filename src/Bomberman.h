#pragma once
#include "Core.h"

class Tablero;

class Bomberman : public Entity {
public:
    int speed;
    int hp;
    int tipoBomba;
    Bomberman(int startX, int startY);
    void move(int dx, int dy, const Tablero* tablero);
    virtual void habilidadEspecial() = 0;
};

class BomberManBlanco : public Bomberman {
public:
    BomberManBlanco(int startX, int startY);
    void habilidadEspecial() override;
};

class BomberManNegro : public Bomberman {
public:
    BomberManNegro(int startX, int startY);
    void habilidadEspecial() override;
};
