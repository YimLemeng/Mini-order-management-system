package com.Ordermanagement.OrderManagementSystem.config;

import com.Ordermanagement.OrderManagementSystem.Entity.Category;
import com.Ordermanagement.OrderManagementSystem.Entity.Customer;
import com.Ordermanagement.OrderManagementSystem.Entity.Product;
import com.Ordermanagement.OrderManagementSystem.Repository.CategoryRepository;
import com.Ordermanagement.OrderManagementSystem.Repository.CustomerRepository;
import com.Ordermanagement.OrderManagementSystem.Repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        if (categoryRepository.count() > 0 || customerRepository.count() > 0) {
            return;
        }

        // ១. បង្កើត និង Save Categories
        Category electronics = new Category();
        electronics.setName("Electronics");

        Category accessories = new Category();
        accessories.setName("Accessories");

        Category appliances = new Category();
        appliances.setName("Home Appliances");

        List<Category> savedCategories = categoryRepository.saveAll(List.of(electronics, accessories, appliances));
        Category savedElectronics = savedCategories.get(0);
        Category savedAccessories = savedCategories.get(1);
        Category savedAppliances = savedCategories.get(2);

        // ២. បង្កើត Products ចំនួន ២៥
        List<Product> products = new ArrayList<>();

        products.add(createProduct("iPhone 15 Pro", 1199.00, 15, Set.of(savedElectronics)));
        products.add(createProduct("Samsung Galaxy S24", 999.00, 20, Set.of(savedElectronics)));
        products.add(createProduct("MacBook Pro 16", 2499.00, 10, Set.of(savedElectronics)));
        products.add(createProduct("iPad Air", 599.00, 25, Set.of(savedElectronics)));
        products.add(createProduct("Sony WH-1000XM5 Headphones", 399.00, 30, Set.of(savedElectronics, savedAccessories)));

        products.add(createProduct("Apple Watch Series 9", 399.00, 18, Set.of(savedElectronics, savedAccessories)));
        products.add(createProduct("USB-C Fast Charger 65W", 29.99, 100, Set.of(savedAccessories)));
        products.add(createProduct("Wireless Power Bank 10000mAh", 45.00, 50, Set.of(savedAccessories)));
        products.add(createProduct("Logitech MX Master 3S Mouse", 99.00, 40, Set.of(savedAccessories)));
        products.add(createProduct("Mechanical Gaming Keyboard", 129.50, 35, Set.of(savedElectronics, savedAccessories)));

        products.add(createProduct("Dell UltraSharp 27 Monitor", 450.00, 12, Set.of(savedElectronics)));
        products.add(createProduct("Anker USB-C Hub", 35.00, 80, Set.of(savedAccessories)));
        products.add(createProduct("AirPods Pro 2", 249.00, 45, Set.of(savedElectronics, savedAccessories)));
        products.add(createProduct("Kindle Paperwhite", 139.00, 22, Set.of(savedElectronics)));
        products.add(createProduct("PlayStation 5", 499.00, 8, Set.of(savedElectronics)));

        products.add(createProduct("Nintendo Switch OLED", 349.00, 14, Set.of(savedElectronics)));
        products.add(createProduct("Smart Robot Vacuum", 299.00, 15, Set.of(savedAppliances)));
        products.add(createProduct("Air Fryer XL", 119.00, 25, Set.of(savedAppliances)));
        products.add(createProduct("Electric Coffee Maker", 85.00, 30, Set.of(savedAppliances)));
        products.add(createProduct("4K Smart TV 55 inch", 650.00, 10, Set.of(savedElectronics, savedAppliances)));

        products.add(createProduct("Bluetooth Portable Speaker", 59.00, 60, Set.of(savedElectronics, savedAccessories)));
        products.add(createProduct("External Hard Drive 2TB", 79.00, 40, Set.of(savedAccessories)));
        products.add(createProduct("Webcam 1080p Full HD", 49.00, 35, Set.of(savedAccessories)));
        products.add(createProduct("Standing Desk Converter", 180.00, 8, Set.of(savedAppliances)));
        products.add(createProduct("Ergonomic Office Chair", 220.00, 10, Set.of(savedAppliances)));

        productRepository.saveAll(products);

        // ៣. បង្កើត Customers ចំនួន ២៥ នាក់
        List<Customer> customers = new ArrayList<>();

        customers.add(createCustomer("Sok Dara", "dara@gmail.com", "012345678"));
        customers.add(createCustomer("Keo Sophea", "sophea@gmail.com", "012345679"));
        customers.add(createCustomer("Chan Vanna", "vanna@gmail.com", "012345680"));
        customers.add(createCustomer("Heng Sreyneang", "sreyneang@gmail.com", "012345681"));
        customers.add(createCustomer("Meng Long", "menglong@gmail.com", "012345682"));

        customers.add(createCustomer("Nhem Sophal", "sophal@gmail.com", "012345683"));
        customers.add(createCustomer("Khim Bopha", "bopha@gmail.com", "012345684"));
        customers.add(createCustomer("Rith Chet", "chet@gmail.com", "012345685"));
        customers.add(createCustomer("Ly Sothea", "sothea@gmail.com", "012345686"));
        customers.add(createCustomer("Chea Leakhena", "leakhena@gmail.com", "012345687"));

        customers.add(createCustomer("Tep Monorom", "monorom@gmail.com", "012345688"));
        customers.add(createCustomer("Vong Borin", "borin@gmail.com", "012345689"));
        customers.add(createCustomer("Phan Rathana", "rathana@gmail.com", "012345690"));
        customers.add(createCustomer("San Kimsour", "kimsour@gmail.com", "012345691"));
        customers.add(createCustomer("Ung Pisey", "pisey@gmail.com", "012345692"));

        customers.add(createCustomer("Kov Makara", "makara@gmail.com", "012345693"));
        customers.add(createCustomer("Sin Visal", "visal@gmail.com", "012345694"));
        customers.add(createCustomer("Ouk Chhay", "chhay@gmail.com", "012345695"));
        customers.add(createCustomer("Pich Sreypov", "sreypov@gmail.com", "012345696"));
        customers.add(createCustomer("So Vatanak", "vatanak@gmail.com", "012345697"));

        customers.add(createCustomer("Meas Sovann", "sovann@gmail.com", "012345698"));
        customers.add(createCustomer("Nou Kanha", "kanha@gmail.com", "012345699"));
        customers.add(createCustomer("Ros Samnang", "samnang@gmail.com", "012345700"));
        customers.add(createCustomer("Chhorn Thida", "thida@gmail.com", "012345701"));
        customers.add(createCustomer("Mao Kosal", "kosal@gmail.com", "012345702"));

        customerRepository.saveAll(customers);

        System.out.println("✅ Sample data initialized successfully (25 Products & 25 Customers)!");
    }

    // Helper Method សម្រាប់បង្កើត Product
    private Product createProduct(String name, double price, int stock, Set<Category> categories) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(BigDecimal.valueOf(price));
        product.setStock(stock);
        product.setCategory(new HashSet<>(categories));
        return product;
    }

    // Helper Method សម្រាប់បង្កើត Customer
    private Customer createCustomer(String name, String email, String phone) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhone(phone);
        return customer;
    }
}