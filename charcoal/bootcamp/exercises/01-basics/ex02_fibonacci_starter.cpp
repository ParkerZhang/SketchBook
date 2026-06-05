// Exercise 2: Compute the n-th Fibonacci number using iteration.
// F(0) = 0, F(1) = 1, F(n) = F(n-1) + F(n-2).
// Use long long. Input: 0 <= n <= 90.
#include <iostream>

long long fibonacci(int n) {
    // TODO: implement iteratively (no recursion)
    return -1;
}

int main() {
    int n;
    if (!(std::cin >> n)) return 0;
    std::cout << fibonacci(n) << "\n";
}
