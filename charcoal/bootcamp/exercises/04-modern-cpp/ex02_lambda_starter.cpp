// Exercise 2: Lambdas and std::function.
// Read n integers. Sort the array in descending order using a lambda
// passed to std::sort. Then count how many values are >= 50 using
// std::count_if with another lambda.
#include <iostream>
#include <vector>
#include <algorithm>
#include <functional>

int main() {
    int n;
    if (!(std::cin >> n)) return 0;
    std::vector<int> v(n);
    for (int i = 0; i < n; ++i) std::cin >> v[i];

    // TODO: sort v in descending order using a lambda
    // std::sort(v.begin(), v.end(), /* lambda */);

    // TODO: count values >= 50 with std::count_if + lambda
    int ge50 = 0;

    for (int i = 0; i < n; ++i) {
        if (i) std::cout << ' ';
        std::cout << v[i];
    }
    std::cout << "\n" << ge50 << "\n";
}
