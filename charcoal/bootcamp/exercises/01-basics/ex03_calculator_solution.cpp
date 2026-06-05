#include <iostream>

int add(int a, int b) { return a + b; }
int sub(int a, int b) { return a - b; }
int mul(int a, int b) { return a * b; }

int div_safe(int a, int b, bool& err) {
    if (b == 0) {
        err = true;
        return 0;
    }
    err = false;
    return a / b;
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
