// Exercise 2: DFS on an undirected graph - connected components.
// Read n, m, then m edges. Print the number of connected components
// and the size of each component (one per line).
#include <iostream>
#include <vector>

void dfs(int u, const std::vector<std::vector<int>>& adj,
         std::vector<int>& vis, int& size) {
    // TODO: standard iterative or recursive DFS.
}

int main() {
    int n, m;
    if (!(std::cin >> n >> m)) return 0;
    std::vector<std::vector<int>> adj(n);
    for (int i = 0; i < m; ++i) {
        int u, v; std::cin >> u >> v;
        adj[u].push_back(v);
        adj[v].push_back(u);
    }

    std::vector<int> vis(n, 0);
    int components = 0;
    std::vector<int> sizes;
    for (int u = 0; u < n; ++u) {
        if (!vis[u]) {
            int sz = 0;
            dfs(u, adj, vis, sz);
            sizes.push_back(sz);
            ++components;
        }
    }

    std::cout << components << "\n";
    for (int s : sizes) std::cout << s << "\n";
}
