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
		
				var recommendId = $(this).attr("recommendId");
		
		/* 执行 */
		$.ajax({
			type : 'post',
			url : BASE_URL + '/goodsRecommend/delete',
			data : 'recommendId=' + recommendId,
			dataType : 'json',
			timeout : 10000,
			success : function(data) {
				if (data.status == 0) {
					$("#recommendId_" + recommendId).parent().parent().remove();
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
					property : 'recommendId',
					label : 'ID',
					sortable : false
				},
				{
					property : 'goodsSku',
					label : '商品sku',
					sortable : false
				},
				{
					property : 'urlLink',
					label : '广告链接',
					sortable : false
				},
				{
					property : 'filepath',
					label : '商品图片路径',
					sortable : false
				},
				{
					property : 'ctime',
					label : '创建时间',
					sortable : false
				},
				{
					property : 'beginTime',
					label : '开始时间',
					sortable : false
				},
				{
					property : 'endTime',
					label : '截止时间',
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
		
		item._query = '<a href="'+BASE_URL+'/goodsRecommend/detail?recommendId=' + item.recommendId + '"  class="modal-detail"><i class="fa fa-search-plus" title="查看详情"></i></a>';
		item._action = '<a href="' + BASE_URL + '/goodsRecommend/edit?recommendId=' + item.recommendId
				+ '" class="operating-edit" title="编辑"><i class="fa fa-pencil"></i></a>&nbsp;&nbsp;'
				+ '<a href="javascript:;" class="operate-delete" id="recommendId_' + item.recommendId + '" recommendId="' + item.recommendId
				+ '" title="删除"><i class="fa fa-trash-o"></i></a>';

		item.ctime = dateConverter(item.ctime, PATTERN_ENUM.datetime);
		item.beginTime = dateConverter(item.beginTime, PATTERN_ENUM.datetime);
		item.endTime = dateConverter(item.endTime, PATTERN_ENUM.datetime);
	});
}