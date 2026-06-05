// Exercise 1: Move semantics.
// Implement a Buffer class that owns a heap int array of size n.
// - Implement copy constructor (deep copy) and copy assignment.
// - Implement move constructor and move assignment that steal the buffer
//   and leave the source in a valid empty state (n_ = 0, data_ = nullptr).
// - Add a print() method that prints "size=<n> data=<sum of elements>".
// In main, create a Buffer, move-construct from it, and print both.
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
    int  n_ = 0;
    int* data_ = nullptr;
};

// TODO: implement copy ctor / assignment with deep copy
Buffer::Buffer(const Buffer& o) : n_(0), data_(nullptr) { /* TODO */ }
Buffer& Buffer::operator=(const Buffer& o) { /* TODO */ return *this; }

// TODO: implement move ctor / assignment that steal
Buffer::Buffer(Buffer&& o) noexcept : n_(0), data_(nullptr) { /* TODO */ }
Buffer& Buffer::operator=(Buffer&& o) noexcept { /* TODO */ return *this; }

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
    Buffer b = std::move(a);     // move-construct
    a.print("a");
    b.print("b");
}
