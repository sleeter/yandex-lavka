package ru.yandex.yandexlavka.entity;

import jakarta.persistence.*;
import lombok.*;
import ru.yandex.yandexlavka.courier.CourierType;
import java.util.List;

/**
 * An entity for imported data of couriers
 */
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "courier_id")
@Entity
@Table(name="courier")
public class CourierEntity {
    @Id
    @GeneratedValue
    @Column(name = "courier_id")
    private Long courier_id;
    @Column(name = "courier_type")
    @Enumerated(EnumType.STRING)
    private CourierType courier_type;
    @Column(name = "regions")
    @ElementCollection
    private List<Integer> regions;
    @Column(name = "working_hours")
    @ElementCollection
    private List<String> working_hours;

}
