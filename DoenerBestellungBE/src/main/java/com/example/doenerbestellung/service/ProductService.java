package com.example.doenerbestellung.service;

import com.example.doenerbestellung.dto.ProductDTO;
import com.example.doenerbestellung.entity.Product;
import com.example.doenerbestellung.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private OrderService orderService;

    public List<ProductDTO> findAll() {
        List<Product> products = this.productRepository.findAll();
        return products.stream()
                .map(product -> {
                    ProductDTO dto = new ProductDTO();
                    dto.setId(product.getId());
                    dto.setName(product.getName());
                    dto.setDescription(product.getDescription());
                    dto.setPrice(product.getPrice());
                    dto.setCategoryName(product.getCategory().getName());
                    return dto;
                })
                .toList();
    }

    public Map<Object, Object> saveDefaultOrder(List<ProductDTO> productDTOS, String username) {
        Map<Object, Object> model = new HashMap<>();
        try {
            if (!this.userDetailsService.existsByUsername(username)) {
                model.put("errorMessage", "Username" + username + " existiert nicht");
            }
            Long savedOrderId = this.orderService.saveDefaultOrder(productDTOS, username);
            if(savedOrderId != null) {
                model.put("orderId", savedOrderId);
            }
        } catch (Exception e) {
            log.error(String.valueOf(e));
        }
        return model;
    }
}
