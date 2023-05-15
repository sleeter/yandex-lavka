package ru.yandex.yandexlavka.order;
import jakarta.validation.Valid;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * An object that contains completed orders
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Validated
public class CompleteOrderRequest {
    private List<@Valid CompleteOrderRequestPart> complete_info;
}
