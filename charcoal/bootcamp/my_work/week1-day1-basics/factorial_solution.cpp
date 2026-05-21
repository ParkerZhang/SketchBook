// Reference solution — review after attempting
#include <iostream>

long long factorial_iter(int n) {
    long long r = 1;
    for (int i = 2; i <= n; ++i) r *= i;
    return r;
}

long long factorial_recur(int n) {
    if (n <= 1) return 1;
    return n * factorial_recur(n - 1);
}

int main() {
    int n;
    std::cin >> n;
    std::cout << factorial_iter(n) << " "
              << factorial_recur(n) << "\n";
}
