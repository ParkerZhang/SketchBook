#include <iostream>
#include <vector>

class DSU {
public:
    DSU(int n) : p_(n, -1) {}

    int find(int x) {
        while (p_[x] >= 0) {
            if (p_[p_[x]] >= 0) p_[x] = p_[p_[x]];
            x = p_[x];
        }
        return x;
    }

    bool unite(int a, int b) {
        a = find(a);
        b = find(b);
        if (a == b) return false;
        if (p_[a] > p_[b]) std::swap(a, b);
        p_[a] += p_[b];
        p_[b] = a;
        return true;
    }

    int size(int x) { return -p_[find(x)]; }

private:
    std::vector<int> p_;
};

int main() {
    int n;
    if (!(std::cin >> n)) return 0;
    DSU dsu(n);
    std::string op;
    while (std::cin >> op) {
        if (op == "u") { int a, b; std::cin >> a >> b; dsu.unite(a, b); }
        else if (op == "q") { int a, b; std::cin >> a >> b; std::cout << (dsu.find(a) == dsu.find(b)) << "\n"; }
        else if (op == "s") { int a; std::cin >> a; std::cout << dsu.size(a) << "\n"; }
    }
}
