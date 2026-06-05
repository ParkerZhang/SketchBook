// Exercise 5: lower_bound / upper_bound on a sorted vector.
// Read a sorted vector v of length n, then q queries x.
// For each x, print "L R" where L is the first index with v[L] >= x
// and R is the first index with v[R] > x. Use std::lower_bound and
// std::upper_bound. If the bound is past the end, print n.
#include <iostream>
#include <vector>
#include <algorithm>

int main() {
    int n;
    if (!(std::cin >> n)) return 0;
    std::vector<int> v(n);
    for (int i = 0; i < n; ++i) std::cin >> v[i];

    int q;
    std::cin >> q;
    while (q--) {
        int x; std::cin >> x;
        // TODO: compute L and R and print.
    }
}
