#include <iostream>

long long gcd(long long a, long long b) {
    while (b != 0) {
        long long t = a % b;
        a = b;
        b = t;
    }
    return a;
}

int main() {
    long long a, b;
    if (!(std::cin >> a >> b)) return 0;
    std::cout << gcd(a, b) << "\n";
}
