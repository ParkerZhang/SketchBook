#include <iostream>
#include <string>

bool is_palindrome(const std::string& s) {
    int i = 0, j = (int)s.size() - 1;
    while (i < j) {
        if (s[i] != s[j]) return false;
        ++i;
        --j;
    }
    return true;
}

int main() {
    std::string s;
    if (!(std::cin >> s)) return 0;
    std::cout << (is_palindrome(s) ? "YES" : "NO") << "\n";
}
