package com.steam.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.steam.bean.ItemInfo;
import com.steam.bean.SysDict;
import com.steam.bean.itemInfoVo;

public interface ItemMapper {

	//查询全部
	List<ItemInfo> selectAll();

	//根据vo查询
	List<ItemInfo> selectItemInfoByVo(itemInfoVo vo);

	//根据词典类型id获取词典对象列表
	List<SysDict> selectSysDicByTypeId(String typeId);

	//新增游戏
	void save(ItemInfo itemInfo);

	//根据商品id查询商品信息
	ItemInfo selectItemInfoById(String id);

	//更新游戏信息
	void update(ItemInfo itemInfo);

	//逻辑删除(下架)
	void deleteByLogic(@Param("id")String id, @Param("enable")Boolean enable);

	//根据标志位名称查询列表排序
	List<ItemInfo> selectItemInfoSortByFlag(@Param("name")String name, @Param("num")Integer num);

}
