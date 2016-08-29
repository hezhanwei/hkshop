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
		
				var bannerId = $(this).attr("bannerId");
		
		/* 执行 */
		$.ajax({
			type : 'post',
			url : BASE_URL + '/cmsBanner/delete',
			data : 'bannerId=' + bannerId,
			dataType : 'json',
			timeout : 10000,
			success : function(data) {
				if (data.status == 0) {
					$("#bannerId_" + bannerId).parent().parent().remove();
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
					property : 'bannerId',
					label : '首页banner表ID',
					sortable : false
				},
				{
					property : 'bannerName',
					label : '名称',
					sortable : false
				},
				{
					property : 'forwardUrl',
					label : '跳转地址',
					sortable : false
				},
				{
					property : 'attachmentid',
					label : '关联trend_attachment表',
					sortable : false
				},
				{
					property : 'status',
					label : '状态',
					sortable : false
				},
				{
					property : 'terminalType',
					label : '终端类型',
					sortable : false
				},
				{
					property : 'bannerSort',
					label : '排序使用',
					sortable : false
				},
				{
					property : 'startTime',
					label : '上架时间',
					sortable : false
				},
				{
					property : 'endTime',
					label : '下架时间',
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
		item._query = '<a href="'+BASE_URL+'/cmsBanner/detail?bannerId=' + item.bannerId + '"  class="modal-detail"><i class="fa fa-search-plus" title="查看详情"></i></a>';
		item._action = '<a href="' + BASE_URL + '/cmsBanner/edit?bannerId=' + item.bannerId
				+ '" class="operating-edit" title="编辑"><i class="fa fa-pencil"></i></a>&nbsp;&nbsp;'
				+ '<a href="javascript:;" class="operate-delete" id="bannerId_' + item.bannerId + '" bannerId="' + item.bannerId
				+ '" title="删除"><i class="fa fa-trash-o"></i></a>';

		item.startTime = dateConverter(item.startTime, PATTERN_ENUM.datetime);
		item.endTime = dateConverter(item.endTime, PATTERN_ENUM.datetime);
	});
}