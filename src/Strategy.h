#pragma once

class StrategyBloque {
public:
    virtual ~StrategyBloque() = default;
    virtual bool esDestructible() = 0;
    virtual bool esAtravesable() = 0;
};

class StBloquesBlandoClassic : public StrategyBloque {
public:
    bool esDestructible() override { return true; }
    bool esAtravesable() override { return false; }
};

class StBloquesDuroClassic : public StrategyBloque {
public:
    bool esDestructible() override { return false; }
    bool esAtravesable() override { return false; }
};

class StVacio : public StrategyBloque {
public:
    bool esDestructible() override { return false; }
    bool esAtravesable() override { return true; }
};
