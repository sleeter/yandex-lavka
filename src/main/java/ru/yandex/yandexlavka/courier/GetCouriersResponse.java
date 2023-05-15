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
public class GetCouriersResponse {
    private List<CourierDto> couriers;
    private int limit;
    private int offset;
}
