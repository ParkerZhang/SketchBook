// Exercise 3: Segment tree for range sum / point update.
// Read n, then n integers. Then read q queries:
//   "u idx val"  - point update: a[idx] = val
//   "q l r"      - print sum of a[l..r] (0-indexed, inclusive)
// Implement the segment tree recursively.
#include <iostream>
#include <vector>

class SegTree {
public:
    explicit SegTree(int n) : n_(n) {}
    void build(const std::vector<int>& a, int v, int tl, int tr);
    void point_update(int idx, int val, int v, int tl, int tr);
    long long range_sum(int l, int r, int v, int tl, int tr);

    void build(const std::vector<int>& a) { build(a, 1, 0, n_ - 1); }
    void point_update(int idx, int val) { point_update(idx, val, 1, 0, n_ - 1); }
    long long range_sum(int l, int r) { return range_sum(l, r, 1, 0, n_ - 1); }

private:
    int n_;
    std::vector<long long> t_;
};

void SegTree::build(const std::vector<int>& a, int v, int tl, int tr) {
    // TODO: leaf = a[tl]; otherwise build children and combine with sum.
    t_.resize(4 * n_, 0);
}

void SegTree::point_update(int idx, int val, int v, int tl, int tr) {
    // TODO: descend to the leaf for idx, then recompute sums on the way up.
}

long long SegTree::range_sum(int l, int r, int v, int tl, int tr) {
    // TODO: three cases - disjoint, fully inside, partial.
    return 0;
}

int main() {
    int n;
    if (!(std::cin >> n)) return 0;
    std::vector<int> a(n);
    for (int i = 0; i < n; ++i) std::cin >> a[i];
    SegTree st(n);
    st.build(a);
    int q; std::cin >> q;
    while (q--) {
        std::string op; std::cin >> op;
        if (op == "u") { int i, v; std::cin >> i >> v; st.point_update(i, v); }
        else { int l, r; std::cin >> l >> r; std::cout << st.range_sum(l, r) << "\n"; }
    }
}
