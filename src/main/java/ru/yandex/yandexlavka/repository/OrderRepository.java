package ru.yandex.yandexlavka.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.yandex.yandexlavka.entity.OrderEntity;

public interface OrderRepository extends CrudRepository<OrderEntity, Long>, PagingAndSortingRepository<OrderEntity, Long> {
}
