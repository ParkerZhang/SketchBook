// Exercise 2: Fenwick tree (Binary Indexed Tree).
// Maintain an array a[0..n-1] of integers. Support:
//   "add idx delta"  - a[idx] += delta
//   "sum l r"        - print sum a[l..r]  (0-indexed, inclusive)
//   "set idx v"      - a[idx] = v
// All operations in O(log n).
#include <iostream>
#include <vector>

class Fenwick {
public:
    explicit Fenwick(int n) : n_(n), bit_(n + 1, 0) {}

    void add(int idx, int delta) {
        // TODO: standard BIT add
    }
    long long sum_prefix(int idx) const {
        // TODO: prefix sum up to and including idx
        return 0;
    }
    long long range_sum(int l, int r) const {
        return sum_prefix(r) - (l ? sum_prefix(l - 1) : 0);
    }
    void set(int idx, int v) {
        // TODO: compute current value at idx, then add (v - current)
    }

private:
    int n_;
    std::vector<long long> bit_;
};

int main() {
    int n;
    if (!(std::cin >> n)) return 0;
    Fenwick fw(n);
    std::string op;
    while (std::cin >> op) {
        if (op == "add") { int i, d; std::cin >> i >> d; fw.add(i, d); }
        else if (op == "sum") { int l, r; std::cin >> l >> r; std::cout << fw.range_sum(l, r) << "\n"; }
        else if (op == "set") { int i, v; std::cin >> i >> v; fw.set(i, v); }
    }
}
