package com.example.doenerbestellung.controller;

import com.example.doenerbestellung.dto.ProductDTO;
import com.example.doenerbestellung.dto.UserDTO;
import com.example.doenerbestellung.entity.Product;
import com.example.doenerbestellung.repositories.ProductRepository;
import com.example.doenerbestellung.service.AuthService;
import com.example.doenerbestellung.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;


    @RequestMapping(method = RequestMethod.GET, value = "/products")
    public List<ProductDTO> product() {
        return this.productService.findAll();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/products/{username}/defaultOrder")
    public ResponseEntity<?> saveDefaultOrder(@RequestBody List<ProductDTO> productDTOS, @PathVariable String username) {
        return ResponseEntity.ok(this.productService.saveDefaultOrder(productDTOS, username));
    }
}
