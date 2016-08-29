$(document).ready(function() {
	/**
	 * 预处理日期时间选择控件
	 */
	$('#startTime').datetimepicker({
		format: 'yyyy-mm-dd hh:ii',
		language : 'zh-CN'
	}).on('changeDate', function(ev){
		var startTime = $('#startTime').val();
		$('#endTime').datetimepicker('setStartDate', startTime);
		$('#startTime').datetimepicker('hide');
	});
	
	$('#endTime').datetimepicker({
		format: 'yyyy-mm-dd hh:ii'
	}).on('changeDate', function(ev){
		var endTime = $('#endTime').val();
		$('#startTime').datetimepicker('setEndDate', endTime);
		$('#endTime').datetimepicker('hide');
	});
	
	/**
	 * 提交按钮处理
	 */
	$(".input-submit").click(function(){
		var submit_type = $(this).attr("data_submit_type");
		switch (submit_type) {
			case 'submit_cancel' : form_cancel(); break;
			case 'submit_save_back' : back_listing = true; form_submit(); break;
			case 'submit_save_continue' : back_listing = false; form_submit(); break;
		}
	});
});


/**
 * 取消处理
 */
function form_cancel() {
	window.location.href = BASE_URL+"/grouponBulk/index";
}

/**
 * 表单提交处理
 */
function form_submit() {
	notice('edit_notice', img_loading_small, false);
	
	var bulkId = $("#bulkId").val();
	
	if (! $("#title").val()) {
		notice('edit_notice', img_delete + ' 请输入团购名称', true, 5000);
		return false;
	}
	
	if (! $("#startTime").val()) {
		notice('edit_notice', img_delete + ' 请选择团购开始时间', true, 5000);
		return false;
	}
	
	if (! $("#endTime").val()) {
		notice('edit_notice', img_delete + ' 请选择团购结束时间', true, 5000);
		return false;
	}
	
	if (! $("#inventorySum").val()) {
		notice('edit_notice', img_delete + ' 请输入团购总库存', true, 5000);
		return false;
	}
	
	if (! $("#inventory").val()) {
		notice('edit_notice', img_delete + ' 请输入库存准量', true, 5000);
		return false;
	}
	
	if (! $("#sku").val()) {
		notice('edit_notice', img_delete + ' 请输入商品 sku', true, 5000);
		return false;
	}
	
	if (! $("#goodsName").val()) {
		notice('edit_notice', img_delete + ' 请输入商品名称', true, 5000);
		return false;
	}
	
	if (! $("#price").val()) {
		notice('edit_notice', img_delete + ' 请输入团购价格', true, 5000);
		return false;
	}
	
	if (! $("input[name='integral']:checked").val()) {
		notice('edit_notice', img_delete + ' 请选择是否使用积分', true, 5000);
		return false;
	}
	
	$(".input-submit").attr('disabled', true);
	
	var saveCallBack;
	if (bulkId == '' || bulkId == 0) {
		saveCallBack = form_save_added;
	} else {
		saveCallBack = form_save_edited;
	}
	
	var options = {
            dataType:'json',
            timeout:60000,
            success:saveCallBack,
            error:ajaxError
    };
    $("#edit_form").ajaxSubmit(options);
    return false;
}

/**
 * 添加成功，返回处理
 */
function form_save_added(data, textStatus) {
    if(data.status === 0) {
        notice('edit_notice', img_done + ' 添加成功!', true, 5000);
        
        // 判断是否返回列表管理
        if (back_listing == true) {
        	form_cancel();
        }
    } else {
    	notice('edit_notice', img_delete + " " + data.error, true, 5000);
    }
    $(".input-submit").removeAttr('disabled');
}

/**
 * 编辑成功，返回处理
 */
function form_save_edited(data, textStatus) {
    if (data.status === 0) {
        notice('edit_notice', img_done + ' 编辑成功!', true, 5000);
        form_cancel();
    } else {
    	notice('edit_notice', img_delete + " " + data.error, true, 5000);
    }
    $(".input-submit").removeAttr('disabled');
}
