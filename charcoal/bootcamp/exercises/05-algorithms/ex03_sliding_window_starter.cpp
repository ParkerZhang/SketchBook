// Exercise 3: Sliding window (fixed size).
// Given an array of n integers and an integer k, print the maximum
// sum of any contiguous subarray of length k.
#include <iostream>
#include <vector>
#include <algorithm>

int main() {
    int n, k;
    if (!(std::cin >> n >> k)) return 0;
    std::vector<int> a(n);
    for (int i = 0; i < n; ++i) std::cin >> a[i];

    // TODO: O(n) sliding window. Compute sum of first k, then
    // slide i from k..n-1: add a[i], subtract a[i-k], track max.
    long long best = 0;
    std::cout << best << "\n";
}
