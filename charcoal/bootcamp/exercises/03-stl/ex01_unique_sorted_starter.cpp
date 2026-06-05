// Exercise 1: Read n, then n integers.
// Print the unique sorted list of these integers in ascending order.
// Use std::set or std::sort + std::unique.
#include <iostream>
#include <vector>
#include <algorithm>

int main() {
    int n;
    if (!(std::cin >> n)) return 0;
    std::vector<int> a(n);
    for (int i = 0; i < n; ++i) std::cin >> a[i];

    // TODO: produce a sorted, deduplicated list and print it space-separated.
}
