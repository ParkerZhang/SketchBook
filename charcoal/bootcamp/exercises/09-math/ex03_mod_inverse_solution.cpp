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
    cout << mod_pow(a, m - 2, m) << "\n";
}
