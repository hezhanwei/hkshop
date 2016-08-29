$(document).ready(function() {
	if($("input[name='couponTypeVal']").val() == 0) {
		$('.full-minus').hide();
	}
	if($("input[name='isExchangeVal']").val() == 1) {
		$('.need-point').hide();
	}
	
	/**
	 * 预处理日期时间选择控件
	 */
	$('#validStime').datepicker({
		format: 'yyyy-mm-dd'
	}).on('changeDate', function(ev){
		var startTime = $('#validStime').val();
		$('#validEtime').datepicker('setStartDate', startTime);
		$('#grantStime').datepicker('setEndDate', startTime);
		$('#validStime').datepicker('hide');
	});
	
	$('#validEtime').datepicker({
		format: 'yyyy-mm-dd'
	}).on('changeDate', function(ev){
		var endTime = $('#validEtime').val();
		$('#validStime').datepicker('setEndDate', endTime);
		$('#grantEtime').datepicker('setEndDate', endTime);
		$('#validEtime').datepicker('hide');
	});
	
	$('#grantStime').datepicker({
		format: 'yyyy-mm-dd'
	}).on('changeDate', function(ev){
		var startTime = $('#grantStime').val();
		$('#grantEtime').datepicker('setStartDate', startTime);
		$('#validStime').datepicker('setStartDate', startTime);
		$('#grantStime').datepicker('hide');
	});
	
	$('#grantEtime').datepicker({
		format: 'yyyy-mm-dd'
	}).on('changeDate', function(ev){
		var endTime = $('#grantEtime').val();
		$('#grantStime').datepicker('setEndDate', endTime);
		$('#validEtime').datepicker('setStartDate', endTime);
		$('#grantEtime').datepicker('hide');
	});
	
	/**
	 * 满减和全场类型切换
	 */
	$("input[name='coupon.couponType']").change(function(){
		var type = $(this).val();
		if(type == 1) {
			$('.full-minus').show();
		} else {
			$('#relationContent').find("option:selected").prop("selected",false);
			$('#basicPrice').val('');
			$('.full-minus').hide();
		}
	});
	
	/**
	 * 是否允许积分切换
	 */
	$("input[name='coupon.isExchange']").change(function(){
		var isExchange = $(this).val();
		if(isExchange == 1) {
			$('.need-point').hide();
			$('#couponPoint').val('');
		} else {
			$('.need-point').show();
		}
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
	window.location.href = BASE_URL+"/couponCategory/index";;
}

/**
 * 表单提交处理
 */
function form_submit() {
	notice('edit_notice', img_loading_small, false);
	
	var couponId = $("#couponId").val();
	
	if (! $("#couponName").val()) {
		notice('edit_notice', img_delete + ' 请输入优惠券名称', true, 5000);
		return false;
	}
	
	if (! $("#total").val()) {
		notice('edit_notice', img_delete + ' 请输入用户券生成数量', true, 5000);
		return false;
	}
	
	if (! $("#validStime").val()) {
		notice('edit_notice', img_delete + ' 请选择有效开始时间', true, 5000);
		return false;
	}
	
	if (! $("#validEtime").val()) {
		notice('edit_notice', img_delete + ' 请选择有效结束时间', true, 5000);
		return false;
	}
	
	if (! $("#grantStime").val()) {
		notice('edit_notice', img_delete + ' 请选择发放开始时间', true, 5000);
		return false;
	}
	
	if (! $("#grantEtime").val()) {
		notice('edit_notice', img_delete + ' 请选择发放结束时间', true, 5000);
		return false;
	}
	
	if (! $("input[name='coupon.couponType']:checked").val()  ) {
		notice('edit_notice', img_delete + ' 请选择优惠券类型', true, 5000);
		return false;
	} 
	if($("input[name='coupon.couponType']:checked").val() == 2) {
		if(! $("#relationContent").val()) {
			notice('edit_notice', img_delete + ' 请选择商品分类', true, 5000);
			return false;
		}
		
		if(! $("#basicPrice").val()) {
			notice('edit_notice', img_delete + ' 请输入基本要求金额', true, 5000);
			return false;
		}
	}
	if(! $("#faceValue").val()) {
		notice('edit_notice', img_delete + ' 请输入优惠金额', true, 5000);
		return false;
	}
	
	if (! $("input[name='coupon.isExchange']:checked").val()) {
		notice('edit_notice', img_delete + ' 请选择是否允许积分兑换', true, 5000);
		return false;
	}
	
	if($("input[name='coupon.isExchange']:checked").val() == 0) {
		if(! $("#couponPoint").val()) {
			notice('edit_notice', img_delete + ' 请输入兑换所需积分', true, 5000);
			return false;
		}
	}
	
	if(! $("#rule").val()) {
		notice('edit_notice', img_delete + ' 请输入使用规则', true, 5000);
		return false;
	}
	
	if(! $("#body").val()) {
		notice('edit_notice', img_delete + ' 请输入使用详情', true, 5000);
		return false;
	}
	
	$(".input-submit").attr('disabled', true);
	
	var saveCallBack;
	if (couponId == '' || couponId == 0) {
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
