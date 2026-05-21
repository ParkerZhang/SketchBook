#include <iostream>
#include <vector>
#include <algorithm>
#include <set>
#include <map>
#include <queue>

using namespace std;

int main() {
    // 1. Vector and Sorting
    vector<int> v = {5, 2, 8, 1, 9};
    sort(v.begin(), v.end()); // [1, 2, 5, 8, 9]

    // 2. Binary Search (lower_bound)
    auto it = lower_bound(v.begin(), v.end(), 5);
    if (it != v.end()) {
        cout << "Lower bound of 5 is at index: " << distance(v.begin(), it) << "\n";
    }

    // 3. Map (Frequency counter)
    map<string, int> freq;
    freq["apple"]++;
    freq["banana"]++;
    freq["apple"]++;
    cout << "Apple count: " << freq["apple"] << "\n";

    // 4. Priority Queue (Min-heap)
    priority_queue<int, vector<int>, greater<int>> min_heap;
    min_heap.push(10);
    min_heap.push(5);
    min_heap.push(20);
    cout << "Smallest element: " << min_heap.top() << "\n"; // 5

    return 0;
}
