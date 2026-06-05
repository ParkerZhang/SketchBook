// Exercise 5: Binary search on the answer.
// There are n packages. The i-th package has weight w[i]. A truck can
// carry at most C per day. Find the minimum number of days D such that
// all packages can be moved in D days (consecutive packages per day).
// This is a classic "ship packages" / "min-max" problem.
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
    // TODO: binary search on capacity. Predicate: days_for(cap) <= D.
    // Find the smallest cap that lets us ship in any number of days.
    // (Here we just want the minimum cap; use lo..hi.)
    std::cout << ans << "\n";
}
