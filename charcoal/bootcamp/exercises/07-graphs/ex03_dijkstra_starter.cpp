// Exercise 3: Dijkstra on a non-negative weighted graph.
// Read n, m, then m edges (u, v, w). Read s, t. Print the length of
// the shortest path from s to t, or -1 if unreachable.
#include <iostream>
#include <vector>
#include <queue>
#include <utility>
#include <limits>

int main() {
    int n, m;
    if (!(std::cin >> n >> m)) return 0;
    std::vector<std::vector<std::pair<int,int>>> adj(n);
    for (int i = 0; i < m; ++i) {
        int u, v, w; std::cin >> u >> v >> w;
        adj[u].push_back({v, w});
        adj[v].push_back({u, w});
    }
    int s, t; std::cin >> s >> t;

    constexpr long long INF = std::numeric_limits<long long>::max() / 4;
    std::vector<long long> dist(n, INF);
    // TODO: priority_queue of (dist, node), decrease-key on relaxation.
    std::cout << (dist[t] == INF ? -1 : dist[t]) << "\n";
}
