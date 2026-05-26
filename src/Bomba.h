#pragma once
#include "Core.h"

class Tablero;

class Bomba : public Entity {
protected:
    int radio;
    int tiempoExplosion;
    bool explotada;
public:
    Bomba(int startX, int startY, int r, int t);
    virtual ~Bomba() = default;
    virtual void explotar(Tablero* tablero) = 0;
    bool estaExplotada() const { return explotada; }
};

class BSuper : public Bomba {
public:
    BSuper(int startX, int startY);
    void explotar(Tablero* tablero) override;
};

class BUltra : public Bomba {
public:
    BUltra(int startX, int startY);
    void explotar(Tablero* tablero) override;
};
