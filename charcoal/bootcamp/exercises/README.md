# Exercises

One folder per module, each with a `README.md` and a `CMakeLists.txt` that
builds every `*_starter` and `*_solution` binary.

| Module | Folder | Count |
|--------|--------|-------|
| 1. Basics (syntax, control flow, references) | `01-basics/` | 6 |
| 2. OOP / RAII | `02-oo/` | 4 |
| 3. STL (containers, algorithms) | `03-stl/` | 5 |
| 4. Modern C++ (move, lambdas, templates, threads) | `04-modern-cpp/` | 4 |
| 5. Foundational algorithms (prefix sums, two pointers, sliding window, binary search, greedy) | `05-algorithms/` | 6 |
| 6. Data structures (DSU, Fenwick, segment tree, sparse table) | `06-data-structures/` | 4 |
| 7. Graphs (BFS, DFS, Dijkstra, Kruskal, topo) | `07-graphs/` | 5 |
| 8. Dynamic programming (knapsack, LCS, LIS, coin change, edit distance) | `08-dp/` | 5 |
| 9. Number theory (sieve, mod pow, mod inverse, nCr, GCD/LCM) | `09-math/` | 5 |

Total: **44 starter/solution pairs** (88 binaries).

## Workflow
For each exercise:
1. Open the `*_starter.cpp` file in the matching folder.
2. Implement the `TODO` blocks.
3. Build with the top-level `CMakeLists.txt` from `templates/`.
4. Run the binary against the sample I/O in the folder's `README.md`.
5. Compare with the `*_solution.cpp` once your tests pass.

## Build everything
```bash
mkdir -p build && cd build
cmake -DCMAKE_PROJECT_TOP_LEVEL_INCLUDES=../templates/CMakeLists.txt .. 2>/dev/null || true
# Or simply:
cp ../templates/CMakeLists.txt ../CMakeLists.txt && cd .. && mkdir -p build && cd build
cmake .. && cmake --build .
```

## Build a single module
```bash
cd exercises/01-basics
mkdir -p build && cd build
cmake .. && cmake --build .
```

## Conventions
- Every starter file has `TODO` markers showing exactly what to implement.
- Every solution file is a complete, compilable C++17 program.
- Use the existing `C++/template.cpp` for free-form practice problems.
