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
    using P = std::pair<long long, int>;
    std::priority_queue<P, std::vector<P>, std::greater<P>> pq;

    dist[s] = 0;
    pq.push({0, s});
    while (!pq.empty()) {
        auto [d, u] = pq.top(); pq.pop();
        if (d != dist[u]) continue;
        if (u == t) break;
        for (auto [v, w] : adj[u]) {
            if (dist[v] > d + w) {
                dist[v] = d + w;
                pq.push({dist[v], v});
            }
        }
    }
    std::cout << (dist[t] == INF ? -1 : dist[t]) << "\n";
}
