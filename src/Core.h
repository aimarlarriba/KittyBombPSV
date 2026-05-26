#pragma once
#include <vector>

class IObserver {
public:
    virtual void update() = 0;
    virtual ~IObserver() = default;
};

class IObservable {
protected:
    std::vector<IObserver*> observers;
public:
    void addObserver(IObserver* observer) { observers.push_back(observer); }
    void notifyObservers() { for (auto* obs : observers) obs->update(); }
    virtual ~IObservable() = default;
};

class Entity {
public:
    int x = 0;
    int y = 0;
    int width = 48;
    int height = 48;
    bool active = true;
    virtual ~Entity() = default;
};
