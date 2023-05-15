package ru.yandex.yandexlavka.order;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.validation.annotation.Validated;

/**
 * An object that contains data of completed orders
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Validated
public class CompleteOrderRequestPart {
    @Positive
    private Long courier_id;
    @Positive
    private Long order_id;
    @Pattern(regexp = "^([0-9][0-9][0-9][0-9])-(0[0-9]|1[0-2])-([0-2][0-9]|3[0-1])T([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9]).([0-9][0-9][0-9])Z$")
    private String complete_time;
}
