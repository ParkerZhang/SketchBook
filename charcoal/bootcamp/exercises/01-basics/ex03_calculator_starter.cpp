// Exercise 3: Implement a small calculator.
// Read two integers a, b and an operator op in {+,-,*,/}.
// Print the result of a op b. Division by zero should print "ERR".
// Use pass-by-reference where appropriate.
#include <iostream>

int add(int a, int b) { return a + b; }
int sub(int a, int b) { return a - b; }
int mul(int a, int b) { return a * b; }

// TODO: implement div_safe that returns 0 and sets an error flag
// when b == 0, otherwise returns a / b.
int div_safe(int a, int b, bool& err) {
    err = false;
    // TODO: handle division by zero
    return 0;
}

int main() {
    int a, b;
    char op;
    std::cin >> a >> op >> b;

    if (op == '+') std::cout << add(a, b) << "\n";
    else if (op == '-') std::cout << sub(a, b) << "\n";
    else if (op == '*') std::cout << mul(a, b) << "\n";
    else if (op == '/') {
        bool err = false;
        std::cout << div_safe(a, b, err) << "\n";
        if (err) std::cout << "ERR\n";
    } else {
        std::cout << "ERR\n";
    }
}
