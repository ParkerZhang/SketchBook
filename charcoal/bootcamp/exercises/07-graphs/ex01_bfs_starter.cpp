// Exercise 1: BFS shortest path in an unweighted undirected graph.
// Read n, m, then m edges (0-indexed). Read s, t. Print the length of
// the shortest path from s to t, or -1 if unreachable.
#include <iostream>
#include <vector>
#include <queue>

int main() {
    int n, m;
    if (!(std::cin >> n >> m)) return 0;
    std::vector<std::vector<int>> adj(n);
    for (int i = 0; i < m; ++i) {
        int u, v; std::cin >> u >> v;
        adj[u].push_back(v);
        adj[v].push_back(u);
    }
    int s, t; std::cin >> s >> t;

    std::vector<int> dist(n, -1);
    // TODO: BFS from s, fill dist, then print dist[t].
    std::cout << dist[t] << "\n";
}
