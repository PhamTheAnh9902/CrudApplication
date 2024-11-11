package com.backend.crud.controller;

import com.backend.crud.model.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("product")
public class CrudController {
    public static final List<Product> productList = new ArrayList<>();
    //form
    @GetMapping("product-form")
    public String viewform(Model model){
        model.addAttribute("PRODUCT",new Product());
        model.addAttribute("ACTION","product-form");
        return"product-form";
    }

    // add product
    @PostMapping("product-form")
    public String add(@RequestParam(value = "id", defaultValue = "0") String id,
                      @RequestParam(value = "name", defaultValue = "null") String name,
                      @RequestParam(value = "price", defaultValue = "0") Double price, Model model){
        Product product = new Product(id,name,price);
        productList.add(product);
        model.addAttribute("PRODUCT",new Product());
        return"product-form";
    }
    //Trang hien thi ds product
    @GetMapping("product-view")
    public String view(Model model){
        model.addAttribute("PRODUCTS",productList);
        return"display-product";
    }
    // Xoa 1 product
    @GetMapping( "/product-view/{id}")
    public String delete(@PathVariable(value = "id") String id){
        for(int i = 0; i< productList.size();i++){
            if(productList.get(i).getId().equals(id)){
                productList.remove(productList.get(i));
            }
        }
        return"redirect:/product/product-view";
    }
    // Tim kiem product dua vao id
    public Product findProductById(String id){
        for(int i = 0; i< productList.size();i++){
            if(productList.get(i).getId().equalsIgnoreCase(id)){
                return productList.get(i);
            }
        }
        return null;
    }
    //Sua 1 product
    @GetMapping("product-form/{id}")
    public String edit(@PathVariable("id") String id, Model model){
        Product product = findProductById(id);
        model.addAttribute("PRODUCT",product);
        model.addAttribute("ACTION","/product/product-update");
        return"product-form";
    }
    @PostMapping("product-update")
    public String update(@RequestParam(value = "id", defaultValue = "0") String id,
                      @RequestParam(value = "name", defaultValue = "null") String name,
                      @RequestParam(value = "price", defaultValue = "0") Double price){
        for(int i = 0; i<productList.size();i++){
            Product product = productList.get(i);
            if(product.getId().equalsIgnoreCase(id)){
                productList.set(i,new Product(id,name,price));
            }
        }
        return"redirect:/product/product-view";
    }
}
