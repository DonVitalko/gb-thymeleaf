package ru.gb.gbthymeleaf.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.gb.gbthymeleaf.entity.Cart;
import ru.gb.gbthymeleaf.entity.Product;
import ru.gb.gbthymeleaf.service.CartService;
import ru.gb.gbthymeleaf.service.ProductService;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final Cart cart = Cart.builder().build();
    private final ProductService productService;

    @GetMapping
    public String getCartList(Model model) {
        if (cart.getId() != null) {
            Cart cartFormDB = cartService.findCartsById(cart.getId());
            cart.setProducts(cartFormDB.getProducts());}
        model.addAttribute("cart", cart);
        return "cart";
    }

    @GetMapping("/add")
    public String saveCart(@RequestParam(name = "product_id") Long id) {
        Product product = productService.findById(id);
        List<Product> productList;
        if (cart.getId() == null) {
            productList = new ArrayList<>();
            productList.add(product);
            cart.setProducts(productList);
            Cart cartFromDb = cartService.save(cart);
            cart.setId(cartFromDb.getId());
        } else {
            Cart cartFormDB = cartService.findCartsById(cart.getId());
            productList = cartFormDB.getProducts();
            productList.removeIf(product1 -> product1.getId().equals(product.getId()));
            productList.add(product);
            cart.setProducts(productList);
            cartService.save(cart);
        }
        return "redirect:/product/all";
    }

    @GetMapping("/delete")
    public String deleteById(@RequestParam(name = "id") Long id, @RequestParam(name = "product_id") Long productId) {
        Cart cart = cartService.findCartsById(id);
        cart.getProducts().removeIf(product -> product.getId().equals(productId));
        cartService.save(cart);
        return "redirect:/cart";
    }

}
