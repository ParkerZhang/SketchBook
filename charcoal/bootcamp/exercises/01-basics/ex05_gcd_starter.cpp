// Exercise 5: Compute the greatest common divisor of a and b using
// the Euclidean algorithm. Both inputs are non-negative and not both zero.
#include <iostream>

long long gcd(long long a, long long b) {
    // TODO: implement Euclidean algorithm
    return 0;
}

int main() {
    long long a, b;
    if (!(std::cin >> a >> b)) return 0;
    std::cout << gcd(a, b) << "\n";
}
