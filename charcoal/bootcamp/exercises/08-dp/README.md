# 08 - Dynamic Programming (Phase 6) Exercises

Classical DP problems.

| # | Topic | Files |
|---|-------|-------|
| 1 | 0/1 Knapsack | `ex01_knapsack_01_*` |
| 2 | Longest Common Subsequence | `ex02_lcs_*` |
| 3 | Longest Increasing Subsequence (O(N log N)) | `ex03_lis_*` |
| 4 | Coin change (unbounded, min coins) | `ex04_coin_change_*` |
| 5 | Edit distance (Levenshtein) | `ex05_edit_distance_*` |

## Build
```bash
mkdir -p build && cd build
cmake .. && cmake --build .
```

## Sample I/O
- `ex01` `n=3 W=8 / 3 30 / 4 50 / 5 60` -> `90` (3+5)
- `ex02` `abcde ace` -> `3` ("ace")
- `ex03` `n=8 a=3 1 4 1 5 9 2 6` -> `4` (1 4 5 9 or 1 4 5 6)
- `ex04` `n=3 T=11 / 1 2 5` -> `3` (5+5+1)
- `ex05` `kitten sitting` -> `3`

## Tips
- 1-D DP for knapsack: iterate `cap` **downwards** so each item is used at most once.
- LCS and edit distance share the same 2-D table structure; only the recurrence changes.
- LIS via `lower_bound` works for strictly increasing; for non-decreasing use `upper_bound`.
