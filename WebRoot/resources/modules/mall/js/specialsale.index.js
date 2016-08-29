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
		
				var specialsaleId = $(this).attr("specialsaleId");
		
		/* 执行 */
		$.ajax({
			type : 'post',
			url : BASE_URL + '/goodsSpecialsale/delete',
			data : 'specialsaleId=' + specialsaleId,
			dataType : 'json',
			timeout : 10000,
			success : function(data) {
				if (data.status == 0) {
					$("#specialsaleId_" + specialsaleId).parent().parent().remove();
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
					property : 'specialsaleId',
					label : 'ID',
					sortable : false
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
					property : 'sort',
					label : '排序',
					sortable : true
				},
				{
					property : 'filepath',
					label : '图片路径',
					sortable : false
				},
				{
					property : 'status',
					label : '状态类型',
					sortable : false
				},
				{
					property : 'beginTime',
					label : '开始时间',
					sortable : false
				},
				{
					property : 'endTime',
					label : '结束时间',
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
		
		var status = item.status;
		if(status=='00'){
			item.status = '商家申请';
		}else if(status=='11'){
			item.status = '平台添加';
		}
		
		item._query = '<a href="'+BASE_URL+'/goodsSpecialsale/detail?specialsaleId=' + item.specialsaleId + '"  class="modal-detail"><i class="fa fa-search-plus" title="查看详情"></i></a>';
		item._action = '<a href="' + BASE_URL + '/goodsSpecialsale/edit?specialsaleId=' + item.specialsaleId
				+ '" class="operating-edit" title="编辑"><i class="fa fa-pencil"></i></a>&nbsp;&nbsp;'
				+ '<a href="javascript:;" class="operate-delete" id="specialsaleId_' + item.specialsaleId + '" specialsaleId="' + item.specialsaleId
				+ '" title="删除"><i class="fa fa-trash-o"></i></a>';

		item.ctime = dateConverter(item.ctime, PATTERN_ENUM.datetime);
		item.beginTime = dateConverter(item.beginTime, PATTERN_ENUM.datetime);
		item.endTime = dateConverter(item.endTime, PATTERN_ENUM.datetime);
	});
}