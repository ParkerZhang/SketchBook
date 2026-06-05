#include <iostream>
#include <vector>
#include <algorithm>

int main() {
    int n;
    if (!(std::cin >> n)) return 0;
    std::vector<int> a(n);
    for (int i = 0; i < n; ++i) std::cin >> a[i];

    std::sort(a.begin(), a.end());
    a.erase(std::unique(a.begin(), a.end()), a.end());

    for (size_t i = 0; i < a.size(); ++i) {
        if (i) std::cout << ' ';
        std::cout << a[i];
    }
    std::cout << "\n";
}
