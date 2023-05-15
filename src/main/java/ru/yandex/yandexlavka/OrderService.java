package ru.yandex.yandexlavka;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.yandexlavka.entity.CompleteOrderEntity;
import ru.yandex.yandexlavka.entity.OrderEntity;
import ru.yandex.yandexlavka.mappers.OrderMapper;
import ru.yandex.yandexlavka.order.*;
import ru.yandex.yandexlavka.repository.CompleteOrderRepository;
import ru.yandex.yandexlavka.repository.OrderRepository;

import java.util.*;

@Service
@Log
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CompleteOrderRepository completeOrderRepository;

    @Transactional
    public List<OrderDto> insertOrders(CreateOrderRequest createOrderRequest) {
        List<CreateOrderDto> createOrderDtos = createOrderRequest.getOrders();
        var orderEntities = createOrderDtos.stream()
            .map(dto -> {
                var ent = new OrderEntity();
                ent.setCost(dto.getCost());
                ent.setWeight(dto.getWeight());
                ent.setRegions(dto.getRegions());
                return ent;
            })
            .toList();
        orderRepository.saveAll(orderEntities);
        return orderEntities.stream().map(orderMapper::toDto).toList();
    }

    public Optional<OrderDto> getOrderById(long orderId) {
        return orderRepository
            .findById(orderId)
            .map(orderMapper::toDto);
    }

    @Transactional
    public List<OrderDto> getOrders(int limit, int offset) {
        Page<OrderEntity> page = orderRepository.findAll(PageRequest.of(offset, limit));
        return page.get().map(orderMapper::toDto).toList();
    }

    /**
     * @throws ResponseStatusException BAD_REQUEST if data is not valid
     */
    @Transactional
    public List<OrderDto> completeOrders(CompleteOrderRequest completeOrderRequest) {
        List<CompleteOrderRequestPart> completeOrderRequestParts = completeOrderRequest.getComplete_info();
        if(completeOrderRequestParts.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        List<OrderDto> orderDtos = new ArrayList<>();
        for(CompleteOrderRequestPart completeOrderRequestPart : completeOrderRequestParts) {

            CompleteOrderEntity completeOrderEntity = completeOrderRepository
                .findCompletedOrderEntityByOrderId(completeOrderRequestPart.getOrder_id())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

            if (!completeOrderRequestPart.getCourier_id().equals(completeOrderEntity.getCourierId())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            OrderEntity orderEntity = orderRepository
                .findById(completeOrderEntity.getOrderId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

            orderEntity.setComplete_time(completeOrderRequestPart.getComplete_time());
            orderDtos.add(orderMapper.toDto(orderEntity));
        }
        return orderDtos;
    }
}
