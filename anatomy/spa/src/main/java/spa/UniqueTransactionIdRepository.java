package spa;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UniqueTransactionIdRepository extends CrudRepository<UniqueTransactionId,Long> {
    List <UniqueTransactionId> findBySwapId(Long swapId);
    UniqueTransactionId findById(long id);
}
