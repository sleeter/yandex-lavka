package ru.yandex.yandexlavka.courier;

import lombok.*;

import java.util.List;

/**
 * An object that contains a metadata for response to a GET request
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "courier_id")
public class GetCourierMetaInfoResponse {
    private Long courier_id;
    private CourierType courier_type;
    private List<Integer> regions;
    private List<String> working_hours;
    private int rating;
    private int earnings;
}
