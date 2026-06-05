// Exercise 1: Implement a Counter class.
// - default-constructed counter starts at 0
// - inc() / dec() change the value
// - value() returns the current value
// - a static total() returns how many Counter instances currently exist
//   (use a static member, increment in constructors, decrement in destructor)
#include <iostream>

class Counter {
public:
    Counter() { /* TODO */ }
    Counter(int v) : v_(v) { /* TODO */ }
    ~Counter() { /* TODO */ }

    void inc() { ++v_; }
    void dec() { --v_; }
    int  value() const { return v_; }

    static int total() { return total_; }

private:
    int v_;
    static int total_;
};

int Counter::total_ = 0;

int main() {
    Counter a;
    Counter b(10);
    a.inc();
    a.inc();
    std::cout << a.value() << "\n";        // expect 2
    std::cout << b.value() << "\n";        // expect 10
    std::cout << Counter::total() << "\n"; // expect 2
}
