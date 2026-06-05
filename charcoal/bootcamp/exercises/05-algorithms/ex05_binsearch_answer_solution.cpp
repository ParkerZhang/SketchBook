#include <iostream>
#include <vector>
#include <numeric>
#include <algorithm>

int days_for(const std::vector<int>& w, int cap) {
    int d = 1, cur = 0;
    for (int x : w) {
        if (cur + x <= cap) cur += x;
        else { ++d; cur = x; }
    }
    return d;
}

int main() {
    int n, C;
    if (!(std::cin >> n >> C)) return 0;
    std::vector<int> w(n);
    for (int i = 0; i < n; ++i) std::cin >> w[i];

    int lo = *std::max_element(w.begin(), w.end());
    int hi = std::accumulate(w.begin(), w.end(), 0);

    int ans = hi;
    while (lo <= hi) {
        int mid = lo + (hi - lo) / 2;
        if (days_for(w, mid) <= C) {
            ans = mid;
            hi = mid - 1;
        } else {
            lo = mid + 1;
        }
    }
    std::cout << ans << "\n";
}
