package com.demo.controllers;

import javax.servlet.http.HttpServletRequest;
import com.demo.models.Product;
import com.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;

	@GetMapping("")
	public String listProducts (Model model) {
		model.addAttribute("products", productService.getAllProduct());
		return "/product/products";
	}
	
	@GetMapping("/add-product")
	public String addNewProductGet (Model model) {
		Product product = new Product();
		model.addAttribute("product", product);
		return "/product/add-product";
	}
	
	@PostMapping("/add-product")
	public String addNewProductPost (Model model
			, @ModelAttribute(name = "product") Product product) {
		productService.createProduct(product);
		return "redirect:/products";
	}
	
	@GetMapping("/edit-product/{id}")
	public String editProductGet (Model model, 
			@PathVariable(name = "id") Long id) {
		Product product = productService.getProductById(id);
		model.addAttribute("product", product);
		return "/product/edit-product";
	}
	
	@PostMapping("/edit-product/{id}")
	public String editProductPost (Model model, 
			@PathVariable(name = "id") Long id
			, @ModelAttribute(name = "product") Product product) {
		productService.editProduct(id, product);
		return "redirect:/products";
	}
	
	@GetMapping("/delete-product/{id}")
	public String deleteProductGet (Model model
			, @PathVariable(name = "id") Long id) {
		
		Product product = productService.getProductById(id);
		model.addAttribute("product", product);
		return "/product/delete-product";
	}
	
	@PostMapping("/delete-product/{id}")
	public String deleteProductPost (HttpServletRequest request , Model model
			, @PathVariable(name = "id") Long id) {
		
		String choose = request.getParameter("choose");
		if (choose.equals("yes")){
			productService.deleteProduct(productService.getProductById(id));
		}
		return "redirect:/products";
	}
	
}
