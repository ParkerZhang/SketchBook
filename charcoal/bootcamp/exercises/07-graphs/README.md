# 07 - Graphs (Phase 5) Exercises

Implement the most common graph algorithms.

| # | Topic | Files |
|---|-------|-------|
| 1 | BFS shortest path (unweighted) | `ex01_bfs_*` |
| 2 | DFS connected components | `ex02_dfs_components_*` |
| 3 | Dijkstra shortest path (non-negative weights) | `ex03_dijkstra_*` |
| 4 | Kruskal MST | `ex04_mst_kruskal_*` |
| 5 | Topological sort (Kahn) | `ex05_topo_sort_*` |

## Build
```bash
mkdir -p build && cd build
cmake .. && cmake --build .
```

## Sample I/O
- `ex01` `n=5 m=4 / 0 1 / 1 2 / 2 3 / 3 4 / s=0 t=4` -> `4`
- `ex02` `n=5 m=2 / 0 1 / 3 4` -> `3`, then `2`, `1`, `2`
- `ex03` `n=4 m=4 / 0 1 1 / 1 2 2 / 0 2 5 / 2 3 1 / s=0 t=3` -> `4` (0-1-2-3)
- `ex04` `n=4 m=5 / 0 1 1 / 0 2 4 / 1 2 2 / 1 3 5 / 2 3 1` -> `4`
- `ex05` `n=3 m=3 / 0 1 / 1 2 / 0 2` -> `0 1 2`

## Tips
- Always use `long long` for distances / total weights.
- For Dijkstra on a static graph, `priority_queue<pair<ll,int>, vector<...>, greater<...>>` is the cleanest pattern.
- Topo sort with Kahn's algorithm gives an easy cycle detection at the end.
