// Exercise 4: Sliding window (variable size, longest).
// Given an array of positive integers, find the length of the longest
// subarray with sum <= S. Two-pointer / variable-size window in O(n).
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
    // TODO: for r in [0..n-1], expand window; while cur > S, shrink from l.
    //       update best = max(best, r - l + 1).

    std::cout << best << "\n";
}
