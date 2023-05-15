package ru.yandex.yandexlavka.repository;

import org.springframework.data.repository.CrudRepository;
import ru.yandex.yandexlavka.entity.CompleteOrderEntity;

import java.util.List;
import java.util.Optional;

public interface CompleteOrderRepository extends CrudRepository<CompleteOrderEntity, Long> {
    Optional<CompleteOrderEntity> findCompletedOrderEntityByOrderId(Long orderId);
    List<CompleteOrderEntity> findCompleteOrderEntitiesByCourierId(Long courierId);
}
