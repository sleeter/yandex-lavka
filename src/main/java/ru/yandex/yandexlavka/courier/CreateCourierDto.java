package ru.yandex.yandexlavka.courier;

import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.validation.annotation.Validated;
import ru.yandex.yandexlavka.validators.Regions;
import ru.yandex.yandexlavka.validators.WDHours;

import java.util.List;

/**
 * An object that contain data of a courier request
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Validated
public class CreateCourierDto {

    private CourierType courier_type; // todo test it

    @Regions
    private List<Integer> regions;

    @WDHours
    private List<String> working_hours;
}
