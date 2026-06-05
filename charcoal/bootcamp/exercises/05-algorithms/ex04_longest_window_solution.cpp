#include <iostream>
#include <vector>

int main() {
    int n;
    long long S;
    if (!(std::cin >> n >> S)) return 0;
    std::vector<int> a(n);
    for (int i = 0; i < n; ++i) std::cin >> a[i];

    int best = 0;
    long long cur = 0;
    int l = 0;
    for (int r = 0; r < n; ++r) {
        cur += a[r];
        while (cur > S && l <= r) {
            cur -= a[l];
            ++l;
        }
        best = std::max(best, r - l + 1);
    }
    std::cout << best << "\n";
}
