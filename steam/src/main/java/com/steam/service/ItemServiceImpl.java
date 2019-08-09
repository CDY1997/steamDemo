package com.steam.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.steam.bean.ItemInfo;
import com.steam.bean.SysDict;
import com.steam.bean.itemInfoVo;
import com.steam.mapper.ItemMapper;
import com.steam.utils.Utils;

/**
 * 游戏相关service实现类
 * @author ASUS
 *
 */

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;
	@Override
	public List<ItemInfo> selectAll() {
		return itemMapper.selectAll();
	}
	@Override
	public List<ItemInfo> selectItemInfoByVo(itemInfoVo vo) {
		List<ItemInfo> queryList = itemMapper.selectItemInfoByVo(vo);
		if(vo==null){
			List<ItemInfo> queryListView = new ArrayList<ItemInfo>();
			for(ItemInfo itemInfo : queryList){
				if(itemInfo.getIs_enable()!=null&&itemInfo.getIs_enable()==true){
					queryListView.add(itemInfo);
				}
			}
			return queryListView;
		}
		if(vo.getItem_tagids()!=null&&!vo.getItem_tagids().equals("")||
		   vo.getItem_platform()!=null&&!vo.getItem_platform().equals("")){
			//将满足条件的结果放入过滤的列表中并返回
			List<ItemInfo> filterList = new ArrayList<ItemInfo>();
			
			//获取数组，用#分割
			String[] voTagids = vo.getItem_tagids().split("#");
			String[] voplatform = vo.getItem_platform().split("#");
			
			//遍历列表
			for(ItemInfo itemInfo : queryList){
				boolean isContain = true;
				//游戏标签
				if(!vo.getItem_tagids().equals("")){
					String[] itemInfoTagids = itemInfo.getItem_tagids().split("#");
					List<String> itemInfoTagidsList = Arrays.asList(itemInfoTagids);
					for(String voTag : voTagids){
						isContain = itemInfoTagidsList.contains(voTag);
						if(!isContain){
							break;
						}
					}
				}
				//游戏平台
				if(!vo.getItem_platform().equals("")&&isContain){
					String[] itemInfoPlatform = itemInfo.getItem_platform().split("#");
					List<String> itemInfoPlatformList = Arrays.asList(itemInfoPlatform);
					for(String vopf : voplatform){
						isContain = itemInfoPlatformList.contains(vopf);
						if(!isContain){
							break;
						}
					}
				}
				if(isContain){
					filterList.add(itemInfo);
				}
			}
			return filterList;
		}else{
			return queryList;
		}
	}
	@Override
	public List<SysDict> selectSysDicByTypeId(String typeId) {
		return itemMapper.selectSysDicByTypeId(typeId);
	}
	@Override
	public void save(ItemInfo itemInfo, MultipartFile upload_image) throws Exception {
		ItemInfo saveItemInfo = setItemInfo(itemInfo,upload_image);
		itemMapper.save(saveItemInfo);
	}
	@Override
	public ItemInfo selectItemInfoById(String id) {
		return itemMapper.selectItemInfoById(id);
	}
	@Override
	public void update(ItemInfo itemInfo, MultipartFile upload_image) throws Exception {
		ItemInfo updateItemInfo = setItemInfo(itemInfo,upload_image);
		itemMapper.update(updateItemInfo);
	}
	@Override
	public void deleteByLogic(String id, Boolean enable) {
		itemMapper.deleteByLogic(id, enable);
	}
	@Override
	public List<ItemInfo> selectItemInfoSortByFlag(String name, Integer num) {
		return itemMapper.selectItemInfoSortByFlag(name, num);
	}
	
	//保存游戏更新游戏的逻辑方法
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
