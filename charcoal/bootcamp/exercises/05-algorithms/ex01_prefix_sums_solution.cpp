#include <iostream>
#include <vector>

int main() {
    int n;
    if (!(std::cin >> n)) return 0;
    std::vector<long long> a(n);
    for (int i = 0; i < n; ++i) std::cin >> a[i];

    std::vector<long long> pref(n + 1, 0);
    for (int i = 0; i < n; ++i) pref[i + 1] = pref[i] + a[i];

    int q;
    std::cin >> q;
    while (q--) {
        int l, r;
        std::cin >> l >> r;
        std::cout << pref[r + 1] - pref[l] << "\n";
    }
}
