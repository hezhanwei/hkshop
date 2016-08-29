$(document).ready(function() {
	loadDataGridContent(columnsDefined(), 'formatData');
	
    /**
	 * 刷新或搜索
	 */
	$('.action-refresh').on('click', function(){
		$('#content_listing').datagrid('reload');
		return false;
	});
	
	/**
	 * 关键字搜索
	 */
	$('input[name=key]').on('keypress', function (event) {
	    if (event.which == '13') {
	        $('#content_listing').datagrid("reload");
	        return false;
	    }
	});
	
	/**
	 * 删除 - 单条
	 */
    $("#content_listing").delegate('.operate-delete', 'click', function(){
		var grabId = $(this).attr("grabId");
		doDeleteGrab(grabId);
	});
    
});


/**
 * 删除
 */
function doDeleteGrab(grabId) {
	var del = confirm('确定要删除所选活动吗？');
	if (! del) {return false;}
	
	/* 执行 */
	$.ajax({
    	type:'post',
        url:BASE_URL+'/grouponGrab/delete',
        data:'grabId=' + grabId,
        dataType:'json',
        timeout:60000,
        success:function(data){
    		if (data.status == 0) {
				$("#grabId_" + grabId).parent().parent().remove();
    		} else {
    			alert(data.msg);
    		}
    		return false;
    	}
    });
}

function columnsDefined() {
	return [
            {
            	property: 'a',
            	label: ''
            },
            {
                property: 'bulkId',
                label: 'ID',
                sortable: false
            },
            {
                property: 'title',
                label: '抢购名称',
                sortable: false
            },
            {
                property: 'startTime',
                label: '抢购开始时间',
                sortable: false
            },
            {
                property: 'endTime',
                label: '抢购结束时间',
                sortable: false
            },
            {
            	property: 'goodsName',
            	label: '商品名称',
            	sortable: false
            },
            {
            	property: 'action',
            	label: '操作',
            	sortable: false
            }
        ];
}

function formatData(items) {
	$.each(items, function (index, item) {
        item.a = '<a href="'+BASE_URL+'/grouponGrab/detail?grabId=' + item.bulkId + '"  class="modal-detail"><i class="fa fa-search-plus" title="查看详情"></i></a>';
        
        var strIntegral = '未知';
        if (item.integral == 1) {
        	strIntegral = '可用';
        } else if (item.integral == 0) {
        	strIntegral = '不可用';
        }
        item.integral = strIntegral;
        
        item.action = '<a href="'+BASE_URL+'/grouponGrab/edit?grabId=' + item.bulkId + '" class="load-content" title="编辑"><i class="fa fa-pencil"></i></a>&nbsp;&nbsp;' +
        '<a href="javascript:;" class="operate-delete" id="grabId_' + item.bulkId + '" grabId="' + item.bulkId + '" title="删除"><i class="fa fa-times"></i></a>';
        
        item.startTime = dateConverter(item.startTime, PATTERN_ENUM.datetime);
        item.endTime = dateConverter(item.endTime, PATTERN_ENUM.datetime);
    });
}