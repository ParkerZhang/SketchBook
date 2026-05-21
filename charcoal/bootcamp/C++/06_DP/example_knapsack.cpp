#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

/**
 * 0/1 Knapsack DP Example
 * Find max value that can be put in a knapsack of capacity W.
 */
int knapsack(int W, vector<int>& weights, vector<int>& values, int n) {
    vector<int> dp(W + 1, 0);

    for (int i = 0; i < n; i++) {
        for (int w = W; w >= weights[i]; w--) {
            dp[w] = max(dp[w], dp[w - weights[i]] + values[i]);
        }
    }
    return dp[W];
}

int main() {
    vector<int> weights = {2, 3, 4, 5};
    vector<int> values = {3, 4, 5, 6};
    int W = 5;
    cout << "Max Knapsack Value: " << knapsack(W, weights, values, weights.size()) << "\n";
    return 0;
}
