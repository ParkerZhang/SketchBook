#include <iostream>
#include <vector>

using namespace std;

/**
 * Competitive Programming Basic Template
 * - Fast I/O
 * - Common typedefs
 */

typedef long long ll;

// Recursive function example
ll factorial(int n) {
    if (n <= 1) return 1;
    return n * factorial(n - 1);
}

// Pass by reference example
void swap_nums(int &a, int &b) {
    int temp = a;
    a = b;
    b = temp;
}

int main() {
    // Fast I/O: Synchronizing with stdio and untying cin from cout
    // avoids the overhead of buffer flushing and C-style sync.
    ios::sync_with_stdio(0);
    cin.tie(0);

    int x = 10, y = 20;
    swap_nums(x, y);
    
    cout << "Swapped: x=" << x << ", y=" << y << "\n";
    cout << "Factorial of 10: " << factorial(10) << "\n";

    return 0;
}
