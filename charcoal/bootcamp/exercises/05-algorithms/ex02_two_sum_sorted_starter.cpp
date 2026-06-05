// Exercise 2: Two sum on a sorted array.
// Given a sorted vector v of n integers and a target T, print the
// 0-based indices (i j) of two elements that sum to T, or "-1" if none.
// Use the two-pointer technique in O(n).
#include <iostream>
#include <vector>

int main() {
    int n;
    long long T;
    if (!(std::cin >> n >> T)) return 0;
    std::vector<long long> v(n);
    for (int i = 0; i < n; ++i) std::cin >> v[i];

    // TODO: two-pointer search; print "i j" or "-1"
}
