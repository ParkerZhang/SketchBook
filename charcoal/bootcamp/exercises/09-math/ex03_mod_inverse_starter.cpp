// Exercise 3: Modular inverse via Fermat's little theorem.
// Read a, m (m is a prime). Print a^{-1} mod m.
// Use mod_pow(a, m-2, m) from the previous exercise (re-implement it here).
#include <iostream>
using namespace std;
using int64 = long long;

int64 mod_pow(int64 a, int64 b, int64 m) {
    int64 res = 1 % m; a %= m;
    while (b > 0) {
        if (b & 1) res = (res * a) % m;
        a = (a * a) % m; b >>= 1;
    }
    return res;
}

int main() {
    int64 a, m;
    cin >> a >> m;
    // TODO: print a^(m-2) mod m.
}
