package com.example.billingservice;

import com.example.billingservice.entities.Bill;
import com.example.billingservice.entities.ProductItem;
import com.example.billingservice.feign.CustomerRestClient;
import com.example.billingservice.feign.ProductItemRestClient;
import com.example.billingservice.model.Customer;
import com.example.billingservice.model.Product;
import com.example.billingservice.repository.BillRepository;
import com.example.billingservice.repository.ProductItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.hateoas.PagedModel;

import java.util.Date;
import java.util.Random;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }
    @Bean
    CommandLineRunner start(BillRepository billRepository,
                            ProductItemRepository productItemRepository,
                            CustomerRestClient customerRestClient,
                            ProductItemRestClient productItemRestClient, RepositoryRestConfiguration restConfiguration
    ){
        return args -> {
            restConfiguration.exposeIdsFor(Bill.class);
            Customer customer=customerRestClient.getCustomerById(1L);
            Bill bill1= billRepository.save(new Bill(null, new Date(), null, customer.getId(), null));
            PagedModel<Product> productPagedModel= productItemRestClient.pageProducts();
            productPagedModel.forEach(p -> {
                ProductItem productItem=new ProductItem();
                productItem.setPrice(p.getPrice());
                productItem.setQuantity(1+ new Random().nextInt(100));
                productItem.setProductID(p.getId());
                productItem.setBill(bill1);
                productItemRepository.save(productItem);

            });
            Customer customer2=customerRestClient.getCustomerById(2L);
            Bill bill2= billRepository.save(new Bill(null, new Date(), null, customer2.getId(), null));
            productPagedModel.forEach(p -> {
                ProductItem productItem=new ProductItem();
                productItem.setPrice(p.getPrice());
                productItem.setQuantity(1+ new Random().nextInt(100));
                productItem.setProductID(p.getId());
                productItem.setBill(bill2);
                productItemRepository.save(productItem);

            });
            Customer customer3=customerRestClient.getCustomerById(3L);
            Bill bill3= billRepository.save(new Bill(null, new Date(), null, customer3.getId(), null));
            productPagedModel.forEach(p -> {
                ProductItem productItem=new ProductItem();
                productItem.setPrice(p.getPrice());
                productItem.setQuantity(1+ new Random().nextInt(100));
                productItem.setProductID(p.getId());
                productItem.setBill(bill3);
                productItemRepository.save(productItem);

            });


        };
    }

}
