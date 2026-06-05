// Exercise 3: Function template.
// Implement a generic `clamp<T>(v, lo, hi)` that returns v clamped to
// [lo, hi]. Test it on int and double.
#include <iostream>

template <typename T>
T clamp(T v, T lo, T hi) {
    // TODO
    return v;
}

int main() {
    std::cout << clamp(15, 0, 10) << "\n";          // 10
    std::cout << clamp(-2, 0, 10) << "\n";          // 0
    std::cout << clamp(3.5, 0.0, 1.0) << "\n";      // 1.0
}
