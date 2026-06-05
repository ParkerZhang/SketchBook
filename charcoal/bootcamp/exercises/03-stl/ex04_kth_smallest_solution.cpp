#include <iostream>
#include <set>

int main() {
    int n;
    if (!(std::cin >> n)) return 0;
    std::set<int> s;
    for (int i = 0; i < n; ++i) {
        int x; std::cin >> x;
        s.insert(x);
    }

    int q;
    std::cin >> q;
    while (q--) {
        int k; std::cin >> k;
        if (k < 1 || k > (int)s.size()) {
            std::cout << "NA\n";
        } else {
            auto it = std::next(s.begin(), k - 1);
            std::cout << *it << "\n";
        }
    }
}
