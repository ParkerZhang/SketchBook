#include <iostream>
#include <vector>
#include <algorithm>

int main() {
    int n, T;
    if (!(std::cin >> n >> T)) return 0;
    std::vector<int> c(n);
    for (int i = 0; i < n; ++i) std::cin >> c[i];

    constexpr int INF = 1e9;
    std::vector<int> dp(T + 1, INF);
    dp[0] = 0;
    for (int a = 1; a <= T; ++a) {
        for (int x : c) {
            if (x <= a && dp[a - x] + 1 < dp[a]) {
                dp[a] = dp[a - x] + 1;
            }
        }
    }
    std::cout << (dp[T] == INF ? -1 : dp[T]) << "\n";
}
