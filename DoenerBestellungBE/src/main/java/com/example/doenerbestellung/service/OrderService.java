package com.example.doenerbestellung.service;

import com.example.doenerbestellung.dto.OrderDTO;
import com.example.doenerbestellung.dto.ProductDTO;
import com.example.doenerbestellung.entity.Order;
import com.example.doenerbestellung.entity.OrderItem;
import com.example.doenerbestellung.entity.Product;
import com.example.doenerbestellung.entity.User;
import com.example.doenerbestellung.repositories.OrderItemRepository;
import com.example.doenerbestellung.repositories.OrderRepository;
import com.example.doenerbestellung.repositories.ProductRepository;
import com.example.doenerbestellung.repositories.ScheduledOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ScheduledOrderRepository scheduledOrderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;


    public Long saveDefaultOrder(List<ProductDTO> productDTOS, String username) {
        // save products as Order
        Order savedOrder = saveOrder(productDTOS.stream().map(ProductDTO::getId).toList());

        // add savedOrder to User as defaultOrder
        User user = this.customUserDetailsService.findUserByUsername(username);
        user.setDefaultOrder(savedOrder);
        this.customUserDetailsService.saveUser(user);
        return savedOrder.getId();
    }

    private Order saveOrder(List<Long> productIds) {
        // create Order
        Order activeOrder = new Order();
        Order finalActiveOrder = this.orderRepository.save(activeOrder);

        // Add Products to Order
        for(Map.Entry<Long, Integer> entry : getIdsMapped_KeyIsId_ValIsQuantity(productIds).entrySet()) {
            Long productId = entry.getKey();
            Integer quantity = entry.getValue();

            Product product = this.productRepository.findById(productId).orElseThrow(
                    () -> new IllegalArgumentException("Product not found with Id: " + productId)
            );

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(finalActiveOrder);
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);

            this.orderItemRepository.save(orderItem);
        }

        return this.orderRepository.findById(finalActiveOrder.getId()).orElseThrow(
                () -> new IllegalArgumentException("Order could not be reloaded by OrderId: " + finalActiveOrder.getId())
        );
    }

    private Map<Long, Integer> getIdsMapped_KeyIsId_ValIsQuantity(List<Long> ids) {
         return ids.stream()
                .collect(Collectors.groupingBy(
                        id -> id,               // key = productId
                        Collectors.summingInt(e -> 1)  // value = Anzahl Vorkommen
                ));
    }

    public List<OrderDTO> order() {
        // alle user holen mit defaultorder /
        // von denen die produkte aus der default order ziehen /
        // neue order erstellen
        // Order braucht flag um sie als Freitagsorder zu zählen
        // Order muss nach leuten gruppiert sein, mit unter tabelle damit diese sie löschen oder anpassen können
        // Drop down von allen benutzern, damit jeweilige paypal andresse eingefügt wird


        List<User> users = this.customUserDetailsService.findAllByDefaultOrderIsNotNull();

        //generates Map with username and corresponding defaultOrder
        Map<String, Order> defaultOrdermap = new HashMap<>();
        users.forEach(user -> {
            defaultOrdermap.put(user.getUsername(), user.getDefaultOrder());
        });


        List<OrderDTO> orders = new ArrayList<>();
        for (Map.Entry<String, Order> entry : defaultOrdermap.entrySet()) {
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setUsername(entry.getKey());
            orderDTO.setProducts(mapToProductDTOList(getProductsFromOrder(entry.getValue())));
            orders.add(orderDTO);
        }

        log.info(orders.toString());

        return orders;
    }


    private ProductDTO mapToProductDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setDescription(product.getDescription());
        productDTO.setCategoryName(product.getCategory().getName());
        return productDTO;
    }

    private List<ProductDTO> mapToProductDTOList(List<Product> product) {
        return product.stream().map(this::mapToProductDTO).toList();
    }


    private List<Product> getProductsFromOrder(Order order) {
        List<Product> products = new ArrayList<>();

        order.getItems().forEach(item -> {
            for (int i = 0; i < item.getQuantity(); i++) {
                products.add(item.getProduct());
            }
        });

        return products;
    }
}
