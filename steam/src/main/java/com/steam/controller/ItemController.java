package com.steam.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.steam.bean.ItemInfo;
import com.steam.bean.SysDict;
import com.steam.bean.itemInfoVo;
import com.steam.service.DictService;
import com.steam.service.ItemService;
import com.steam.utils.Utils;

/**
 * 游戏相关 Controller
 * @author ASUS
 *
 */

@Controller
@RequestMapping("/admin/items")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private DictService dictService;
	
	@Value("${dict.tagids}")
	private String dict_type_tagids;
	
	@Value("${dict.platform}")
	private String dict_type_platform;
	
	@Value("${dict.flag}")
	private String dict_type_flag;
	
	//后台显示页面
	@RequestMapping("")
	public String list(Model model,itemInfoVo vo){
		List<ItemInfo> itemList = itemService.selectItemInfoByVo(vo);
		List<SysDict> tagidsList = itemService.selectSysDicByTypeId(dict_type_tagids);
		List<SysDict> platformList = itemService.selectSysDicByTypeId(dict_type_platform);
		List<SysDict> flagList = itemService.selectSysDicByTypeId(dict_type_flag);
		model.addAttribute("itemList", itemList);
		model.addAttribute("tagidsList", tagidsList);
		model.addAttribute("platformList", platformList);
		model.addAttribute("flagList", flagList);
		
		//将vo保存到model中
		model.addAttribute("itemVo", vo);
		return "admin/item_list";
	}
	
	//根据词典id 查询返回对应的dict_item_name
	@RequestMapping("/adminTags")
	@ResponseBody
	public List<String> tagName(@RequestBody List<String> idList){
		//查询 用到dicService
		List<String> nameList = dictService.selectTagName(idList);
		return nameList;
	}
	
	//添加游戏
	@RequestMapping("/save")
	@ResponseBody
	public String save(ItemInfo itemInfo, MultipartFile upload_image) throws Exception{
		itemService.save(itemInfo,upload_image);
		return "OK";
	}
	
	//打开编辑窗口
	@RequestMapping("/edit")
	@ResponseBody
	public ItemInfo edit(String id){
		return itemService.selectItemInfoById(id);
	}
	
	//修改游戏
	@RequestMapping("/update")
	@ResponseBody
	public String update(ItemInfo itemInfo, MultipartFile upload_image) throws Exception{
		itemService.update(itemInfo,upload_image);
		return "OK";
	}
	
	//逻辑删除 下架物品
	@RequestMapping("/delete")
	@ResponseBody
	public String delete(String id, Boolean enable){
		itemService.deleteByLogic(id, enable);
		return "OK";
	}
	
	private ItemInfo setItemInfo(ItemInfo itemInfo, MultipartFile upload_image) throws Exception{
		if(upload_image!=null&&!upload_image.getOriginalFilename().equals("")){
			//处理图片
			//文件名
			String name = System.currentTimeMillis()+"";
			//拓展名
			String extName = FilenameUtils.getExtension(upload_image.getOriginalFilename());
			//路径
			String path = "F:\\images\\";
			//拼接成文件名
			String fileName = name+"."+extName;
			//保存
			upload_image.transferTo(new File(path+fileName));
			//保存文件名称到itemInfo
			itemInfo.setItem_cap_image(fileName);
		}
		System.out.println(itemInfo);
		
		//处理日期日期格式
		String date = Utils.DateConvert(itemInfo.getItem_release_date());
		itemInfo.setItem_release_date(date);
		
		//处理标志位,若为空设置为false
		if(itemInfo.getIs_hot()==null){
			itemInfo.setIs_hot(false);
		}
		if(itemInfo.getIs_hot_sale()==null){
			itemInfo.setIs_hot_sale(false);
		}
		if(itemInfo.getIs_free()==null){
			itemInfo.setIs_free(false);
		}
		if(itemInfo.getIs_specials()==null){
			itemInfo.setIs_specials(false);
		}
		if(itemInfo.getIs_upcoming()==null){
			itemInfo.setIs_upcoming(false);
		}
		if(itemInfo.getIs_new()==null){
			itemInfo.setIs_new(false);
		}
		if(itemInfo.getIs_enable()==null){
			itemInfo.setIs_enable(false);
		}
		return itemInfo;
	}
}
