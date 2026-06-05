#include <iostream>

long long factorial(int n) {
    if (n <= 1) return 1;
    long long r = 1;
    for (int i = 2; i <= n; ++i) r *= i;
    return r;
}

int main() {
    int n;
    if (!(std::cin >> n)) return 0;
    std::cout << factorial(n) << "\n";
}
