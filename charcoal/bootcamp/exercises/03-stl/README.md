# 03 - STL Exercises

Container and algorithm practice.

| # | Topic | Files |
|---|-------|-------|
| 1 | Sort + unique a vector | `ex01_unique_sorted_*` |
| 2 | Frequency table with `std::map` | `ex02_frequency_*` |
| 3 | Balanced brackets with `std::stack` | `ex03_brackets_*` |
| 4 | K-th smallest with `std::set` | `ex04_kth_smallest_*` |
| 5 | `lower_bound` / `upper_bound` | `ex05_bounds_*` |

## Build
```bash
mkdir -p build && cd build
cmake .. && cmake --build .
```

## Sample I/O
- `ex01` `6 / 3 1 2 2 4 3` -> `1 2 3 4`
- `ex02` `7 / the quick brown fox jumps over the` ->
  ```
  brown 1
  fox 1
  jumps 1
  over 1
  quick 1
  the 2
  ```
- `ex03` `{[()]}` -> `YES`; `{[(])}` -> `NO`
- `ex04` `n=5 v=4 2 5 2 1 q=3 k=1 3 5` -> `1 4 5`
- `ex05` `n=6 v=1 2 4 4 4 6 q=1 x=4` -> `2 5`

## Hints
- `ex04` uses `std::next` to advance the iterator in O(k). For large k use an order-statistics tree (`pbds`) or sort + index.
- `ex05` the right bound is the standard idiom for "number of elements equal to x".
