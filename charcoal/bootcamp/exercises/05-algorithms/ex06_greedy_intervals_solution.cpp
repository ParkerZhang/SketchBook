#include <iostream>
#include <vector>
#include <algorithm>

int main() {
    int n;
    if (!(std::cin >> n)) return 0;
    std::vector<std::pair<int,int>> a(n);
    for (int i = 0; i < n; ++i) std::cin >> a[i].first >> a[i].second;

    std::sort(a.begin(), a.end(),
              [](const auto& p, const auto& q){ return p.second < q.second; });

    int ans = 0;
    int last_end = -1;
    for (auto& [l, r] : a) {
        if (l >= last_end) {
            ++ans;
            last_end = r;
        }
    }
    std::cout << ans << "\n";
}
