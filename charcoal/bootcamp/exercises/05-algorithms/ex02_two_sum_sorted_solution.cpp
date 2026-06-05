#include <iostream>
#include <vector>

int main() {
    int n;
    long long T;
    if (!(std::cin >> n >> T)) return 0;
    std::vector<long long> v(n);
    for (int i = 0; i < n; ++i) std::cin >> v[i];

    int i = 0, j = n - 1;
    while (i < j) {
        long long s = v[i] + v[j];
        if (s == T) { std::cout << i << ' ' << j << "\n"; return 0; }
        if (s < T) ++i; else --j;
    }
    std::cout << -1 << "\n";
}
