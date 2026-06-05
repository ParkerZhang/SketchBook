#include <iostream>
#include <algorithm>

template <typename T>
T clamp(T v, T lo, T hi) {
    return std::max(lo, std::min(v, hi));
}

int main() {
    std::cout << clamp(15, 0, 10) << "\n";
    std::cout << clamp(-2, 0, 10) << "\n";
    std::cout << clamp(3.5, 0.0, 1.0) << "\n";
}
