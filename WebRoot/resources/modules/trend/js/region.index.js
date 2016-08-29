$(document).ready(function() {
    /**
     * 刷新
     */
    $(".action-refresh").on('click', function(){
    	location.reload();
    });
    
    /**
     * 展开/收起地区
     */
    $('#content_listing').delegate('.region-extend', 'click', function(){
    	var region_id = $(this).attr('regionId');
    	region_extend(region_id);
    });
    
    /**
     * 单个删除
     */
    $('body').delegate('.operate-delete', 'click', function(){
    	var tr = $(this).closest('tr');
    	var regionId = $(this).attr('regionId');
    	if (! regionId || ! confirm('确定要删除此地区吗？')) return false;
    	
    	$.ajax({
	    	type:'post',
	        url:BASE_URL+'/trend/region/delete',
	        data:'regionId=' + regionId,
	        dataType:'json',
	        timeout:10000,
	        success:function(data){
	    		if (data.status == 0) {
	    			$(tr).fadeIn().remove();
	    		} else {
	    			alert(data.msg);
	    		}
	    		return false;
	    	}
	    });
	});
});


/**
 * 展开/收起地区
 */
function region_extend(region_id) {
	var tr_template = '<tr id="tr_region_{#region_id#}" class="{#region-pid#}">' + 
	    	'<td style="padding-left:{#padding-left#}px">' + 
	    	'<a href="javascript:;" class="region-extend" regionId="{#region_id#}">' + 
	    	'<i class="fa fa-plus-square-o"></i> {#region_name#}' + 
	    	'</a>' + 
	    	'</td>' + 
	    	'<td>{#sort_order#}</td>' + 
	    	'<td>' + 
	    	'<a href="'+BASE_URL+'/trend/region/add?pid={#region_id#}" data-toggle="ajaxModal" class="operate-edit">添加子地区</a> | ' + 
	    	'<a href="'+BASE_URL+'/trend/region/edit?regionId={#region_id#}" data-toggle="ajaxModal" class="operate-edit">编辑</a> | ' + 
	    	'<a href="javascript:;" class="operate-delete" regionId="{#region_id#}">删除</a>' + 
	    	'</td>';
	
	/* 判断是展开，还是收起 */
	var $tr = $('#tr_region_' + region_id);
	var act_extend = 'open';
	if ($tr.find('.fa-minus-square-o').size() > 0) {
		act_extend = 'close';
	}
	
	/* 收起 */
	if (act_extend == 'close') {
		// 收起 - 即隐藏
		$('tr.region-' + region_id).hide('normal');
		
		// 处理节点样式
		$tr.find('.fa-minus-square-o').removeClass('fa-minus-square-o').addClass('fa-plus-square-o');
		
		return false;
	}
	
	/* 展开 - 已展开过的 */
	if ($('tr.region-' + region_id).size() > 0) {
		// 展开 - 即显示
		$('tr.region-' + region_id).show('normal');
		
		// 处理节点样式
		$tr.find('.fa-plus-square-o').removeClass('fa-plus-square-o').addClass('fa-minus-square-o');
		
		return false;
	}
	
	/* 展开 - 首次 */
	var str_padding = 30;
	$.ajax({
    	type:'post',
        url:BASE_URL+'/trend/region/getChilds',
        data:'regionId=' + region_id,
        dataType:'json',
        timeout:10000,
        success:function(data){
    		if (status == 0) {
    			var d = data.data;
    			
    			$(d).each(function(){
    				var str_padding_format = str_padding * (this.grade - 1);
    				var region_pid = $tr.attr('class') ? $tr.attr('class') + ' region-' + region_id : 'region-' + region_id;
    				
    				// 格式化模板
    				var tr_template_format = tr_template.replace(/{#region_id#}/g, this.regionId)
    						.replace(/{#region-pid#}/g, region_pid)
    						.replace(/{#padding-left#}/g, str_padding_format)
    						.replace(/{#region_name#}/g, this.regionName)
    						.replace(/{#sort_order#}/g, this.sortOrder);
    				
    				// 加载格式化后的模板
    				$tr.after(tr_template_format);
    				
    				// 处理节点样式
    				$tr.find('.fa-plus-square-o').removeClass('fa-plus-square-o').addClass('fa-minus-square-o');
    			});
    		}
    		return false;
    	}
    });
}