function getDictItemName(value,divObj,urlAddr){
	//获取td内容 拆分
	var idArray = value.split("#");
	//使用ajax发送数据
	$.ajax({
		type:"POST",
		url:urlAddr,
		contentType:"application/json",
		dataType:"json",
		data:JSON.stringify(idArray),
		success:function(data){
			//获取名字列表
			var str = "";
			for(var i=0;i<data.length;i++){
				str += data[i] + " ";
			}
			$(divObj).html(str);
		}
	});
}

function getDictItemNameView(value,divObj,urlAddr,type){
	//获取td内容 拆分
	var idArray = value.split("#");
	//使用ajax发送数据
	$.ajax({
		type:"POST",
		url:urlAddr,
		contentType:"application/json",
		dataType:"json",
		data:JSON.stringify(idArray),
		success:function(data){
			//获取名字列表
			var str = "";
			for(var i=0;i<data.length;i++){
				if(type=="tagids"){
					str += '<span class="top_tag"> '+data[i]+' </span>';
				}else if(type=="platform"){
					str += '<span class="platform_img '+data[i]+'"></span>';
				}
			}
			$(divObj).prepend(str);
		}
	});
}

//拼接checkbox中的多选后的id，用#分割
function joinTagidsAndPlatformId(name, divObj) {
	//拼接标签id
	$("input[name="+name+"]").click(function() {
		//创造一个数组
		var arr = [];
		$("input[name="+name+"]").each(function() {
			//拼接id字符串
			if(this.checked){
				arr.push(this.value);
			}else{
				var index = arr.indexOf(this.value);
				if(index!=-1){
					arr[index] = "";
				}
			}
		})
		//遍历完成
		$(divObj).val(arr.join("#"));
	})
}

//处理标签和平台checkbox状态回显
function setTagidsAndPlatformEcho(allData,itemData) {
	var allDataIds = allData;
	var itemIds = itemData.split("#");
	
	for(var i=0;i<allDataIds.length;i++){
		for(var j=0;j<itemIds.length;j++){
			if(allDataIds[i].value==itemIds[j]){
				allDataIds[i].checked = true;
				break;
			}
		}
	}
}

//处理标志位回显
function setFlagEcho(name,value){
	var flag = $("input[name="+name+"]");
	$(flag).val(value);
	$(flag).prop("checked",value);
}

