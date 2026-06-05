// Exercise 1: Sieve of Eratosthenes.
// Read n. Print the number of primes in [2, n]. n <= 10^6.
#include <iostream>
#include <vector>

int main() {
    int n;
    if (!(std::cin >> n)) return 0;

    std::vector<bool> is_prime(n + 1, true);
    // TODO: classic sieve.
    int count = 0;
    std::cout << count << "\n";
}
