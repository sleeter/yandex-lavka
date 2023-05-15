package ru.yandex.yandexlavka.mappers;

import org.springframework.stereotype.Component;
import ru.yandex.yandexlavka.courier.CourierDto;
import ru.yandex.yandexlavka.entity.CourierEntity;
/**
 * A class for mapping courier entities to data transfer objects
 */
@Component
public class CourierMapper {
    public CourierDto toDto(CourierEntity entity) {
        return new CourierDto(entity.getCourier_id(), entity.getCourier_type().toString(),
                entity.getRegions(), entity.getWorking_hours());
    }
}
