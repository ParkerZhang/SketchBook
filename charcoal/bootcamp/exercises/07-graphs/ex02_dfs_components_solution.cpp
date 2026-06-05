#include <iostream>
#include <vector>
#include <stack>

void dfs(int u, const std::vector<std::vector<int>>& adj,
         std::vector<int>& vis, int& size) {
    std::stack<int> st;
    st.push(u);
    vis[u] = 1;
    while (!st.empty()) {
        int x = st.top(); st.pop();
        ++size;
        for (int v : adj[x]) {
            if (!vis[v]) {
                vis[v] = 1;
                st.push(v);
            }
        }
    }
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
