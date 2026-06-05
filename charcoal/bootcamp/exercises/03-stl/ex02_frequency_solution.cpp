#include <iostream>
#include <string>
#include <map>

int main() {
    int n;
    if (!(std::cin >> n)) return 0;
    std::map<std::string, int> freq;

    for (int i = 0; i < n; ++i) {
        std::string w; std::cin >> w;
        ++freq[w];
    }

    for (const auto& [w, c] : freq) {
        std::cout << w << ' ' << c << "\n";
    }
}
