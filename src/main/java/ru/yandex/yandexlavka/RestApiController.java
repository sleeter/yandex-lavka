package ru.yandex.yandexlavka;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.yandex.yandexlavka.courier.CourierDto;
import ru.yandex.yandexlavka.courier.CreateCourierRequest;
import ru.yandex.yandexlavka.courier.CreateCouriersResponse;
import ru.yandex.yandexlavka.courier.GetCourierMetaInfoResponse;
import ru.yandex.yandexlavka.courier.GetCouriersResponse;
import ru.yandex.yandexlavka.order.CompleteOrderRequest;
import ru.yandex.yandexlavka.order.CreateOrderRequest;

import java.util.logging.Level;
import ru.yandex.yandexlavka.order.OrderDto;

@RestController
@RequestMapping("/")
@Log
@RequiredArgsConstructor
public class RestApiController {

    private final CourierService courierService;
    private final OrderService orderService;
    private final CourierMetaService courierMetaService;

    @PostMapping("/couriers")
    public CreateCouriersResponse createCourier(
        @RequestBody @Valid CreateCourierRequest createCourierRequest
    ) {
        log.info("Received /couriers request");
        return courierService.insertCouriers(createCourierRequest);
    }

    @GetMapping("/couriers/{courier_id}")
    public CourierDto getCourierById(@PathVariable("courier_id") @Min(1) long courierId) {
        log.log(Level.INFO, "Received /couriers/{0} request", courierId);
        return courierService.getCourierById(courierId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/couriers")
    public GetCouriersResponse getCouriers(
        @RequestParam(value = "limit", defaultValue = "1") @Min(1) Integer limit,
        @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset
    ) {
        log.info("Received /couriers request");
        return courierService.getCouriers(limit, offset);
    }

    @PostMapping("/orders")
    public List<OrderDto> createOrder(@RequestBody @Valid CreateOrderRequest createOrderRequest) {
        log.info("Received /orders request");
        return orderService.insertOrders(createOrderRequest);
    }

    @GetMapping("/orders/{order_id}")
    public OrderDto getOrder(@PathVariable("order_id") @Min(1) long orderId) {
        log.log(Level.INFO, "Received /orders/{0} request", orderId);
        return orderService.getOrderById(orderId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/orders")
    public List<OrderDto> getOrders(
        @RequestParam(value = "limit", defaultValue = "1") @Min(1) Integer limit,
        @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset
    ) {
        log.info("Received /orders request");
        return orderService.getOrders(limit, offset);
    }

    @PostMapping("/orders/complete")
    public List<OrderDto> completeOrder(
        @RequestBody @Valid CompleteOrderRequest completeOrderRequest) {
        log.info("Received /orders/complete request");
        return orderService.completeOrders(completeOrderRequest);
    }

    @GetMapping("/couriers/meta-info/{courier_id}")
    public GetCourierMetaInfoResponse getCourierMetaInfo(
        @PathVariable("courier_id") long courierId,
        @RequestParam("start_date") String startDate,
        @RequestParam("end_date")  String endDate
    ) {
        log.log(Level.INFO, "Received /couriers/meta-info/{0}?start_date={1}&end_date={2} request", new Object[]{courierId, startDate, endDate});
        return courierMetaService.getCourierMetaInfo(courierId, startDate, endDate);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException constraintViolationException){
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){
        return ResponseEntity.badRequest().build();
    }
}
