#include <iostream>
#include <memory>
#include <vector>

int main() {
    int n;
    if (!(std::cin >> n)) return 0;

    auto ptr1 = std::make_shared<std::vector<int>>();
    auto ptr2 = ptr1;

    for (int i = 0; i < n; ++i) {
        int x; std::cin >> x;
        ptr1->push_back(x);
    }

    ptr1->push_back(0);
    std::cout << "use_count=" << ptr1.use_count() << " last=" << ptr2->back() << "\n";
}
