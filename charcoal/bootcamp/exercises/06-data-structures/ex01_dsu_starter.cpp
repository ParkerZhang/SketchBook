// Exercise 1: Disjoint Set Union (Union-Find) with path compression
// and union by rank.
// Read n, then a sequence of operations:
//   "u a b"   - unite sets containing a and b
//   "q a b"   - print 1 if a and b are in the same set, else 0
//   "s a"     - print the size of the set containing a
// Operands are 0-indexed in [0, n).
#include <iostream>
#include <vector>

class DSU {
public:
    DSU(int n) : p_(n, -1) {}

    int find(int x) {
        // TODO: iterative path compression
        return 0;
    }
    bool unite(int a, int b) {
        // TODO: union by rank/size, return true if merged
        return false;
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
