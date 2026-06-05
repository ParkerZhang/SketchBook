#include <iostream>
#include <utility>

class Buffer {
public:
    Buffer() : n_(0), data_(nullptr) {}
    explicit Buffer(int n) : n_(n), data_(n ? new int[n] : nullptr) {
        for (int i = 0; i < n_; ++i) data_[i] = i + 1;
    }
    ~Buffer() { delete[] data_; }

    Buffer(const Buffer& o);
    Buffer(Buffer&& o) noexcept;
    Buffer& operator=(const Buffer& o);
    Buffer& operator=(Buffer&& o) noexcept;

    int  size() const { return n_; }
    long sum() const;
    void print(const std::string& tag) const;

private:
    int n_;
    int* data_;
};

Buffer::Buffer(const Buffer& o) : n_(o.n_), data_(o.n_ ? new int[o.n_] : nullptr) {
    for (int i = 0; i < n_; ++i) data_[i] = o.data_[i];
}

Buffer& Buffer::operator=(const Buffer& o) {
    if (this != &o) {
        int* nd = o.n_ ? new int[o.n_] : nullptr;
        for (int i = 0; i < o.n_; ++i) nd[i] = o.data_[i];
        delete[] data_;
        data_ = nd;
        n_ = o.n_;
    }
    return *this;
}

Buffer::Buffer(Buffer&& o) noexcept : n_(o.n_), data_(o.data_) {
    o.n_ = 0;
    o.data_ = nullptr;
}

Buffer& Buffer::operator=(Buffer&& o) noexcept {
    if (this != &o) {
        delete[] data_;
        n_ = o.n_;
        data_ = o.data_;
        o.n_ = 0;
        o.data_ = nullptr;
    }
    return *this;
}

long Buffer::sum() const {
    long s = 0;
    for (int i = 0; i < n_; ++i) s += data_[i];
    return s;
}

void Buffer::print(const std::string& tag) const {
    std::cout << tag << ": size=" << n_ << " sum=" << sum() << "\n";
}

int main() {
    Buffer a(5);
    Buffer b = std::move(a);
    a.print("a");
    b.print("b");
}
