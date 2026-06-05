#include <iostream>
#include <string>
#include <vector>
#include <algorithm>

int main() {
    std::string a, b;
    if (!(std::cin >> a >> b)) return 0;
    int n = (int)a.size(), m = (int)b.size();

    std::vector<int> prev(m + 1, 0), cur(m + 1, 0);
    for (int i = 1; i <= n; ++i) {
        std::fill(cur.begin(), cur.end(), 0);
        for (int j = 1; j <= m; ++j) {
            if (a[i-1] == b[j-1]) cur[j] = prev[j-1] + 1;
            else                  cur[j] = std::max(prev[j], cur[j-1]);
        }
        std::swap(prev, cur);
    }
    std::cout << prev[m] << "\n";
}
