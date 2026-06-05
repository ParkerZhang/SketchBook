// Exercise 2: Fast exponentiation (a^b % m).
// Read a, b, m. Print a^b mod m in O(log b). Use long long.
#include <iostream>
using namespace std;
using int64 = long long;

int64 mod_pow(int64 a, int64 b, int64 m) {
    // TODO: square-and-multiply; watch out for overflow on a * a.
    return 0;
}

int main() {
    int64 a, b, m;
    cin >> a >> b >> m;
    cout << mod_pow(a, b, m) << "\n";
}
