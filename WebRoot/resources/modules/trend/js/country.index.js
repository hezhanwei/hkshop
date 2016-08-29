$(document).ready(function() {
	loadDataGridContent(columnsDefined(), 'formatData');

	/**
	 * 刷新或搜索
	 */
	$('body').delegate('.action-refresh, #action_search', 'click', function() {
		$('#content_listing').datagrid('reload');
	});

	/**
	 * 关键字搜索 - 支持回车
	 */
	$("input[name=key]").on('keypress', function(event) {
		if (event.which == '13') {
			$('#content_listing').datagrid('reload');
			return false;
		}
	});

	/**
	 * 单个删除
	 */
	$('body').delegate('.operate-delete', 'click', function() {
		var del = confirm('确定要删除吗？');
		if (!del) {
			return false;
		}
		
				var countryId = $(this).attr("countryId");
		
		/* 执行 */
		$.ajax({
			type : 'post',
			url : BASE_URL + '/trendCountry/delete',
			data : 'countryId=' + countryId,
			dataType : 'json',
			timeout : 10000,
			success : function(data) {
				if (data.status == 0) {
					$("#countryId_" + countryId).parent().parent().remove();
				} else {
					alert(data.msg);
				}
				return false;
			}
		});
	});
});

function columnsDefined() {
	return [
				{
					property: '_query',
					label: ''
				},
				{
					property : 'countryId',
					label : '国家ID',
					sortable : false
				},
				{
					property : 'countryName',
					label : '国家名称',
					sortable : false
				},
				{
					property : 'countrySort',
					label : '排序',
					sortable : false
				},
				{
					property : 'filepath',
					label : '图片路径',
					sortable : false
				},
				{
					property : 'type',
					label : '类型',
					sortable : false
				},
				{
					property : 'isDelete',
					label : '是否删除',
					sortable : false
				},
				{
					property : '_action',
					label : '操作',
					sortable : false
				} 
			];
}

function formatData(items) {
	$.each(items, function(index, item) {
		
		var type = item.type;
		var isDelete = item.isDelete;
		if(type==='00'){
			item.type = '商家申请';
		}else if(type==='11'){
			item.type = '平台添加';
		}
		if(isDelete==='00'){
			item.isDelete = '已删除';
		}else if(isDelete==='11'){
			item.isDelete = '未删除';
		}
		
		item._query = '<a href="'+BASE_URL+'/trendCountry/detail?countryId=' + item.countryId + '"  class="modal-detail"><i class="fa fa-search-plus" title="查看详情"></i></a>';
		item._action = '<a href="' + BASE_URL + '/trendCountry/edit?countryId=' + item.countryId
				+ '" class="operating-edit" title="编辑"><i class="fa fa-pencil"></i></a>&nbsp;&nbsp;'
				+ '<a href="javascript:;" class="operate-delete" id="countryId_' + item.countryId + '" countryId="' + item.countryId
				+ '" title="删除"><i class="fa fa-trash-o"></i></a>';

		item.ctime = dateConverter(item.ctime, PATTERN_ENUM.datetime);
	});
}