// Exercise 3: Balanced-parentheses checker using std::stack.
// Read a string consisting of ()[]{}. Print "YES" if the brackets are
// balanced, otherwise "NO".
#include <iostream>
#include <string>
#include <stack>

bool balanced(const std::string& s) {
    // TODO: use a stack<char> and a closing->opening map.
    return false;
}

int main() {
    std::string s;
    if (!(std::cin >> s)) return 0;
    std::cout << (balanced(s) ? "YES" : "NO") << "\n";
}
