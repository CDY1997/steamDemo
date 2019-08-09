package com.steam.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.steam.mapper.DictMapper;

@Service
public class DictServiceImpl implements DictService {
	
	@Autowired
	private DictMapper dictMapper;
	@Override
	public List<String> selectTagName(List<String> idList) {
		return dictMapper.selectTagName(idList);
	}

}
