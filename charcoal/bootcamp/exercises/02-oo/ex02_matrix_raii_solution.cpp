#include <iostream>
#include <algorithm>

class Matrix {
public:
    Matrix(int rows, int cols);
    ~Matrix();

    Matrix(const Matrix& other);
    Matrix& operator=(const Matrix& other);
    Matrix(Matrix&& other) noexcept;
    Matrix& operator=(Matrix&& other) noexcept;

    int rows() const { return rows_; }
    int cols() const { return cols_; }

    double& operator()(int i, int j);
    double  operator()(int i, int j) const;

private:
    int rows_, cols_;
    double* data_;
};

Matrix::Matrix(int rows, int cols) : rows_(rows), cols_(cols) {
    data_ = new double[rows_ * cols_]();
}

Matrix::~Matrix() {
    delete[] data_;
}

Matrix::Matrix(const Matrix& other) : rows_(other.rows_), cols_(other.cols_) {
    data_ = new double[rows_ * cols_];
    std::copy(other.data_, other.data_ + rows_ * cols_, data_);
}

Matrix& Matrix::operator=(const Matrix& other) {
    if (this != &other) {
        double* nd = new double[other.rows_ * other.cols_];
        std::copy(other.data_, other.data_ + other.rows_ * other.cols_, nd);
        delete[] data_;
        data_ = nd;
        rows_ = other.rows_;
        cols_ = other.cols_;
    }
    return *this;
}

Matrix::Matrix(Matrix&& other) noexcept
    : rows_(other.rows_), cols_(other.cols_), data_(other.data_) {
    other.data_ = nullptr;
    other.rows_ = 0;
    other.cols_ = 0;
}

Matrix& Matrix::operator=(Matrix&& other) noexcept {
    if (this != &other) {
        delete[] data_;
        rows_ = other.rows_;
        cols_ = other.cols_;
        data_ = other.data_;
        other.data_ = nullptr;
        other.rows_ = 0;
        other.cols_ = 0;
    }
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

    Matrix copy = m;
    for (int i = 0; i < r; ++i) {
        for (int j = 0; j < c; ++j) {
            if (j) std::cout << ' ';
            std::cout << copy(i, j);
        }
        std::cout << "\n";
    }
}
