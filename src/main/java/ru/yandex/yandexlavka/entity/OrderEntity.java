package ru.yandex.yandexlavka.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.LazyGroup;

import java.util.List;

/**
 * An entity for imported data of orders
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name="orders")
public class OrderEntity {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long order_id;
    @Column(name = "cost")
    private int cost;
    @Column(name = "delivery_hours")
    @ElementCollection
    private List<String> delivery_hours;
    @Column(name = "regions")
    private int regions;
    @Column(name = "weight")
    private float weight;
    @Column(name = "complete_time")
    private String complete_time;
}
