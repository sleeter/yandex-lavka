package ru.yandex.yandexlavka.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.yandex.yandexlavka.entity.CourierEntity;

public interface CourierRepository extends CrudRepository<CourierEntity, Long>, PagingAndSortingRepository<CourierEntity, Long> {

}
