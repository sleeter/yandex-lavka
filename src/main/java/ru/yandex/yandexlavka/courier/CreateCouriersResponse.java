package ru.yandex.yandexlavka.courier;

import lombok.*;

import java.util.List;

/**
 * An object that contains a data for response to a GET request
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CreateCouriersResponse {
    private List<CourierDto> couriers;
}
