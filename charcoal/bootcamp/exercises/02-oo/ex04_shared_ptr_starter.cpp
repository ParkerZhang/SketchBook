// Exercise 4: Shared ownership with std::shared_ptr.
// Read n. Then read n integers and store them in a shared vector that is
// pointed to by two independent shared_ptr instances. Append 0 to the
// vector through one pointer and read it through the other to demonstrate
// shared state.
#include <iostream>
#include <memory>
#include <vector>

int main() {
    int n;
    if (!(std::cin >> n)) return 0;

    // TODO: declare two shared_ptr<std::vector<int>> that share ownership
    // of the same vector. Read n integers into the vector via ptr1.
    std::shared_ptr<std::vector<int>> ptr1;
    std::shared_ptr<std::vector<int>> ptr2;

    for (int i = 0; i < n; ++i) {
        int x; std::cin >> x;
        // TODO: push into *ptr1
    }

    ptr1->push_back(0);
    std::cout << "use_count=" << ptr1.use_count() << " last=" << ptr2->back() << "\n";
}
