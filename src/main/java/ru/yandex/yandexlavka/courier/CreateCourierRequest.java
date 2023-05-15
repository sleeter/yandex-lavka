package ru.yandex.yandexlavka.courier;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * An object that contains a courier request.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Validated
public class CreateCourierRequest {
    @NotEmpty
    private List<@Valid CreateCourierDto> couriers;
}
