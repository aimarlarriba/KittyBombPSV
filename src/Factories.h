#pragma once
#include "Bloques.h"
#include "Bomberman.h"
#include "Bomba.h"

class BloqueFactory {
public:
    static Bloque* crearBloque(int tipo) {
        switch(tipo) {
            case 1: return new Vacio();
            case 2: return new Duro();
            case 3: return new Blando();
            case 4: return new Barrera();
            default: return new Vacio();
        }
    }
};

class BombermanFactory {
public:
    static Bomberman* crear(int tipo, int x, int y) {
        if(tipo == 1) return new BomberManBlanco(x, y);
        return new BomberManNegro(x, y);
    }
};

class BombaFactory {
public:
    static Bomba* crear(int tipo, int x, int y) {
        if(tipo == 1) return new BSuper(x, y);
        return new BUltra(x, y);
    }
};
