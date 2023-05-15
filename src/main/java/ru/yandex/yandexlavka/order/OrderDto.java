package ru.yandex.yandexlavka.order;

import lombok.*;

import java.util.List;

/**
 * A data transfer object of an order.
 * Used as a response to a GET request.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "order_id")
public class OrderDto {
    private Long order_id;
    private int cost;
    private List<String> delivery_hours;
    private int regions;
    private float weight;
    private String completed_time;
}
