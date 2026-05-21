# Phase 2: Standard Template Library (STL)

## Core Containers
- **`vector`**: Dynamic array. Use `pb` (push_back) and `pop_back`.
- **`set` / `map`**: Balanced BSTs (O(log N)). `set` stores unique sorted elements; `map` stores key-value pairs.
- **`unordered_set` / `unordered_map`**: Hash tables (O(1) average). Faster but can be "hacked" to O(N) in some contests.
- **`priority_queue`**: Max-heap by default. Use `greater<int>` for min-heap.

## Essential Algorithms
- `sort(all(v))`: Sorts a vector.
- `lower_bound`: First element $\ge$ value.
- `upper_bound`: First element $>$ value.

## Implementation Example
See `example_stl.cpp` for common usage patterns.
