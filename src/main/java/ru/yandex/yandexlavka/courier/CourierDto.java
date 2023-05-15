package ru.yandex.yandexlavka.courier;

import lombok.*;

import java.util.List;

/**
 * A data transfer object of a courier.
 * Used as a response to a GET request.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class CourierDto {
    private Long courier_id;
    private String courier_type;
    private List<Integer> regions;
    private List<String> working_hours;
}
