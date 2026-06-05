// Exercise 4: Sparse table for idempotent range queries (range minimum).
// Read n, then a static array a[0..n-1]. Then read q queries "l r".
// Print min(a[l..r]) (0-indexed, inclusive) using a sparse table.
// Build O(n log n), query O(1).
#include <iostream>
#include <vector>
#include <cmath>
#include <algorithm>

class SparseMin {
public:
    explicit SparseMin(const std::vector<int>& a) {
        int n = (int)a.size();
        int K = (n ? (int)std::log2(n) + 1 : 0);
        st_.assign(n, std::vector<int>(std::max(K, 1), 0));
        // TODO: build table where st_[i][k] = min over a[i..i+2^k-1].
    }
    int query(int l, int r) const {
        // TODO: O(1) idempotent query by combining two overlapping blocks.
        return 0;
    }
private:
    std::vector<std::vector<int>> st_;
};

int main() {
    int n;
    if (!(std::cin >> n)) return 0;
    std::vector<int> a(n);
    for (int i = 0; i < n; ++i) std::cin >> a[i];
    SparseMin st(a);
    int q; std::cin >> q;
    while (q--) {
        int l, r; std::cin >> l >> r;
        std::cout << st.query(l, r) << "\n";
    }
}
