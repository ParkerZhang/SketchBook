#include <iostream>

long long fibonacci(int n) {
    if (n == 0) return 0;
    if (n == 1) return 1;
    long long a = 0, b = 1;
    for (int i = 2; i <= n; ++i) {
        long long c = a + b;
        a = b;
        b = c;
    }
    return b;
}

int main() {
    int n;
    if (!(std::cin >> n)) return 0;
    std::cout << fibonacci(n) << "\n";
}
