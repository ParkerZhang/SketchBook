# Phase 4: Intermediate Data Structures

## Key Structures
1.  **DSU (Disjoint Set Union)**: Efficiently manage equivalence classes. Operations: `find` and `unite`.
2.  **Fenwick Tree (Binary Indexed Tree)**: Range sum queries and point updates in O(log N).
3.  **Segment Tree**: More powerful than Fenwick; supports range updates (with lazy propagation) and range queries (min, max, gcd, etc.).
4.  **Sparse Table**: O(N log N) preprocessing for O(1) range queries (non-updating range min/max).

## Implementation Tips
- DSU is the most frequently used structure in this category.
- Segment trees can be implemented recursively or iteratively.
