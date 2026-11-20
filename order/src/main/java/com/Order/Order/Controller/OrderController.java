package com.Order.Order.Controller;

import com.Base.Base.Dto.OrderEventDto;
import com.Order.Order.Dto.OrderDto;
import com.Order.Order.Kafka.OrderProducer;
import com.Order.Order.Services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderServices orderServices;

    public OrderController(OrderServices orderServices) {
        this.orderServices = orderServices;
    }

    @Autowired
    private OrderProducer orderProducer;

    @PostMapping("/add")
    public ResponseEntity<String> addOrder(@RequestBody OrderDto orderDto) {

        OrderEventDto orderEventDto = new OrderEventDto();
        orderEventDto.setMessage("Order is Commited");
        orderEventDto.setStatus("pending");
        orderProducer.sendMessage(orderEventDto);
        String response = orderServices.addOrder(orderDto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> orders = orderServices.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable long id) {
        OrderDto orderDto = orderServices.getOrderById(id);
        return ResponseEntity.ok(orderDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateOrder(@PathVariable long id, @RequestBody OrderDto orderDto) {
        String response = orderServices.updateOrder(id, orderDto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable long id) {
        String response = orderServices.deleteOrder(id);
        return ResponseEntity.ok(response);
    }
}