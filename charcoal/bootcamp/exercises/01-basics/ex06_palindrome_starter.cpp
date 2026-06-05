// Exercise 6: Read a string (one word, lowercase) and print "YES" if
// it is a palindrome, otherwise "NO". Use two indices.
#include <iostream>
#include <string>

bool is_palindrome(const std::string& s) {
    // TODO
    return false;
}

int main() {
    std::string s;
    if (!(std::cin >> s)) return 0;
    std::cout << (is_palindrome(s) ? "YES" : "NO") << "\n";
}
