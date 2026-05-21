// VaR starter: compute simple historical VaR for a portfolio of returns
#include <iostream>
#include <vector>

int main(){
    // Read N followed by N returns (one per line)
    int N; if(!(std::cin>>N)) return 0;
    std::vector<double> r(N);
    for(int i=0;i<N;++i) std::cin>>r[i];
    // TODO: compute historical VaR at 95%
    std::cout<<"TODO: compute historical VaR\n";
}
