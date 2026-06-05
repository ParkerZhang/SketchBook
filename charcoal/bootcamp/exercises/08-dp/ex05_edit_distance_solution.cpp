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
    for (int j = 0; j <= m; ++j) prev[j] = j;

    for (int i = 1; i <= n; ++i) {
        cur[0] = i;
        for (int j = 1; j <= m; ++j) {
            if (a[i-1] == b[j-1]) cur[j] = prev[j-1];
            else cur[j] = 1 + std::min({prev[j],      // delete
                                        cur[j-1],      // insert
                                        prev[j-1]});   // substitute
        }
        std::swap(prev, cur);
    }
    std::cout << prev[m] << "\n";
}
