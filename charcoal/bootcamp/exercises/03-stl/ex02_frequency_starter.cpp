// Exercise 2: Frequency table.
// Read n words and print each distinct word with the number of times
// it appears, in alphabetical order. Use std::map.
#include <iostream>
#include <string>
#include <map>

int main() {
    int n;
    if (!(std::cin >> n)) return 0;
    std::map<std::string, int> freq;

    // TODO: read n words and bump freq[word] for each.
    for (int i = 0; i < n; ++i) {
        std::string w; std::cin >> w;
        // TODO
    }

    for (const auto& [w, c] : freq) {
        std::cout << w << ' ' << c << "\n";
    }
}
