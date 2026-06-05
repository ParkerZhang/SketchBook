# 09 - Math (Phase 7) Exercises

Number theory and modular arithmetic.

| # | Topic | Files |
|---|-------|-------|
| 1 | Sieve of Eratosthenes | `ex01_sieve_*` |
| 2 | Fast modular exponentiation | `ex02_mod_pow_*` |
| 3 | Modular inverse (Fermat) | `ex03_mod_inverse_*` |
| 4 | nCr mod p | `ex04_ncr_*` |
| 5 | GCD and LCM | `ex05_gcd_lcm_*` |

## Build
```bash
mkdir -p build && cd build
cmake .. && cmake --build .
```

## Sample I/O
- `ex01` `20` -> `8` (primes up to 20)
- `ex02` `2 10 1000` -> `24`
- `ex03` `3 1000000007` -> `333333336`
- `ex04` `5 2 1000000007` -> `10`
- `ex05` `12 18` -> `6 36`

## Tips
- Use `__int128` for the multiply inside `mod_pow` if you ever see `a*a` overflowing `long long`.
- The Fermat inverse only works when `m` is prime. For composite m use the extended Euclidean algorithm.
- For repeated `nCr` queries, precompute factorials **and** inverse factorials once.
