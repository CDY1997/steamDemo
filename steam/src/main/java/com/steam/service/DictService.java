package com.steam.service;

import java.util.List;

public interface DictService {

	//根据词典id 查询返回对应的dict_item_name
	List<String> selectTagName(List<String> idList);

}
