package ru.yandex.yandexlavka;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;
import org.springframework.util.MultiValueMap;
import ru.yandex.yandexlavka.courier.CourierType;
import ru.yandex.yandexlavka.courier.CreateCourierDto;
import ru.yandex.yandexlavka.courier.CreateCourierRequest;
import ru.yandex.yandexlavka.order.CompleteOrderRequestPart;
import ru.yandex.yandexlavka.order.CompleteOrderRequest;
import ru.yandex.yandexlavka.order.CreateOrderDto;
import ru.yandex.yandexlavka.order.CreateOrderRequest;
import ru.yandex.yandexlavka.repository.CompleteOrderRepository;
import ru.yandex.yandexlavka.repository.CourierRepository;
import ru.yandex.yandexlavka.repository.OrderRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ImportTests {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private CourierRepository courierRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CompleteOrderRepository completeOrderRepository;
    private ResponseEntity<Object> testInvalidRequest(CreateCourierRequest request) {
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<CreateCourierRequest> requestEntity = new HttpEntity<>(request, headers);
        ResponseEntity<Object> response = restTemplate.exchange("/couriers", HttpMethod.POST, requestEntity, Object.class, new HashMap<String, String>());
        Assert.isTrue(response.getStatusCode() == HttpStatus.BAD_REQUEST, "Status should be 400");
        return response;
    }
    private  ResponseEntity<Object> testInvalidRequest(CreateOrderRequest request) {
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<CreateOrderRequest> requestEntity = new HttpEntity<>(request, headers);
        ResponseEntity<Object> response = restTemplate.exchange("/orders", HttpMethod.POST, requestEntity, Object.class, new HashMap<String, String>());
        Assert.isTrue(response.getStatusCode() == HttpStatus.BAD_REQUEST, "Status should be 400");
        return response;
    }
    private  ResponseEntity<Object> testInvalidRequest(CompleteOrderRequest request) {
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<CompleteOrderRequest> requestEntity = new HttpEntity<>(request, headers);
        ResponseEntity<Object> response = restTemplate.exchange("/orders/complete", HttpMethod.POST, requestEntity, Object.class, new HashMap<String, String>());
        Assert.isTrue(response.getStatusCode() == HttpStatus.BAD_REQUEST, "Status should be 400");
        return response;
    }
    private  ResponseEntity<Object> testSuccessfulInsetion(CreateCourierRequest request) {
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<CreateCourierRequest> requestEntity = new HttpEntity<>(request, headers);
        ResponseEntity<Object> response = restTemplate.exchange("/couriers", HttpMethod.POST, requestEntity, Object.class, new HashMap<String, String>());
        Assert.isTrue(response.getStatusCode() == HttpStatus.OK, "Status code should be OK - 200");
        return response;
    }
    private  ResponseEntity<Object> testSuccessfulInsetion(CreateOrderRequest request) {
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<CreateOrderRequest> requestEntity = new HttpEntity<>(request, headers);
        ResponseEntity<Object> response = restTemplate.exchange("/orders", HttpMethod.POST, requestEntity, Object.class, new HashMap<String, String>());
        Assert.isTrue(response.getStatusCode() == HttpStatus.OK, "Status code should be OK - 200");
        return response;
    }
    /*private  ResponseEntity<Object> testSuccessfulInsetion(CompleteOrderRequestDto request) {
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        HttpEntity<CompleteOrderRequestDto> requestEntity = new HttpEntity<>(request, headers);
        ResponseEntity<Object> response = restTemplate.exchange("/orders/complete", HttpMethod.POST, requestEntity, Object.class, new HashMap<String, String>());
        Assert.isTrue(response.getStatusCode() == HttpStatus.OK, "Status code should be OK - 200");
        return response;
    }*/
    @AfterEach
    public void clearDb() {
        courierRepository.deleteAll();
        orderRepository.deleteAll();
        completeOrderRepository.deleteAll();
    }
    @Test
    public void shouldNotBeOkOnEmptyCourierRequest() {
        List<CreateCourierDto> couriers = new ArrayList<>();
        CreateCourierRequest request = new CreateCourierRequest(couriers);
        testInvalidRequest(request);
    }
    @Test
    public void regionsCourierShouldBePositive() {
        List<CreateCourierDto> couriers = new ArrayList<>();
        List<Integer> regions = new ArrayList<>();
        regions.add(-1);
        List<String> working_hours = new ArrayList<>();
        working_hours.add("12:00-13:00");
        couriers.add(new CreateCourierDto(CourierType.FOOT, regions, working_hours));
        CreateCourierRequest request = new CreateCourierRequest(couriers);
        testInvalidRequest(request);
    }
    @Test
    public void regionsCourierShouldBeCorrelateWithType() {
        List<CreateCourierDto> couriers = new ArrayList<>();
        List<Integer> regions = new ArrayList<>();
        regions.add(1);
        regions.add(2);
        List<String> working_hours = new ArrayList<>();
        working_hours.add("12:00-13:00");
        couriers.add(new CreateCourierDto(CourierType.FOOT, regions, working_hours));
        CreateCourierRequest request = new CreateCourierRequest(couriers);
        testInvalidRequest(request);
    }
    @Test
    public void workingHoursShouldBeEqualToPattern() {
        List<CreateCourierDto> couriers = new ArrayList<>();
        List<Integer> regions = new ArrayList<>();
        regions.add(1);
        List<String> working_hours = new ArrayList<>();
        working_hours.add("12-00:13-00");
        couriers.add(new CreateCourierDto(CourierType.FOOT, regions, working_hours));
        CreateCourierRequest request = new CreateCourierRequest(couriers);
        testInvalidRequest(request);
    }
    @Test
    public void shouldNotBeOkOnEmptyOrderRequest() {
        List<CreateOrderDto> couriers = new ArrayList<>();
        CreateOrderRequest request = new CreateOrderRequest(couriers);
        testInvalidRequest(request);
    }
    @Test
    public void costShouldBePositive() {
        List<CreateOrderDto> orders = new ArrayList<>();
        int cost = -1;
        List<String> delivery_hours = new ArrayList<>();
        delivery_hours.add("12:00-13:00");
        int regions = 1;
        float weight = 1.1F;
        orders.add(new CreateOrderDto(cost, delivery_hours, regions, weight));
        CreateOrderRequest request = new CreateOrderRequest(orders);
        testInvalidRequest(request);
    }
    @Test
    public void deliveryHoursShouldBeEqualToPattern() {
        List<CreateOrderDto> orders = new ArrayList<>();
        int cost = 1;
        List<String> delivery_hours = new ArrayList<>();
        delivery_hours.add("12-00:13-00");
        int regions = 1;
        float weight = 1.1F;
        orders.add(new CreateOrderDto(cost, delivery_hours, regions, weight));
        CreateOrderRequest request = new CreateOrderRequest(orders);
        testInvalidRequest(request);
    }
    @Test
    public void regionsOrderShouldBePositive() {
        List<CreateOrderDto> orders = new ArrayList<>();
        int cost = 1;
        List<String> delivery_hours = new ArrayList<>();
        delivery_hours.add("12:00-13:00");
        int regions = -1;
        float weight = 1.1F;
        orders.add(new CreateOrderDto(cost, delivery_hours, regions, weight));
        CreateOrderRequest request = new CreateOrderRequest(orders);
        testInvalidRequest(request);
    }
    @Test
    public void weightShouldBePositive() {
        List<CreateOrderDto> orders = new ArrayList<>();
        int cost = 1;
        List<String> delivery_hours = new ArrayList<>();
        delivery_hours.add("12:00-13:00");
        int regions = 1;
        float weight = -1.1F;
        orders.add(new CreateOrderDto(cost, delivery_hours, regions, weight));
        CreateOrderRequest request = new CreateOrderRequest(orders);
        testInvalidRequest(request);
    }
    @Test
    public void shouldNotBeOkOnEmptyCompleteOrderRequest() {
        List<CompleteOrderRequestPart> completeOrderRequestPart = new ArrayList<>();
        CompleteOrderRequest request = new CompleteOrderRequest(completeOrderRequestPart);
        testInvalidRequest(request);
    }
    @Test
    public void courierIdShouldBePositive() {
        List<CompleteOrderRequestPart> completeOrderRequestParts = new ArrayList<>();
        long courier_id = -1L;
        long order_id = 1L;
        String complete_time = "2023-05-15T00:00:00.000Z";
        completeOrderRequestParts.add(new CompleteOrderRequestPart(courier_id, order_id, complete_time));
        CompleteOrderRequest request = new CompleteOrderRequest(completeOrderRequestParts);
        testInvalidRequest(request);
    }
    @Test
    public void orderIdShouldBePositive() {
        List<CompleteOrderRequestPart> completeOrderRequestParts = new ArrayList<>();
        long courier_id = 1L;
        long order_id = -1L;
        String complete_time = "2023-05-15T00:00:00.000Z";
        completeOrderRequestParts.add(new CompleteOrderRequestPart(courier_id, order_id, complete_time));
        CompleteOrderRequest request = new CompleteOrderRequest(completeOrderRequestParts);
        testInvalidRequest(request);
    }
    @Test
    public void completeTimeShouldBeEqualToPattern() {
        List<CompleteOrderRequestPart> completeOrderRequestParts = new ArrayList<>();
        long courier_id = 1L;
        long order_id = 1L;
        String complete_time = "2023:05:15T00:00:00:000Z";
        completeOrderRequestParts.add(new CompleteOrderRequestPart(courier_id, order_id, complete_time));
        CompleteOrderRequest request = new CompleteOrderRequest(completeOrderRequestParts);
        testInvalidRequest(request);
    }
    @Test
    public void sampleAssertions() {
        List<CreateCourierDto> couriers = new ArrayList<>();
        List<Integer> regionsCourier = new ArrayList<>();
        regionsCourier.add(1);
        List<String> working_hours = new ArrayList<>();
        working_hours.add("12:00-13:00");
        couriers.add(new CreateCourierDto(CourierType.FOOT, regionsCourier, working_hours));
        CreateCourierRequest courierRequest = new CreateCourierRequest(couriers);
        testSuccessfulInsetion(courierRequest);

        List<CreateOrderDto> orders = new ArrayList<>();
        int cost = 1;
        List<String> delivery_hours = new ArrayList<>();
        delivery_hours.add("12:00-13:00");
        int regionsOrder = 1;
        float weight = 1.1F;
        orders.add(new CreateOrderDto(cost, delivery_hours, regionsOrder, weight));
        CreateOrderRequest orderRequest = new CreateOrderRequest(orders);
        testSuccessfulInsetion(orderRequest);

        /*List<CompleteOrder> completeOrders = new ArrayList<>();
        long courier_id = 1L;
        long order_id = 1L;
        String complete_time = "2023-05-15T00:00:00.000Z";
        completeOrders.add(new CompleteOrder(courier_id, order_id, complete_time));
        CompleteOrderRequestDto completeOrderRequest = new CompleteOrderRequestDto(completeOrders);
        testSuccessfulInsetion(completeOrderRequest);*/
    }
    @Test
    public void stress() {
        List<CreateCourierDto> couriers = new ArrayList<>();
        List<CreateOrderDto> orders = new ArrayList<>();
        for (int i = 1; i < 51; i++) {
            List<Integer> regionsCourier = new ArrayList<>();
            regionsCourier.add(i);
            List<String> working_hours = new ArrayList<>();
            working_hours.add("12:00-13:00");
            if (i % 3 == 0)
                couriers.add(new CreateCourierDto(CourierType.FOOT, regionsCourier, working_hours));
            else if (i % 3 == 1)
                couriers.add(new CreateCourierDto(CourierType.BIKE, regionsCourier, working_hours));
            else
                couriers.add(new CreateCourierDto(CourierType.AUTO, regionsCourier, working_hours));

            int cost = i;
            List<String> delivery_hours = new ArrayList<>();
            delivery_hours.add("12:00-13:00");
            int regionsOrder = i;
            float weight = 1.1F;
            orders.add(new CreateOrderDto(cost, delivery_hours, regionsOrder, weight));
        }
        CreateCourierRequest courierRequest = new CreateCourierRequest(couriers);
        testSuccessfulInsetion(courierRequest);
        CreateOrderRequest orderRequest = new CreateOrderRequest(orders);
        testSuccessfulInsetion(orderRequest);
    }
}
