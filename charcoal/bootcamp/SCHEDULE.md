# 2-Week Bootcamp Schedule

## Overview
- **Track**: C++ Competitive Programming (primary) + Quant Finance (supplementary)
- **Goal**: Build from C++ fundamentals through advanced algorithms and data structures
- **Daily time**: ~4-6 hours (adjust as needed)

---

## Week 1 — Foundations & STL

### Day 1: Setup & C++ Basics
- [ ] Read `lessons/00-setup/README.md` — tooling/build setup
- [ ] Work through `C++/01_Basics/README.md`
- [ ] Study `C++/01_Basics/example_basics.cpp`
- [ ] Practice: `exercises/01-basics/starter.cpp` → solution
- [ ] Use `template.cpp` for all new problems

### Day 2: STL Containers
- [ ] Read `C++/02_STL/README.md`
- [ ] Study `C++/02_STL/example_stl.cpp`
- [ ] Master: `vector`, `deque`, `stack`, `queue`, `priority_queue`
- [ ] Practice: Write 3 small programs using different containers

### Day 3: STL Associative & Algorithms
- [ ] `set`, `map`, `unordered_set`, `unordered_map`
- [ ] `sort`, `binary_search`, `lower_bound`, `upper_bound`
- [ ] Study `C++/02_STL/example_stl.cpp` (algorithm sections)
- [ ] Practice: Contest-style problems using STL

### Day 4: Foundational Algorithms
- [ ] Read `C++/03_Algorithms_Foundational/README.md`
- [ ] Study `C++/03_Algorithms_Foundational/example_foundational.cpp`
- [ ] Learn: Big-O, Prefix Sums, Two Pointers, Sliding Window
- [ ] Practice: 2 sliding window + 2 two-pointer problems

### Day 5: Binary Search & Greedy
- [ ] Binary search on answers pattern
- [ ] Greedy algorithm techniques
- [ ] Practice: 2 binary search + 2 greedy problems

### Day 6: OOP & RAII (Modern C++ Track)
- [ ] Read `lessons/02-oo/README.md`
- [ ] RAII, smart pointers (`unique_ptr`, `shared_ptr`)
- [ ] Practice: Implement a simple `Matrix` class with RAII

### Day 7: Review & Contest Practice
- [ ] Review all Week 1 topics
- [ ] Solve 3-4 mixed problems (one from each phase)
- [ ] Optional: Start `courses/quantitative_finance/README.md`

---

## Week 2 — Advanced Topics & Problem Solving

### Day 8: Data Structures — DSU & Fenwick
- [ ] Read `C++/04_Data_Structures/README.md`
- [ ] Study `C++/04_Data_Structures/example_dsu.cpp`
- [ ] Implement DSU from scratch
- [ ] Learn Fenwick Tree (BIT) — point update, range query

### Day 9: Segment Tree & Sparse Table
- [ ] Segment Tree: build, update, query
- [ ] Sparse Table: range min/max queries
- [ ] Practice: 2 range query problems

### Day 10: Graph Traversal & Shortest Paths
- [ ] Read `C++/05_Graphs/README.md`
- [ ] Study `C++/05_Graphs/example_traversal.cpp`
- [ ] DFS (recursive + iterative), BFS
- [ ] Dijkstra, Bellman-Ford

### Day 11: Advanced Graphs & MST
- [ ] Floyd-Warshall (all-pairs shortest path)
- [ ] Kruskal / Prim (MST)
- [ ] Topological Sort (Kahn's algorithm)
- [ ] Practice: 2 graph problems

### Day 12: Dynamic Programming
- [ ] Read `C++/06_DP/README.md`
- [ ] Study `C++/06_DP/example_knapsack.cpp`
- [ ] 0/1 Knapsack, LCS, LIS
- [ ] Practice: 2 DP problems

### Day 13: Number Theory & Math
- [ ] Read `C++/07_Math/README.md`
- [ ] Study `C++/07_Math/example_math.cpp`
- [ ] GCD/LCM (Euclidean), Sieve, Modular Arithmetic
- [ ] Practice: 2 math problems

### Day 14: Capstone & Quant Finance
- [ ] Solve a full contest simulation (4 problems, 2 hours)
- [ ] Pick a `courses/` module:
  - `courses/quantitative_finance/`
  - `courses/financial_risk/`
  - `courses/pricing_models/`
- [ ] Review notes and fill knowledge gaps

---

## Quick Reference

| Phase | Folder | Key Topics |
|-------|--------|------------|
| 1 | `C++/01_Basics/` | I/O, pointers, recursion |
| 2 | `C++/02_STL/` | Containers, algorithms |
| 3 | `C++/03_Algorithms_Foundational/` | Prefix sums, sliding window, greedy |
| 4 | `C++/04_Data_Structures/` | DSU, BIT, Segtree |
| 5 | `C++/05_Graphs/` | BFS, DFS, shortest path, MST |
| 6 | `C++/06_DP/` | Knapsack, LCS, LIS |
| 7 | `C++/07_Math/` | Sieve, modular, combinatorics |
| — | `courses/*/` | Quant finance applications |

## Tips
- **Template**: Copy `C++/template.cpp` for every new problem
- **Fast I/O**: Always use `ios::sync_with_stdio(0); cin.tie(0);`
- **Log your progress**: Note which problems you solve in each directory
- **Pacing**: If behind, prioritize Phase 2 (STL) and Phase 3 (algorithms) — they're highest leverage for contests
