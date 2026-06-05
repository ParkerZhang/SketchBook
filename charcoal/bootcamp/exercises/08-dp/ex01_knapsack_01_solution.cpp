#include <iostream>
#include <vector>
#include <algorithm>

int main() {
    int n, W;
    if (!(std::cin >> n >> W)) return 0;
    std::vector<int> w(n), v(n);
    for (int i = 0; i < n; ++i) std::cin >> w[i] >> v[i];

    std::vector<int> dp(W + 1, 0);
    for (int i = 0; i < n; ++i) {
        for (int cap = W; cap >= w[i]; --cap) {
            dp[cap] = std::max(dp[cap], dp[cap - w[i]] + v[i]);
        }
    }
    std::cout << dp[W] << "\n";
}
