#pragma once

enum class GameState {
    PANTALLA_INICIO,
    SELECCION_TABLERO,
    JUGANDO
};

class PantallaManager {
public:
    GameState currentState;
    int mapSelection;
    
    PantallaManager() {
        currentState = GameState::PANTALLA_INICIO;
        mapSelection = 1;
    }
};
