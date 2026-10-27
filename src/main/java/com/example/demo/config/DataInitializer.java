package com.example.demo.config;

import com.example.demo.model.Category;
import com.example.demo.model.Product;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(CategoryRepository categoryRepository, ProductRepository productRepository) {
        return args -> {
            // TỰ ĐỘNG XÓA SẠCH DỮ LIỆU CŨ TRƯỚC KHI THÊM MỚI (Tránh lỗi khóa ngoại, xóa sản phẩm trước rồi đến danh mục)
            productRepository.deleteAll();
            categoryRepository.deleteAll();

            // 1. Tạo các danh mục
            Category iphone = new Category();
            iphone.setName("iPhone");
            iphone.setDescription("Điện thoại Apple chính hãng");
            categoryRepository.save(iphone);

            Category samsung = new Category();
            samsung.setName("Samsung");
            samsung.setDescription("Dòng điện thoại Galaxy cao cấp");
            categoryRepository.save(samsung);

            Category accessory = new Category();
            accessory.setName("Phụ kiện");
            accessory.setDescription("Tai nghe, củ sạc, cáp chính hãng");
            categoryRepository.save(accessory);

            // 2. Thêm danh sách toàn bộ sản phẩm mẫu
            Product p1 = new Product();
            p1.setName("iPhone 15 Pro Max 256GB");
            p1.setBrand("Apple");
            p1.setPrice(29990000.0);
            p1.setStockQuantity(15);
            p1.setImageUrl("https://images.unsplash.com/photo-1695048133142-1a20484d2569?auto=format&fit=crop&w=500&q=80");
            p1.setDescription("Titan tự nhiên, chip A17 Pro.");
            p1.setCategory(iphone);
            productRepository.save(p1);

            Product p2 = new Product();
            p2.setName("Samsung Galaxy S24 Ultra 5G");
            p2.setBrand("Samsung");
            p2.setPrice(26990000.0);
            p2.setStockQuantity(20);
            p2.setImageUrl("https://images.unsplash.com/photo-1610945265064-0e34e5519bbf?auto=format&fit=crop&w=500&q=80");
            p2.setDescription("Tích hợp Galaxy AI toàn diện, bút S-Pen.");
            p2.setCategory(samsung);
            productRepository.save(p2);

            Product p3 = new Product();
            p3.setName("Tai nghe AirPods Pro 2");
            p3.setBrand("Apple");
            p3.setPrice(5990000.0);
            p3.setStockQuantity(30);
            p3.setImageUrl("https://images.unsplash.com/photo-1600294037681-c80b4cb5b434?auto=format&fit=crop&w=500&q=80");
            p3.setDescription("Chống ồn chủ động ANC nâng cấp.");
            p3.setCategory(accessory);
            productRepository.save(p3);

            Product p4 = new Product();
            p4.setName("iPhone 14 128GB");
            p4.setBrand("Apple");
            p4.setPrice(18990000.0);
            p4.setStockQuantity(25);
            p4.setImageUrl("https://images.unsplash.com/photo-1591337676887-a217a6970a8a?auto=format&fit=crop&w=500&q=80");
            p4.setDescription("Thiết kế sang trọng, camera kép sắc nét.");
            p4.setCategory(iphone);
            productRepository.save(p4);

            Product p5 = new Product();
            p5.setName("Samsung Galaxy Z Flip5 256GB");
            p5.setBrand("Samsung");
            p5.setPrice(21990000.0);
            p5.setStockQuantity(12);
            p5.setImageUrl("https://images.unsplash.com/photo-1580910051074-3eb694886505?auto=format&fit=crop&w=500&q=80");
            p5.setDescription("Màn hình phụ Flex Window lớn, gập mở linh hoạt.");
            p5.setCategory(samsung);
            productRepository.save(p5);

            Product p6 = new Product();
            p6.setName("Sạc nhanh Apple 20W Type-C");
            p6.setBrand("Apple");
            p6.setPrice(520000.0);
            p6.setStockQuantity(50);
            p6.setImageUrl("https://images.unsplash.com/photo-1583863788434-e58a36330cf0?auto=format&fit=crop&w=500&q=80");
            p6.setDescription("Củ sạc chính hãng tốc độ cao cho iPhone.");
            p6.setCategory(accessory);
            productRepository.save(p6);

            Product p7 = new Product();
            p7.setName("Samsung Galaxy A54 5G");
            p7.setBrand("Samsung");
            p7.setPrice(8490000.0);
            p7.setStockQuantity(18);
            p7.setImageUrl("https://images.unsplash.com/photo-1546868871-7041f2a55e12?auto=format&fit=crop&w=500&q=80");
            p7.setDescription("Màn hình Super AMOLED 120Hz mượt mà.");
            p7.setCategory(samsung);
            productRepository.save(p7);

            Product p8 = new Product();
            p8.setName("Sạc dự phòng Samsung 10000mAh 25W");
            p8.setBrand("Samsung");
            p8.setPrice(790000.0);
            p8.setStockQuantity(35);
            p8.setImageUrl("https://images.unsplash.com/photo-1609592424164-500c4c243452?auto=format&fit=crop&w=500&q=80");
            p8.setDescription("Hỗ trợ sạc siêu nhanh chuẩn PD.");
            p8.setCategory(accessory);
            productRepository.save(p8);

            Product p9 = new Product();
            p9.setName("iPhone 13 128GB");
            p9.setBrand("Apple");
            p9.setPrice(14990000.0);
            p9.setStockQuantity(22);
            p9.setImageUrl("https://images.unsplash.com/photo-1632661674596-df8be070a5c5?auto=format&fit=crop&w=500&q=80");
            p9.setDescription("Hiệu năng ổn định với chip A15 Bionic, pin trâu.");
            p9.setCategory(iphone);
            productRepository.save(p9);

            Product p10 = new Product();
            p10.setName("Tai nghe Samsung Galaxy Buds2 Pro");
            p10.setBrand("Samsung");
            p10.setPrice(2990000.0);
            p10.setStockQuantity(28);
            p10.setImageUrl("https://images.unsplash.com/photo-1590658268037-6bf12165a8df?auto=format&fit=crop&w=500&q=80");
            p10.setDescription("Âm thanh Hi-Fi 24-bit, chống ồn chủ động thông minh.");
            p10.setCategory(accessory);
            productRepository.save(p10);
        };
    }
}