package com.steam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.steam.bean.ItemInfo;
import com.steam.service.ItemService;

/**
 * 前台首页
 * @author ASUS
 *
 */
@Controller
@RequestMapping("/index")
public class indexController {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("")
	public String list(Model model){
		List<ItemInfo> itemList = itemService.selectItemInfoByVo(null);
		model.addAttribute("itemList", itemList);
		return "/index";
	}
	
	@RequestMapping("/sort")
	@ResponseBody
	public List<ItemInfo> sort(String name,Integer num){
		List<ItemInfo> itemList = itemService.selectItemInfoSortByFlag(name,num);
		for(ItemInfo item : itemList){
			System.out.println(item);
		}
		System.out.println("");
		System.out.println("");
		return itemList;
	}
	
}
