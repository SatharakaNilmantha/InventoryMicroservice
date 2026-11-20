package com.Order.Order.Services.ServiceIMP;


import com.Inventory.Inventory.Dto.InventoryDto;
import com.Order.Order.Dto.OrderDto;
import com.Order.Order.Entity.Order;
import com.Order.Order.Exception.NotFoundException;
import com.Order.Order.Exception.ServiceCommunicationException;
import com.Order.Order.Repository.OrderRepository;
import com.Order.Order.Services.OrderServices;
import com.product.product.Dto.ProductDto;
import org.modelmapper.ModelMapper;;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;


import java.util.List;

@Service
public class OrderService implements OrderServices {

    private final WebClient inventoryWebClient ;

    private final WebClient productWebClient;


    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    public OrderService( WebClient inventoryWebClient,WebClient productWebClient, OrderRepository orderRepository, ModelMapper modelMapper) {
        this.inventoryWebClient = inventoryWebClient;
        this.productWebClient = productWebClient;
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;

    }

    @Override
    public String addOrder(OrderDto orderDto) {
        String itemId = orderDto.getItemId();

        // 1. Inventory call
        InventoryDto inventoryResponse;
        try {

            inventoryResponse = inventoryWebClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/inventory/{itemId}").build(itemId))
                    .retrieve()
                    .bodyToMono(InventoryDto.class)
                    .block();

        } catch (WebClientResponseException.NotFound e) {
            throw new NotFoundException("Item with ID '" + itemId + "' not found in inventory.");
        } catch (Exception e) {
            throw new ServiceCommunicationException("Inventory service is unavailable", e);
        }

        // 2. Null / stock check (productId ගන්න කලින්)
        if (inventoryResponse == null || inventoryResponse.getQuantity() <= 0) {
            throw new NotFoundException("Item with ID '" + itemId + "' is out of stock.");
        }

        // 3. Product call
        String productId = inventoryResponse.getProductId();
        ProductDto productResponse;
        try {
            productResponse = productWebClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/product/{productId}").build(productId))
                    .retrieve()
                    .bodyToMono(ProductDto.class)
                    .block();

        } catch (WebClientResponseException.NotFound e) {
            throw new NotFoundException("Product with ID '" + productId + "' not found.");
        } catch (Exception e) {
            throw new ServiceCommunicationException("Product service is unavailable", e);
        }

        if (productResponse == null || !productResponse.isForSale()) {
            throw new NotFoundException("Product with ID '" + productId + "' is not for sale.");
        }

        Order order = modelMapper.map(orderDto, Order.class);
        orderRepository.save(order);
        return "Order added successfully";
    }

    @Override
    public List<OrderDto> getAllOrders() {
        List<Order> orders = orderRepository.getAllOrders();
        return modelMapper.map(orders, new TypeToken<List<OrderDto>>() {}.getType());
    }

    @Override
    public OrderDto getOrderById(long id) {
        Order order = orderRepository.getOrderById(id);
        if (order == null) {
            throw new NotFoundException("Order not found with id : " + id);
        }
        return modelMapper.map(order, OrderDto.class);
    }

    @Override
    public String updateOrder(long id, OrderDto orderDto) {
        Order existingOrder = orderRepository.getOrderById(id);
        if (existingOrder == null) {
            throw new NotFoundException("Order not found with id : " + id);
        }

        // orderId removed from update
        existingOrder.setItemId(orderDto.getItemId());
        existingOrder.setOrderDate(orderDto.getOrderDate());
        existingOrder.setAmount(orderDto.getAmount());

        orderRepository.save(existingOrder);
        return "Order updated successfully with id : " + id;
    }

    @Override
    public String deleteOrder(long id) {
        Order order = orderRepository.getOrderById(id);
        if (order == null) {
            throw new NotFoundException("Order not found with id : " + id);
        }
        orderRepository.deleteById(id);
        return "Order deleted successfully with id : " + id;
    }
}