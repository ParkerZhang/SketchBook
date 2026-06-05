#include <iostream>
#include <vector>
#include <utility>

void reverse_array(std::vector<int>& a) {
    int i = 0, j = (int)a.size() - 1;
    while (i < j) {
        std::swap(a[i], a[j]);
        ++i;
        --j;
    }
}

int main() {
    int n;
    if (!(std::cin >> n)) return 0;
    std::vector<int> a(n);
    for (int i = 0; i < n; ++i) std::cin >> a[i];
    reverse_array(a);
    for (int i = 0; i < n; ++i) {
        if (i) std::cout << ' ';
        std::cout << a[i];
    }
    std::cout << "\n";
}
