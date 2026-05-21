#include <iostream>
#include <vector>
#include <numeric>

using namespace std;

/**
 * Disjoint Set Union (DSU) Implementation
 * - Path compression
 * - Union by rank/size
 */
struct DSU {
    vector<int> parent;
    DSU(int n) {
        parent.resize(n);
        iota(parent.begin(), parent.end(), 0);
    }
    int find(int i) {
        if (parent[i] == i) return i;
        return parent[i] = find(parent[i]); // Path compression
    }
    void unite(int i, int j) {
        int root_i = find(i);
        int root_j = find(j);
        if (root_i != root_j) parent[root_i] = root_j;
    }
};

int main() {
    DSU dsu(10);
    dsu.unite(1, 2);
    dsu.unite(2, 3);
    
    if (dsu.find(1) == dsu.find(3)) {
        cout << "1 and 3 are in the same component\n";
    }
    
    return 0;
}
