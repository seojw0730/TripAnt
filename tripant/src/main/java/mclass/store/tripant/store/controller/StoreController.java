package mclass.store.tripant.store.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;
import mclass.store.tripant.store.domain.ItemEntity;
import mclass.store.tripant.store.model.service.StoreService;


@Controller
@RequiredArgsConstructor
public class StoreController {
	
	private final StoreService storeService; 
	
	// 스토어 페이지
	@GetMapping("/store")
	public ModelAndView store(ModelAndView mv, Principal principal) {
		mv.setViewName("store/home");
		String memEmail;
		if(principal != null) {
			memEmail = principal.getName();
		}else {
			memEmail = "";
		}
		List<ItemEntity> themeList = storeService.themeList(memEmail);
		List<ItemEntity> fontList = storeService.fontList();
		mv.addObject("themeList", themeList);
		mv.addObject("fontList", fontList);
		return mv;
	}
	
	// 장바구니에 담기
	@PostMapping("/store/insert")
	@ResponseBody
	public int storeInsert(@RequestParam List<String> items, Principal principal) {
		int size = items.size();
		List<Map<String, Object>> list = new ArrayList<>();
		String memEmail = principal.getName();
		for(String itemCode : items) {
			Map<String, Object> map = new HashMap<>();
			map.put("itemCode", itemCode);
			map.put("memEmail", memEmail);
			list.add(map);
		}
		int insertNum = storeService.insertItems(memEmail, list);
		int result;
		if(size == insertNum) {
			result = 1;
		}else {
			result = 0;
		}
		return result;
	}
	
	// 장바구니 페이지
	@GetMapping("/store/cart")
	public ModelAndView storeCart(ModelAndView mv, Principal principal) {
		mv.setViewName("store/cart");
		if(principal != null) {
			String memEmail = principal.getName();
			List<Map<String, Object>> map = storeService.cart(memEmail);
			System.out.println(map.size());
			if(map.size() > 0) {
				mv.addObject("cart", map);
			}
		}
		return mv;
	}
	
	@GetMapping("/store/buy")
	public String storeBuy() {
		return "store/buy";
	}
}
