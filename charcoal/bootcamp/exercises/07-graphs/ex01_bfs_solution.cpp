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
    std::queue<int> q;
    dist[s] = 0;
    q.push(s);
    while (!q.empty()) {
        int u = q.front(); q.pop();
        if (u == t) break;
        for (int v : adj[u]) {
            if (dist[v] == -1) {
                dist[v] = dist[u] + 1;
                q.push(v);
            }
        }
    }
    std::cout << dist[t] << "\n";
}
