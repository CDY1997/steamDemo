package com.steam.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.steam.bean.ItemInfo;
import com.steam.bean.SysDict;
import com.steam.bean.itemInfoVo;

public interface ItemService {

	//查询全部
	List<ItemInfo> selectAll();

	//根据itemInfoVo查询
	List<ItemInfo> selectItemInfoByVo(itemInfoVo vo);

	List<SysDict> selectSysDicByTypeId(String typeId);

	//新增游戏
	void save(ItemInfo itemInfo, MultipartFile upload_image) throws Exception;

	//根据id查询游戏信息
	ItemInfo selectItemInfoById(String id);

	//更新游戏信息
	void update(ItemInfo itemInfo, MultipartFile upload_image) throws Exception;

	//逻辑删除(下架)
	void deleteByLogic(String id, Boolean enable);

	//根据标志位名称查询列表排序
	List<ItemInfo> selectItemInfoSortByFlag(String name, Integer num);


}
