$(document).ready(function() {
	/**
	 * 提交按钮处理
	 */
	$(".input-submit").click(function(){
		var submit_id = $(this).attr("id");
		switch (submit_id) {
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
//	history.go(-1);
	window.location.href=BASE_URL+"/version/index"; 
}

/**
 * 表单提交处理
 */
function form_submit() {
	if (! $("input[name=vCode]").val()) {
		alert('版本号不能为空');
		return false;
	}
	if (! $("input[name=vName]").val()) {
		alert('版本名称不能为空');
		return false;
	}
	if (! $("input[name=filepath]").val()) {
		alert('文件路径不能为空');
		return false;
	}
	if (! $("input[name=size]").val()) {
		alert('安装包大小不能为空');
		return false;
	}
	
	$(".input-submit").attr('disabled', true);
	
	var version_id = $("input[name=id]").val();
	var saveCallBack;
	if (version_id == '' || version_id == 0) {
		saveCallBack = form_save_added;
	} else {
		$("#edit_form").attr("action", BASE_URL+"/version/edit");
		saveCallBack = form_save_edited;
	}
	
	var options = {
            dataType:'json',
            timeout:60000,
            success:saveCallBack
    };
    $("#edit_form").ajaxSubmit(options);
    return false;
}

/**
 * 添加成功，返回处理
 */
function form_save_added(data, textStatus) {
	if (data.status === 0) {
        notice('edit_notice', img_done + ' 添加成功!', true, 5000);
        
        // 判断是否返回列表管理
        if (back_listing == true) {
//        	history.back(-1);
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
//        history.back(-1);
        form_cancel();
    } else {
    	notice('edit_notice', img_delete + " " + data.error, true, 5000);
    }
    $(".input-submit").removeAttr('disabled');
}