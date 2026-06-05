// Exercise 6: Greedy - activity selection / max non-overlapping intervals.
// Read n intervals [l, r] and print the maximum number of them that can
// be chosen so no two overlap. Greedy: sort by end, pick earliest-finishing.
#include <iostream>
#include <vector>
#include <algorithm>

int main() {
    int n;
    if (!(std::cin >> n)) return 0;
    std::vector<std::pair<int,int>> a(n);
    for (int i = 0; i < n; ++i) std::cin >> a[i].first >> a[i].second;

    // TODO: sort by second, then count greedy.
    int ans = 0;
    std::cout << ans << "\n";
}
