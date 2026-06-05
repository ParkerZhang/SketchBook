// Exercise 5: Topological sort (Kahn's algorithm).
// Read n, m, then m directed edges (u, v). Print a topological order,
// or "CYCLE" if the graph has one.
#include <iostream>
#include <vector>
#include <queue>

int main() {
    int n, m;
    if (!(std::cin >> n >> m)) return 0;
    std::vector<std::vector<int>> adj(n);
    std::vector<int> indeg(n, 0);
    for (int i = 0; i < m; ++i) {
        int u, v; std::cin >> u >> v;
        adj[u].push_back(v);
        ++indeg[v];
    }

    std::queue<int> q;
    for (int i = 0; i < n; ++i) if (indeg[i] == 0) q.push(i);

    std::vector<int> order;
    // TODO: pop q, append to order, decrement neighbours' indeg, push 0's.

    if ((int)order.size() != n) std::cout << "CYCLE\n";
    else {
        for (int i = 0; i < n; ++i) {
            if (i) std::cout << ' ';
            std::cout << order[i];
        }
        std::cout << "\n";
    }
}
