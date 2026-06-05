#include <iostream>
#include <vector>
#include <algorithm>

int main() {
    int n, k;
    if (!(std::cin >> n >> k)) return 0;
    std::vector<int> a(n);
    for (int i = 0; i < n; ++i) std::cin >> a[i];

    long long window = 0;
    for (int i = 0; i < k; ++i) window += a[i];
    long long best = window;
    for (int i = k; i < n; ++i) {
        window += a[i] - a[i - k];
        best = std::max(best, window);
    }
    std::cout << best << "\n";
}
