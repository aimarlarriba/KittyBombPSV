#pragma once
#include "Strategy.h"

class Bloque {
protected:
    StrategyBloque* strategy;
public:
    Bloque(StrategyBloque* st) : strategy(st) {}
    virtual ~Bloque() { delete strategy; }
    bool esDestructible() { return strategy->esDestructible(); }
    bool esAtravesable() { return strategy->esAtravesable(); }
    virtual int getTipo() = 0;
};

class Blando : public Bloque {
public:
    Blando() : Bloque(new StBloquesBlandoClassic()) {}
    int getTipo() override { return 3; }
};

class Duro : public Bloque {
public:
    Duro() : Bloque(new StBloquesDuroClassic()) {}
    int getTipo() override { return 2; }
};

class Vacio : public Bloque {
public:
    Vacio() : Bloque(new StVacio()) {}
    int getTipo() override { return 1; }
};

class Barrera : public Bloque {
public:
    Barrera() : Bloque(new StBloquesDuroClassic()) {}
    int getTipo() override { return 4; }
};
