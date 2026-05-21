#include <iostream>
#include <vector>

using namespace std;

/**
 * Example: Prefix Sums
 * Given an array, answer multiple queries for the sum of range [L, R].
 */
void prefix_sum_example() {
    vector<int> a = {1, 2, 3, 4, 5};
    int n = a.size();
    vector<long long> pref(n + 1, 0);

    for (int i = 0; i < n; i++) {
        pref[i + 1] = pref[i] + a[i];
    }

    // Sum of range [1, 3] (indices 1, 2, 3) -> 2+3+4 = 9
    int L = 1, R = 3;
    cout << "Range sum [1, 3]: " << pref[R + 1] - pref[L] << "\n";
}

/**
 * Example: Two Pointers
 * Find if there exist two numbers in a sorted array that sum to 'target'.
 */
bool two_pointers_example(vector<int> &a, int target) {
    int i = 0, j = a.size() - 1;
    while (i < j) {
        int sum = a[i] + a[j];
        if (sum == target) return true;
        if (sum < target) i++;
        else j--;
    }
    return false;
}

int main() {
    prefix_sum_example();
    
    vector<int> sorted_arr = {1, 4, 6, 8, 10};
    if (two_pointers_example(sorted_arr, 14)) {
        cout << "Found sum 14\n";
    }

    return 0;
}
