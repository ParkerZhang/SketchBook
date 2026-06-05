#include <iostream>
#include <string>
#include <stack>
#include <unordered_map>

bool balanced(const std::string& s) {
    std::stack<char> st;
    static const std::unordered_map<char, char> match{
        {')', '('}, {']', '['}, {'}', '{'}
    };
    for (char c : s) {
        if (c == '(' || c == '[' || c == '{') {
            st.push(c);
        } else {
            if (st.empty() || st.top() != match.at(c)) return false;
            st.pop();
        }
    }
    return st.empty();
}

int main() {
    std::string s;
    if (!(std::cin >> s)) return 0;
    std::cout << (balanced(s) ? "YES" : "NO") << "\n";
}
