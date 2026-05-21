# Phase 6: Dynamic Programming (DP)

## Core Idea
Breaking down a complex problem into simpler subproblems and storing the results of subproblems to avoid redundant calculations.

## Classical Problems
1.  **Knapsack (0/1 and Unbounded)**.
2.  **LCS (Longest Common Subsequence)**.
3.  **LIS (Longest Increasing Subsequence)** (O(N log N) optimization).
4.  **Edit Distance**.
5.  **Coin Change**.

## Common DP Types
- **Linear DP**: Result depends on previous 1 or 2 states.
- **Bitmask DP**: Used when $N$ is small ($\le 20$) and states can be represented as bits.
- **Range DP**: Solving for ranges $[i, j]$.
- **Tree DP**: DP on tree structures.
