#include <iostream>
#include <vector>

class Fenwick {
public:
    explicit Fenwick(int n) : n_(n), bit_(n + 1, 0) {}

    void add(int idx, int delta) {
        for (++idx; idx <= n_; idx += idx & -idx) bit_[idx] += delta;
    }
    long long sum_prefix(int idx) const {
        long long s = 0;
        for (++idx; idx > 0; idx -= idx & -idx) s += bit_[idx];
        return s;
    }
    long long range_sum(int l, int r) const {
        return sum_prefix(r) - (l ? sum_prefix(l - 1) : 0);
    }
    void set(int idx, int v) {
        long long cur = sum_prefix(idx) - (idx ? sum_prefix(idx - 1) : 0);
        add(idx, v - cur);
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
