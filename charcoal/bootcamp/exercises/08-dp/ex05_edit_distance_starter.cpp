// Exercise 5: Edit distance (Levenshtein).
// Read two strings a, b. Print the minimum number of single-character
// insertions, deletions or substitutions to turn a into b.
#include <iostream>
#include <string>
#include <vector>
#include <algorithm>

int main() {
    std::string a, b;
    if (!(std::cin >> a >> b)) return 0;
    int n = (int)a.size(), m = (int)b.size();

    std::vector<int> prev(m + 1), cur(m + 1);
    // TODO: init prev[j] = j (insert j chars into empty a to get b[0..j-1]).

    for (int i = 1; i <= n; ++i) {
        // TODO: cur[0] = i, then fill cur[j] from prev.
        std::swap(prev, cur);
    }
    std::cout << prev[m] << "\n";
}
