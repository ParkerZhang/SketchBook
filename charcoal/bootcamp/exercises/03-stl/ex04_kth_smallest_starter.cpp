// Exercise 4: K-th smallest using std::set and order_of_key semantics.
// Read n, then n integers, then q queries of the form "k".
// For each query, print the k-th smallest (1-indexed) value among the n.
// Use std::set and std::next (or an order-statistics tree replacement note).
#include <iostream>
#include <set>

int main() {
    int n;
    if (!(std::cin >> n)) return 0;
    std::set<int> s;
    for (int i = 0; i < n; ++i) {
        int x; std::cin >> x;
        s.insert(x);
    }

    int q;
    std::cin >> q;
    while (q--) {
        int k; std::cin >> k;
        // TODO: print the k-th smallest (1-indexed) value in s.
        // If k is out of range, print "NA".
    }
}
