#include <iostream>
#include <vector>

using namespace std;

typedef long long ll;

/**
 * Fast Exponentiation: (a^b) % m
 * O(log b)
 */
ll power(ll a, ll b, ll m) {
    ll res = 1;
    a %= m;
    while (b > 0) {
        if (b & 1) res = (res * a) % m;
        a = (a * a) % m;
        b >>= 1;
    }
    return res;
}

/**
 * GCD using Euclidean Algorithm
 */
ll gcd(ll a, ll b) {
    return b == 0 ? a : gcd(b, a % b);
}

int main() {
    cout << "2^10 % 1000 = " << power(2, 10, 1000) << "\n";
    cout << "GCD of 24 and 36: " << gcd(24, 36) << "\n";
    return 0;
}
