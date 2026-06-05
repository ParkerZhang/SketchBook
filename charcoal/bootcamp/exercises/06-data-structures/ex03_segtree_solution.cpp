// Exercise 3: Segment tree for range sum / point update.
// Read n, then n integers. Then read q queries:
//   "u idx val"  - point update: a[idx] = val
//   "q l r"      - print sum of a[l..r] (0-indexed, inclusive)
// Implement the segment tree recursively.
#include <iostream>
#include <vector>

class SegTree {
public:
    explicit SegTree(int n) : n_(n), t_(4 * n, 0) {}
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
    if (tl == tr) { t_[v] = a[tl]; return; }
    int tm = (tl + tr) / 2;
    build(a, 2*v, tl, tm);
    build(a, 2*v+1, tm+1, tr);
    t_[v] = t_[2*v] + t_[2*v+1];
}

void SegTree::point_update(int idx, int val, int v, int tl, int tr) {
    if (tl == tr) { t_[v] = val; return; }
    int tm = (tl + tr) / 2;
    if (idx <= tm) point_update(idx, val, 2*v, tl, tm);
    else          point_update(idx, val, 2*v+1, tm+1, tr);
    t_[v] = t_[2*v] + t_[2*v+1];
}

long long SegTree::range_sum(int l, int r, int v, int tl, int tr) {
    if (l > tr || r < tl) return 0;
    if (l <= tl && tr <= r) return t_[v];
    int tm = (tl + tr) / 2;
    return range_sum(l, r, 2*v, tl, tm) + range_sum(l, r, 2*v+1, tm+1, tr);
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
