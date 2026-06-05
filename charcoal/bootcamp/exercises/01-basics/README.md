# 01 - Basics Exercises

Warm-up problems covering syntax, control flow, functions, references, and basic algorithms.

| # | Topic | Starter | Solution |
|---|-------|---------|----------|
| 1 | Iterative factorial | `ex01_factorial_starter.cpp` | `ex01_factorial_solution.cpp` |
| 2 | Iterative Fibonacci | `ex02_fibonacci_starter.cpp` | `ex02_fibonacci_solution.cpp` |
| 3 | Calculator + division-by-zero | `ex03_calculator_starter.cpp` | `ex03_calculator_solution.cpp` |
| 4 | Reverse via pass-by-reference | `ex04_reverse_starter.cpp` | `ex04_reverse_solution.cpp` |
| 5 | GCD (Euclidean) | `ex05_gcd_starter.cpp` | `ex05_gcd_solution.cpp` |
| 6 | Palindrome (two indices) | `ex06_palindrome_starter.cpp` | `ex06_palindrome_solution.cpp` |

## Build
```bash
mkdir -p build && cd build
cmake .. && cmake --build .
```

## How to work
1. Open the `_starter.cpp` file for an exercise.
2. Fill in the `TODO` sections.
3. Compile and test with the sample inputs in each problem header.
4. Compare with the `_solution.cpp` once your tests pass.

## Sample I/O quick-check
- `ex01` `5` -> `120`
- `ex02` `10` -> `55`
- `ex03` `7 / 0` -> `0` then `ERR`
- `ex04` `4 1 2 3 4` -> `4 3 2 1`
- `ex05` `48 18` -> `6`
- `ex06` `racecar` -> `YES`
