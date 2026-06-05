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
        auto lit = std::lower_bound(v.begin(), v.end(), x);
        auto rit = std::upper_bound(v.begin(), v.end(), x);
        int L = (int)(lit - v.begin());
        int R = (int)(rit - v.begin());
        std::cout << L << ' ' << R << "\n";
    }
}
