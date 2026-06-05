#include <iostream>
#include <vector>
#include <algorithm>
#include <tuple>

struct DSU {
    std::vector<int> p;
    DSU(int n) : p(n, -1) {}
    int find(int x) { while (p[x] >= 0) { if (p[p[x]] >= 0) p[x] = p[p[x]]; x = p[x]; } return x; }
    bool unite(int a, int b) {
        a = find(a); b = find(b);
        if (a == b) return false;
        if (p[a] > p[b]) std::swap(a, b);
        p[a] += p[b]; p[b] = a; return true;
    }
};

int main() {
    int n, m;
    if (!(std::cin >> n >> m)) return 0;
    std::vector<std::tuple<int,int,int>> edges;
    edges.reserve(m);
    for (int i = 0; i < m; ++i) {
        int u, v, w; std::cin >> u >> v >> w;
        edges.emplace_back(w, u, v);
    }
    std::sort(edges.begin(), edges.end());

    DSU dsu(n);
    long long total = 0;
    int used = 0;
    for (auto& [w, u, v] : edges) {
        if (dsu.unite(u, v)) {
            total += w;
            ++used;
            if (used == n - 1) break;
        }
    }
    if (used == n - 1) std::cout << total << "\n";
    else               std::cout << "IMPOSSIBLE\n";
}
