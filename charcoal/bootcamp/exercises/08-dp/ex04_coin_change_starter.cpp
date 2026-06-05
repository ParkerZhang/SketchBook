// Exercise 4: Coin change (unbounded, minimum number of coins).
// Read n (number of coin denominations) and T (target), then n coin
// values. Print the minimum number of coins summing to T, or -1.
// All values are positive and T <= 10000.
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
    // TODO: for each amount a in [1..T], dp[a] = min over c of dp[a-c]+1.
    std::cout << (dp[T] == INF ? -1 : dp[T]) << "\n";
}
