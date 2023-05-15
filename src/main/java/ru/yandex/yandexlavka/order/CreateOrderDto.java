package ru.yandex.yandexlavka.order;

import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.validation.annotation.Validated;
import ru.yandex.yandexlavka.validators.WDHours;

import java.util.List;

/**
 * An object that contain data of a order request
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Validated
public class CreateOrderDto {
    @Positive
    private int cost;
    @WDHours
    private List<String> delivery_hours;
    @Positive
    private int regions;
    @Positive
    private float weight;
}
