#include <iostream>
using namespace std;
using int64 = long long;

int64 gcd(int64 a, int64 b) { while (b) { int64 t = a % b; a = b; b = t; } return a; }
int64 lcm(int64 a, int64 b) { return a / gcd(a, b) * b; }

int main() {
    int64 a, b; cin >> a >> b;
    cout << gcd(a, b) << ' ' << lcm(a, b) << "\n";
}
