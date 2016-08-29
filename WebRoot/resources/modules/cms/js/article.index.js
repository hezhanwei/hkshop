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
		
				var articleId = $(this).attr("articleId");
		
		/* 执行 */
		$.ajax({
			type : 'post',
			url : BASE_URL + '/cmsArticle/delete',
			data : 'articleId=' + articleId,
			dataType : 'json',
			timeout : 10000,
			success : function(data) {
				if (data.status == 0) {
					$("#articleId_" + articleId).parent().parent().remove();
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
					property : 'articleId',
					label : '文章ID',
					sortable : false
				},
				{
					property : 'author',
					label : '作者',
					sortable : false
				},
				{
					property : 'title',
					label : '标题',
					sortable : false
				},
				{
					property : 'titleCode',
					label : '标题标识',
					sortable : false
				},
				{
					property : 'type',
					label : '类型',
					sortable : false
				},
				{
					property : 'status',
					label : '状态',
					sortable : false
				},
				{
					property : 'ctime',
					label : '创建时间',
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
		if(type==='00'){
			item.type = '用户端';
		}else if(type==='11'){
			item.type='商户端';
		}
		
		var status = item.status;
		if(status==='00'){
			item.status= '显示';
		}else if(status==='11'){
			item.status='隐藏';
		}
		item._query = '<a href="'+BASE_URL+'/cmsArticle/detail?articleId=' + item.articleId + '"  class="modal-detail"><i class="fa fa-search-plus" title="查看详情"></i></a>';
		item._action = '<a href="' + BASE_URL + '/cmsArticle/edit?articleId=' + item.articleId
				+ '" class="operating-edit" title="编辑"><i class="fa fa-pencil"></i></a>&nbsp;&nbsp;'
				+ '<a href="javascript:;" class="operate-delete" id="articleId_' + item.articleId + '" articleId="' + item.articleId
				+ '" title="删除"><i class="fa fa-trash-o"></i></a>';

		item.ctime = dateConverter(item.ctime, PATTERN_ENUM.datetime);
		item.mtime = dateConverter(item.mtime, PATTERN_ENUM.datetime);
	});
}