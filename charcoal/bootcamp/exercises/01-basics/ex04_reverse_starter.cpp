// Exercise 4: Pass-by-reference practice.
// Read n, then n integers. Print them in reverse order.
// Implement reverse_array using std::swap and references.
#include <iostream>
#include <vector>

void reverse_array(std::vector<int>& a) {
    // TODO: reverse a in place
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
