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
            std::lock_guard<std::mutex> g(m);
            ++counter;
        }
    };

    std::vector<std::thread> ts;
    ts.emplace_back(worker);
    ts.emplace_back(worker);
    for (auto& t : ts) t.join();

    std::cout << counter << "\n";
}
