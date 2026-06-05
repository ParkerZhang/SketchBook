// Exercise 5: GCD and LCM.
// Read a, b. Print gcd and lcm. Use long long; lcm = a / gcd * b to
// avoid overflow. Assume a, b fit in 64 bits and are non-zero.
#include <iostream>
using namespace std;
using int64 = long long;

int64 gcd(int64 a, int64 b) { while (b) { int64 t = a % b; a = b; b = t; } return a; }
int64 lcm(int64 a, int64 b) {
    // TODO: return a / gcd(a, b) * b (mind the order to avoid overflow).
    return 0;
}

int main() {
    int64 a, b; cin >> a >> b;
    cout << gcd(a, b) << ' ' << lcm(a, b) << "\n";
}
