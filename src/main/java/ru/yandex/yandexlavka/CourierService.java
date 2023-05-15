package ru.yandex.yandexlavka;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.yandexlavka.courier.CourierDto;
import ru.yandex.yandexlavka.courier.CourierType;
import ru.yandex.yandexlavka.courier.CreateCourierDto;
import ru.yandex.yandexlavka.courier.CreateCourierRequest;
import ru.yandex.yandexlavka.courier.CreateCouriersResponse;
import ru.yandex.yandexlavka.courier.GetCouriersResponse;
import ru.yandex.yandexlavka.entity.CourierEntity;
import ru.yandex.yandexlavka.mappers.CourierMapper;
import ru.yandex.yandexlavka.repository.CourierRepository;

@Service
@Log
@RequiredArgsConstructor
public class CourierService {

    private final CourierRepository courierRepository;
    private final CourierMapper courierMapper;

    /**
     * @throws ResponseStatusException BAD_REQUEST if data is not valid
     */
    @Transactional
    public CreateCouriersResponse insertCouriers(CreateCourierRequest createCourierRequest) {
        List<CreateCourierDto> createCourierDtos = createCourierRequest.getCouriers();
        List<CourierEntity> courierEntities = new ArrayList<>();
        for (CreateCourierDto createCourierDto : createCourierDtos) {
            CourierEntity entity = new CourierEntity();
            CourierType courierType = createCourierDto.getCourier_type();
            entity.setCourier_type(courierType);
            if(createCourierDto.getRegions().size() > courierType.getMaxRegions()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            entity.setRegions(createCourierDto.getRegions());
            entity.setWorking_hours(createCourierDto.getWorking_hours());
            courierEntities.add(entity);
        }
        courierRepository.saveAll(courierEntities);
        var courierDtos = courierEntities.stream()
            .map(courierMapper::toDto)
            .toList();
        return new CreateCouriersResponse(courierDtos);
    }

    public Optional<CourierDto> getCourierById(long courierId) {
        return courierRepository
            .findById(courierId)
            .map(courierMapper::toDto);
    }

    public GetCouriersResponse getCouriers(int limit, int offset) {
        Page<CourierEntity> page = courierRepository.findAll(PageRequest.of(offset, limit));
        var couriers = page.get()
            .map(courierMapper::toDto)
            .toList();
        return new GetCouriersResponse(couriers, limit, offset);
    }
}
