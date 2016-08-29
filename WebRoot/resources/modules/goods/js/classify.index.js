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
		
				var classifyId = $(this).attr("classifyId");
		
		/* 执行 */
		$.ajax({
			type : 'post',
			url : BASE_URL + '/goodsClassify/delete',
			data : 'classifyId=' + classifyId,
			dataType : 'json',
			timeout : 10000,
			success : function(data) {
				if (data.status == 0) {
					$("#classifyId_" + classifyId).parent().parent().remove();
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
					property : 'classifyId',
					label : '分类ID',
					sortable : false
				},
				{
					property : 'classifyName',
					label : '分类名称',
					sortable : false
				},
				{
					property : 'classifySort',
					label : '排序',
					sortable : false
				},
				{
					property : 'type',
					label : '类型',
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
		if (type==='00') {
			item.type = '商家申请';
		}else if(type==='11'){
			item.type = '平台添加';
		}
		item._query = '<a href="'+BASE_URL+'/goodsClassify/detail?classifyId=' + item.classifyId + '"  class="modal-detail"><i class="fa fa-search-plus" title="查看详情"></i></a>';
		item._action = '<a href="' + BASE_URL + '/goodsClassify/edit?classifyId=' + item.classifyId
				+ '" class="operating-edit" title="编辑"><i class="fa fa-pencil"></i></a>&nbsp;&nbsp;'
				+ '<a href="javascript:;" class="operate-delete" id="classifyId_' + item.classifyId + '" classifyId="' + item.classifyId
				+ '" title="删除"><i class="fa fa-trash-o"></i></a>';

		item.ctime = dateConverter(item.ctime, PATTERN_ENUM.datetime);
	});
}