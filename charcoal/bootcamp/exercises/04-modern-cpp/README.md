# 04 - Modern C++ Exercises

Move semantics, lambdas, templates, and concurrency.

| # | Topic | Files |
|---|-------|-------|
| 1 | Rule of Five (move + copy) | `ex01_move_*` |
| 2 | Lambdas with `std::sort` / `std::count_if` | `ex02_lambda_*` |
| 3 | Function template (`clamp<T>`) | `ex03_clamp_*` |
| 4 | `std::thread` + `std::mutex` | `ex04_threads_*` |

## Build
```bash
mkdir -p build && cd build
cmake .. && cmake --build .
```

## Notes
- `ex01`: after the move, `a` should be in a valid empty state (`size=0`, no data).
- `ex02`: descending sort + count of values `>= 50`.
- `ex03`: use `std::min`/`std::max` so it works for any comparable type.
- `ex04`: requires linking against pthreads (`find_package(Threads REQUIRED)`).

## Sample I/O
- `ex01`: `a: size=0 sum=0` then `b: size=5 sum=15`.
- `ex02`: input `5 / 50 100 20 70 30` -> `100 70 50 30 20` then `3`.
- `ex03`: `10 / 0 / 1`.
- `ex04`: `200000`.
