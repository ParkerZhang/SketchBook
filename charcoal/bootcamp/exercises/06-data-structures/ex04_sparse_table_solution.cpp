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
        int K = (int)std::log2(n) + 1;
        st_.assign(n, std::vector<int>(K));
        // TODO: level 0 is a; level k is min of two halves of length 2^(k-1).
        for (int i = 0; i < n; ++i) st_[i][0] = a[i];
        for (int k = 1; k < K; ++k) {
            for (int i = 0; i + (1 << k) <= n; ++i) {
                st_[i][k] = std::min(st_[i][k-1], st_[i + (1 << (k-1))][k-1]);
            }
        }
    }
    int query(int l, int r) const {
        int k = (int)std::log2(r - l + 1);
        return std::min(st_[l][k], st_[r - (1 << k) + 1][k]);
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
