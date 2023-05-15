package ru.yandex.yandexlavka.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * An entity for storing data of completed orders
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "completed_order_id")
@Entity
@Table(name="completed_order")
public class CompleteOrderEntity {
    @Id
    @GeneratedValue
    @Column(name = "completed_order_id")
    private Long completedOrderId;
    @Column(name = "courier_id")
    private Long courierId;
    @Column(name = "order_id")
    private Long orderId;
}
