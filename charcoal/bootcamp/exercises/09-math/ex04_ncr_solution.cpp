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

    vector<int64> fact(n + 1, 1);
    for (int i = 1; i <= n; ++i) fact[i] = fact[i-1] * i % p;

    int64 num = fact[n];
    int64 den = mod_pow(fact[r], p - 2, p) * mod_pow(fact[n - r], p - 2, p) % p;
    cout << num * den % p << "\n";
}
