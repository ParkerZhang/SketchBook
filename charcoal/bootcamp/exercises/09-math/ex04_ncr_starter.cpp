// Exercise 4: nCr mod p.
// Read n, r, p (p prime). Print C(n, r) mod p using factorials +
// modular inverse. 0 <= r <= n <= 10^6, p prime ~ 10^9.
#include <iostream>
#include <vector>
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
    int n, r;
    int64 p;
    cin >> n >> r >> p;

    // TODO: precompute fact[0..n] mod p, then print
    //       fact[n] * mod_pow(fact[r], p-2, p) % p * mod_pow(fact[n-r], p-2, p) % p.
}
