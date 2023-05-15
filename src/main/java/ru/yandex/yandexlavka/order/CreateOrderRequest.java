package ru.yandex.yandexlavka.order;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * An object that contains a order request.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Validated
public class CreateOrderRequest {
    @NotEmpty
    private List<@Valid CreateOrderDto> orders;
}
