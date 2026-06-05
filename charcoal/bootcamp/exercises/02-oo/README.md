# 02 - OOP / RAII Exercises

Practice writing classes, applying RAII, and using smart pointers.

| # | Topic | Files |
|---|-------|-------|
| 1 | Class with constructors, destructor, static member | `ex01_counter_*` |
| 2 | `Matrix` with RAII + Rule of Five | `ex02_matrix_raii_*` |
| 3 | Non-copyable, movable resource with `unique_ptr` | `ex03_unique_ptr_*` |
| 4 | Shared ownership with `shared_ptr` | `ex04_shared_ptr_*` |

## Build
```bash
mkdir -p build && cd build
cmake .. && cmake --build .
```

## What to focus on
- `ex01`: notice that the static counter only makes sense if every ctor/dtor touches it.
- `ex02`: implement the copy operations **and** the move operations (Rule of Five).
- `ex03`: try to copy a `ResourceHandle` and observe the compiler error.
- `ex04`: `use_count` should be `2` after the aliasing assignment.

## Sample I/O
- `ex01`: see the in-line `expect` comments in `ex01_counter_starter.cpp`.
- `ex02`:
  ```
  2 3
  1 2 3
  4 5 6
  ```
  prints the same matrix on a second line.
- `ex03`: `42`.
- `ex04`: `use_count=2 last=<last input>`.
