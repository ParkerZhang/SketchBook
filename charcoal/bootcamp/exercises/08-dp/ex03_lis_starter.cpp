// Exercise 3: Longest Increasing Subsequence (O(N log N) patience sort).
// Read n, then n integers. Print the length of the LIS.
#include <iostream>
#include <vector>
#include <algorithm>

int main() {
    int n;
    if (!(std::cin >> n)) return 0;
    std::vector<int> a(n);
    for (int i = 0; i < n; ++i) std::cin >> a[i];

    std::vector<int> tail;
    for (int x : a) {
        // TODO: lower_bound on tail for x, then either append or replace.
        // tail size at the end = LIS length.
    }
    std::cout << (int)tail.size() << "\n";
}
