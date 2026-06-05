// Exercise 4: Threads and a mutex.
// Spawn two threads, each incrementing a shared counter 100000 times.
// Use std::mutex to make the increment atomic. Print the final value (200000).
#include <iostream>
#include <thread>
#include <mutex>
#include <vector>

int main() {
    int counter = 0;
    std::mutex m;
    constexpr int N = 100000;

    auto worker = [&]() {
        for (int i = 0; i < N; ++i) {
            // TODO: lock the mutex, increment counter, unlock.
        }
    };

    std::vector<std::thread> ts;
    ts.emplace_back(worker);
    ts.emplace_back(worker);
    for (auto& t : ts) t.join();

    std::cout << counter << "\n";
}
