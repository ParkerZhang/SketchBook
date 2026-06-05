// Exercise 1: Prefix sums.
// Read n and an array a of n integers. Then read q queries of the form
// "l r" (0-indexed, inclusive). For each, print the sum a[l..r].
// Build a prefix-sum array in O(n) and answer each query in O(1).
#include <iostream>
#include <vector>

int main() {
    int n;
    if (!(std::cin >> n)) return 0;
    std::vector<long long> a(n);
    for (int i = 0; i < n; ++i) std::cin >> a[i];

    // TODO: build pref where pref[0] = 0 and pref[i+1] = pref[i] + a[i]
    std::vector<long long> pref(n + 1, 0);

    int q;
    std::cin >> q;
    while (q--) {
        int l, r;
        std::cin >> l >> r;
        // TODO: print pref[r+1] - pref[l]
    }
}
