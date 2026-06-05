// Exercise 3: ResourceHandle using std::unique_ptr.
// Re-implement the "owns a heap int" wrapper, but use unique_ptr so the
// destructor and copy semantics come for free.
// Disable copy (delete copy ctor/assign) but allow move.
// Provide get() and value().
// Demonstrates "prefer unique_ptr to raw new/delete".
#include <iostream>
#include <memory>
#include <utility>

class ResourceHandle {
public:
    explicit ResourceHandle(int v);
    ResourceHandle(const ResourceHandle&) = delete;
    ResourceHandle& operator=(const ResourceHandle&) = delete;
    ResourceHandle(ResourceHandle&& other) noexcept;
    ResourceHandle& operator=(ResourceHandle&& other) noexcept;

    int  value() const { return *p_; }
    int* get()   const { return p_.get(); }

private:
    std::unique_ptr<int> p_;
};

ResourceHandle::ResourceHandle(int v) : p_(std::make_unique<int>(v)) {}
ResourceHandle::ResourceHandle(ResourceHandle&& other) noexcept = default;
ResourceHandle& ResourceHandle::operator=(ResourceHandle&& other) noexcept = default;

int main() {
    ResourceHandle a(42);
    ResourceHandle b = std::move(a);
    std::cout << b.value() << "\n";
}
