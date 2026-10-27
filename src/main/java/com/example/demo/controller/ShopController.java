package com.example.demo.controller;

import com.example.demo.model.*;
import com.example.demo.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class ShopController {

    @Autowired private ProductRepository productRepository;
    @Autowired private CategoryRepository categoryRepository;
    @Autowired private OrderRepository orderRepository;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(required = false) String keyword,
                        @RequestParam(required = false) Long categoryId,
                        @RequestParam(required = false) String brand,
                        @RequestParam(required = false) Double minPrice,
                        @RequestParam(required = false) Double maxPrice) {

        List<Product> products;
        if (keyword != null && !keyword.isEmpty()) {
            products = productRepository.findByNameContainingIgnoreCaseOrBrandContainingIgnoreCase(keyword, keyword);
        } else if (categoryId != null || (brand != null && !brand.isEmpty()) || minPrice != null || maxPrice != null) {
            products = productRepository.filterProducts(categoryId, brand, minPrice, maxPrice);
        } else {
            products = productRepository.findAll();
        }

        model.addAttribute("products", products);
        model.addAttribute("categories", categoryRepository.findAll());
        
        // BỔ SUNG DÒNG NÀY ĐỂ GIỮ LẠI TRẠNG THÁI DANH MỤC ĐÃ CHỌN TRÊN GIAO DIỆN
        model.addAttribute("selectedCategoryId", categoryId);

        return "shop/index";
    }

    @PostMapping("/cart/add")
    public String addToCart(@RequestParam Long productId, @RequestParam(defaultValue = "1") int quantity, HttpSession session) {
        Map<Long, CartItem> cart = (Map<Long, CartItem>) session.getAttribute("cart");
        if (cart == null) cart = new HashMap<>();

        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            if (cart.containsKey(productId)) {
                cart.get(productId).setQuantity(cart.get(productId).getQuantity() + quantity);
            } else {
                cart.put(productId, new CartItem(product, quantity));
            }
        }
        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String viewCart(HttpSession session, Model model) {
        Map<Long, CartItem> cart = (Map<Long, CartItem>) session.getAttribute("cart");
        double total = 0;
        if (cart != null) {
            for (CartItem item : cart.values()) total += item.getTotalPrice();
        }
        model.addAttribute("cart", cart != null ? cart.values() : Collections.emptyList());
        model.addAttribute("total", total);
        return "shop/cart";
    }

    @PostMapping("/cart/update")
    public String updateCart(@RequestParam Long productId, @RequestParam int quantity, HttpSession session) {
        Map<Long, CartItem> cart = (Map<Long, CartItem>) session.getAttribute("cart");
        if (cart != null && cart.containsKey(productId)) {
            if (quantity > 0) cart.get(productId).setQuantity(quantity);
            else cart.remove(productId);
            session.setAttribute("cart", cart);
        }
        return "redirect:/cart";
    }

    @GetMapping("/cart/remove/{id}")
    public String removeFromCart(@PathVariable Long id, HttpSession session) {
        Map<Long, CartItem> cart = (Map<Long, CartItem>) session.getAttribute("cart");
        if (cart != null) {
            cart.remove(id);
            session.setAttribute("cart", cart);
        }
        return "redirect:/cart";
    }

    @PostMapping("/checkout")
    public String checkout(HttpSession session) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) return "redirect:/login";

        Map<Long, CartItem> cart = (Map<Long, CartItem>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) return "redirect:/cart";

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setStatus("PENDING");

        double total = 0;
        List<OrderItem> items = new ArrayList<>();
        for (CartItem cartItem : cart.values()) {
            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(cartItem.getProduct());
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(cartItem.getProduct().getPrice());
            items.add(item);
            total += cartItem.getTotalPrice();
        }
        order.setTotalAmount(total);
        order.setOrderItems(items);

        orderRepository.save(order);
        session.removeAttribute("cart");
        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String userOrders(HttpSession session, Model model) {
        User user = (User) session.getAttribute("currentUser");
        if (user == null) return "redirect:/login";

        List<Order> orders = orderRepository.findByUserIdOrderByOrderDateDesc(user.getId());
        model.addAttribute("orders", orders);
        return "shop/orders";
    }
}