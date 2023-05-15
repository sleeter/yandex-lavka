package ru.yandex.yandexlavka;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.yandexlavka.courier.CourierType;
import ru.yandex.yandexlavka.courier.GetCourierMetaInfoResponse;
import ru.yandex.yandexlavka.entity.CompleteOrderEntity;
import ru.yandex.yandexlavka.entity.CourierEntity;
import ru.yandex.yandexlavka.entity.OrderEntity;
import ru.yandex.yandexlavka.repository.CompleteOrderRepository;
import ru.yandex.yandexlavka.repository.CourierRepository;
import ru.yandex.yandexlavka.repository.OrderRepository;

@Service
@RequiredArgsConstructor
public class CourierMetaService {

    private final OrderRepository orderRepository;
    private final CourierRepository courierRepository;
    private final CompleteOrderRepository completeOrderRepository;

    /**
     * @throws ResponseStatusException BAD_REQUEST if a data is not valid, NOT_FOUND
     */
    @Transactional(rollbackFor = Exception.class)
    public GetCourierMetaInfoResponse getCourierMetaInfo(
        long courierId,
        String startDate,
        String endDate
    ) {
        List<CompleteOrderEntity> completeOrderEntities = completeOrderRepository
            .findCompleteOrderEntitiesByCourierId(courierId);
        int countOrders = 0;
        int costTotal = 0;
        GetCourierMetaInfoResponse courierMetaInfoResponse = new GetCourierMetaInfoResponse();
        if (completeOrderEntities == null || completeOrderEntities.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        LocalDateTime start_date_format = LocalDateTime.parse(startDate);
        LocalDateTime end_date_format = LocalDateTime.parse(endDate);
        for (CompleteOrderEntity completeOrderEntity : completeOrderEntities) {
            OrderEntity orderEntity = orderRepository
                .findById(completeOrderEntity.getOrderId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
            String complete_time = orderEntity.getComplete_time().substring(0, 10);
            LocalDateTime complete_time_format = LocalDateTime.parse(complete_time);
            if (complete_time_format.isAfter(start_date_format)
                && complete_time_format.isBefore(end_date_format)) {
                costTotal += orderEntity.getCost();
                countOrders++;
            }

        }
        CourierEntity courierEntity = courierRepository.findById(courierId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
        courierMetaInfoResponse.setCourier_id(courierId);
        courierMetaInfoResponse.setRegions(courierEntity.getRegions());
        courierMetaInfoResponse.setWorking_hours(courierEntity.getWorking_hours());
        CourierType courierType = courierEntity.getCourier_type();
        courierMetaInfoResponse.setCourier_type(courierType);
        int countHours = (int) (ChronoUnit.DAYS.between(start_date_format, end_date_format)
            * 24);
        if (courierType.equals(CourierType.FOOT)) {
            courierMetaInfoResponse.setEarnings(costTotal * 2);
            courierMetaInfoResponse.setRating(countOrders * 3 / countHours);
        } else if (courierType.equals(CourierType.BIKE)) {
            courierMetaInfoResponse.setEarnings(costTotal * 3);
            courierMetaInfoResponse.setRating(countOrders * 2 / countHours);
        } else if (courierType.equals(CourierType.AUTO)) {
            courierMetaInfoResponse.setEarnings(costTotal * 4);
            courierMetaInfoResponse.setRating(countOrders / countHours);
        }
        return courierMetaInfoResponse;
    }
}
