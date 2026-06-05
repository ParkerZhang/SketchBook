# 06 - Data Structures (Phase 4) Exercises

Implement and use the most common contest data structures.

| # | Topic | Files |
|---|-------|-------|
| 1 | DSU with path compression + union by size | `ex01_dsu_*` |
| 2 | Fenwick tree (BIT) for range sum + point update | `ex02_fenwick_*` |
| 3 | Segment tree (recursive, range sum + point update) | `ex03_segtree_*` |
| 4 | Sparse table (range min, O(1) query) | `ex04_sparse_table_*` |

## Build
```bash
mkdir -p build && cd build
cmake .. && cmake --build .
```

## Sample I/O
- `ex01` `n=5 / u 0 1 / u 2 3 / q 0 2 / s 0 / q 1 3` -> `0`, `2`, `0`
- `ex02` `n=5` start with all zeros, then
  `add 1 3 / add 3 2 / sum 0 4 / set 1 0 / sum 0 4` -> `5 / 2`
- `ex03` `n=5 a=1 2 3 4 5 q=3 / q 0 4 / u 2 10 / q 0 4` -> `15 / 22`
- `ex04` `n=8 a=2 5 1 7 3 6 4 8 q=2 / q 1 4 / q 0 7` -> `1 / 1`

## Tips
- DSU is the most frequently used structure in this category. Master it first.
- For range min with updates, segment tree is the right tool; sparse table is for static arrays only.
- Sparse table works for any idempotent op (min, max, gcd) but **not** for sum.
