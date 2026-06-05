#include <iostream>
#include <vector>
#include <algorithm>

int main() {
    int n;
    if (!(std::cin >> n)) return 0;
    std::vector<int> a(n);
    for (int i = 0; i < n; ++i) std::cin >> a[i];

    std::vector<int> tail;
    for (int x : a) {
        auto it = std::lower_bound(tail.begin(), tail.end(), x);
        if (it == tail.end()) tail.push_back(x);
        else                  *it = x;
    }
    std::cout << (int)tail.size() << "\n";
}
