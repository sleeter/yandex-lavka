package ru.yandex.yandexlavka.mappers;

import org.springframework.stereotype.Component;
import ru.yandex.yandexlavka.entity.OrderEntity;
import ru.yandex.yandexlavka.order.OrderDto;
/**
 * A class for mapping order entities to data transfer objects
 */
@Component
public class OrderMapper{

    public OrderDto toDto(OrderEntity entity){
        String completed_time = null;
        if(entity.getComplete_time() != null){
            completed_time = entity.getComplete_time();
        }
        return new OrderDto(entity.getOrder_id(),
                entity.getCost(), entity.getDelivery_hours(),
                entity.getRegions(), entity.getWeight(), completed_time);
    }
}
