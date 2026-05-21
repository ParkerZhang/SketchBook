#include <iostream>
#include <vector>
#include <queue>

using namespace std;

/**
 * Basic DFS Implementation
 */
void dfs(int u, vector<int> adj[], vector<bool>& visited) {
    visited[u] = true;
    cout << u << " ";
    for (int v : adj[u]) {
        if (!visited[v]) {
            dfs(v, adj, visited);
        }
    }
}

/**
 * Basic BFS Implementation
 */
void bfs(int start, vector<int> adj[], int n) {
    vector<bool> visited(n + 1, false);
    queue<int> q;

    visited[start] = true;
    q.push(start);

    while (!q.empty()) {
        int u = q.front();
        q.pop();
        cout << u << " ";

        for (int v : adj[u]) {
            if (!visited[v]) {
                visited[v] = true;
                q.push(v);
            }
        }
    }
}

int main() {
    int n = 5; // nodes 1 to 5
    vector<int> adj[6];
    
    // Build a simple graph
    adj[1].push_back(2); adj[2].push_back(1);
    adj[1].push_back(3); adj[3].push_back(1);
    adj[2].push_back(4); adj[4].push_back(2);
    adj[3].push_back(5); adj[5].push_back(3);

    vector<bool> visited(6, false);
    cout << "DFS traversal: ";
    dfs(1, adj, visited);
    cout << "\n";

    cout << "BFS traversal: ";
    bfs(1, adj, 6);
    cout << "\n";

    return 0;
}
