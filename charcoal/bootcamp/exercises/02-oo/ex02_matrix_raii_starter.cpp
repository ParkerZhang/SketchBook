// Exercise 2: Implement a Matrix class with RAII.
// - Internally store data in a raw heap-allocated buffer (rows*cols doubles).
// - Constructor allocates, destructor releases.
// - Provide operator()(i, j) for element access (0-indexed).
// - Provide rows(), cols() accessors.
// - Implement copy constructor and copy assignment (deep copy).
// Input: rows cols, then rows*cols doubles. Print all elements in row-major order.
#include <iostream>

class Matrix {
public:
    Matrix(int rows, int cols);
    ~Matrix();

    Matrix(const Matrix& other);
    Matrix& operator=(const Matrix& other);

    int rows() const { return rows_; }
    int cols() const { return cols_; }

    double& operator()(int i, int j);
    double  operator()(int i, int j) const;

private:
    int rows_, cols_;
    double* data_;
};

Matrix::Matrix(int rows, int cols) : rows_(rows), cols_(cols) {
    // TODO: allocate data_ as a rows*cols array, zero-initialised
    data_ = nullptr;
}

Matrix::~Matrix() {
    // TODO: release the buffer
}

Matrix::Matrix(const Matrix& other) : rows_(other.rows_), cols_(other.cols_) {
    // TODO: deep copy
    data_ = nullptr;
}

Matrix& Matrix::operator=(const Matrix& other) {
    // TODO: deep copy with self-assignment guard
    return *this;
}

double& Matrix::operator()(int i, int j) {
    return data_[i * cols_ + j];
}

double Matrix::operator()(int i, int j) const {
    return data_[i * cols_ + j];
}

int main() {
    int r, c;
    if (!(std::cin >> r >> c)) return 0;
    Matrix m(r, c);
    for (int i = 0; i < r; ++i)
        for (int j = 0; j < c; ++j)
            std::cin >> m(i, j);

    Matrix copy = m; // exercises copy constructor
    for (int i = 0; i < r; ++i) {
        for (int j = 0; j < c; ++j) {
            if (j) std::cout << ' ';
            std::cout << copy(i, j);
        }
        std::cout << "\n";
    }
}
