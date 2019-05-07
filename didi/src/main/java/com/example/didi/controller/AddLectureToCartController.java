package com.example.didi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import com.example.didi.domain.Cart;
import com.example.didi.domain.Lecture;
import com.example.didi.service.DidiFacade;

@Controller
@SessionAttributes("sessionCart")
public class AddLectureToCartController { 

	private DidiFacade didi;

	@Autowired
	public void setDidi(DidiFacade didi) {
		this.didi = didi;
	}

	@ModelAttribute("sessionCart")
	public Cart createCart() {
		return new Cart();
	}
	
	@RequestMapping("/dd/addLectureToCart.do")
	public ModelAndView handleRequest(
			@RequestParam("LectureId") String LectureId,
			@ModelAttribute("sessionCart") Cart cart 
			) throws Exception {
		if (cart.containsItemId(LectureId)) {
			cart.incrementQuantityByItemId(LectureId);
		}
		else {
			boolean isInStock = this.didi.isLectureInStock(LectureId);
			Lecture lecture = this.didi.getLecture(LectureId);
			cart.addlecture(lecture, isInStock);
		}
		return new ModelAndView("Cart", "cart", cart);
	}
}