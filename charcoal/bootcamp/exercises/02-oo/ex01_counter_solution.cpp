#include <iostream>

class Counter {
public:
    Counter() : v_(0) { ++total_; }
    Counter(int v) : v_(v) { ++total_; }
    ~Counter() { --total_; }

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
    std::cout << a.value() << "\n";
    std::cout << b.value() << "\n";
    std::cout << Counter::total() << "\n";
}
