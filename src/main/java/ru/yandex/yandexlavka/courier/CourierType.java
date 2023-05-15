package ru.yandex.yandexlavka.courier;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * A type of courier
 */
@RequiredArgsConstructor
@Getter
public enum CourierType {
    FOOT(1),
    BIKE(2),
    AUTO(3);

    private final int maxRegions;
}
