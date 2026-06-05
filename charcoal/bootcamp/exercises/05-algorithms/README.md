# 05 - Algorithms (Phase 3) Exercises

Foundational competitive-programming techniques.

| # | Topic | Files |
|---|-------|-------|
| 1 | Prefix sums (range query, O(1)) | `ex01_prefix_sums_*` |
| 2 | Two pointers (two-sum on sorted array) | `ex02_two_sum_sorted_*` |
| 3 | Sliding window, fixed size (max sum k) | `ex03_sliding_window_*` |
| 4 | Sliding window, variable size (longest sum ≤ S) | `ex04_longest_window_*` |
| 5 | Binary search on the answer (ship packages) | `ex05_binsearch_answer_*` |
| 6 | Greedy interval scheduling | `ex06_greedy_intervals_*` |

## Build
```bash
mkdir -p build && cd build
cmake .. && cmake --build .
```

## Sample I/O
- `ex01` `n=5 a=1 2 3 4 5 q=2 / 0 2 / 1 3` -> `6 / 9`
- `ex02` `n=6 T=9 v=1 3 4 5 7 9` -> `2 4` (4+5)
- `ex03` `n=7 k=3 a=2 1 5 1 3 2 6` -> `11` (window 3 2 6 sums to 11)
- `ex04` `n=5 S=10 a=1 2 3 4 5` -> `3` (1+2+3=6, 2+3+4=9, 3+4=7; longest is 3)
- `ex05` `n=4 C=3 w=1 2 3 4` -> `3` (with capacity 3 the days are 1|2,3,4 -> 3 days)
- `ex06` `n=4 [(1,3),(2,4),(3,5),(0,6)]` -> `2` (pick (1,3) and (3,5))

## Tips
- For `ex05`, the predicate "can we ship in D days at capacity `cap`?" is monotonic.
- For `ex06`, the `l >= last_end` (rather than `>`) lets intervals that meet at a point be picked.
