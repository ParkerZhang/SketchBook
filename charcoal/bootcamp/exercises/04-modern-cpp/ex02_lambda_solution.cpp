#include <iostream>
#include <vector>
#include <algorithm>

int main() {
    int n;
    if (!(std::cin >> n)) return 0;
    std::vector<int> v(n);
    for (int i = 0; i < n; ++i) std::cin >> v[i];

    std::sort(v.begin(), v.end(), [](int a, int b) { return a > b; });

    int ge50 = (int)std::count_if(v.begin(), v.end(), [](int x) { return x >= 50; });

    for (int i = 0; i < n; ++i) {
        if (i) std::cout << ' ';
        std::cout << v[i];
    }
    std::cout << "\n" << ge50 << "\n";
}
