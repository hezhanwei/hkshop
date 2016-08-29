$(document).ready(function() {
	$('#validStartDate').datepicker({format: 'yyyy-mm-dd', autoclose: true});
	$('#validEndDate').datepicker({format: 'yyyy-mm-dd', autoclose: true});
	$('#grantStartDate').datepicker({format: 'yyyy-mm-dd', autoclose: true});
	$('#grantEndDate').datepicker({format: 'yyyy-mm-dd', autoclose: true});

	loadDataGridContent(columnsDefined(), 'formatData');
	
	
    /**
	 * 刷新或搜索
	 */
	$('.action-refresh').on('click', function(){
		$('#content_listing').datagrid('reload');
		return false;
	});
	
	/**
	 * 重置搜索条件
	 */
	$('.action-reset').on('click', function(){
		$('#validStartDate').val('');
		$('#validEndDate').val('');
		$('#grantStartDate').val('');
		$('#grantEndDate').val('');
		$('#type').find("option:selected").prop("selected",false);
		$('#status').find("option:selected").prop("selected",false);
		$('#isExchange').find("option:selected").prop("selected",false);
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
    $("#content_listing").delegate('.switch-sm', 'click', function(){
		var couponId = $(this).attr("couponId");
		doDeleteCoupon(couponId);
		return false;
	});
    
    /**
	 * 启用，锁定，解锁 - 单条
	 */
    $("#content_listing").delegate('.operate-delete', 'click', function(){
		var couponId = $(this).attr("couponId");
		changeCouponStatus(couponId);
		return false;
	});
    
});

/**
 * 启用，锁定，解锁
 */
function changeCouponStatus(couponId) {
	var del = confirm('确定要'+$('#couponId_' + couponId + '_status').attr('title')+'所选优惠券吗？');
	if (! del) {return false;}
	/* 执行 */
	var status = $('#couponId_' + couponId + '_status').attr('status');
	$.ajax({
    	type:'post',
        url:BASE_URL+'/couponCategory/updateStatus',
        data:
        	{
        		'couponId': couponId,
        		'status': status,
        	 },
        dataType:'json',
        timeout:60000,
        success:function(data){
    		if (data.status > 0) {
    			alert(data.msg);
    		}
    		$('#content_listing').datagrid('reload');
    		return false;
    	}
    });
}

/**
 * 删除
 */
function doDeleteCoupon(couponId) {
	var del;
	if($('#coupon_' + couponId).prop('checked')) {
		del = confirm('确定要恢复所选优惠券吗？');
	} else {
		del = confirm('确定要删除所选优惠券吗？');
	}
	if (! del) {return false;}
	
	/* 执行 */
	$.ajax({
    	type:'post',
        url:BASE_URL+'/couponCategory/delete',
        data:'couponId=' + couponId,
        dataType:'json',
        timeout:60000,
        success:function(data){
    		if (data.status == 0) {
    			$('#coupon_' + couponId).prop('checked', !$('#coupon_' + couponId).prop('checked'));
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
                property: 'couponId',
                label: 'ID',
                sortable: false
            },
            {
                property: 'couponName',
                label: '优惠券名称',
                sortable: false
            },
            {
                property: 'faceValue',
                label: '优惠券面额',
                sortable: false
            },
            {
                property: 'validStime',
                label: '有效开始时间',
                sortable: false
            },
            {
                property: 'validEtime',
                label: '有效结束时间',
                sortable: false
            },
            {
                property: 'grantStime',
                label: '发放开始时间',
                sortable: false
            },
            {
                property: 'grantEtime',
                label: '发放结束时间',
                sortable: false
            },
            {
            	property: 'status',
            	label: '优惠券状态',
            	sortable: false
            },
            {
            	property: 'isExchange',
            	label: '允许积分',
            	sortable: false
            },
            {
            	property: 'couponType',
            	label: '优惠券类型',
            	sortable: false
            },
            {
            	property: 'isDel',
            	label: '删除标记',
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
        item.a = '<a href="'+BASE_URL+'/couponCategory/detail?couponId=' + item.couponId + '"  class="modal-detail"><i class="fa fa-search-plus" title="查看详情"></i></a>';
        
        item.action = '<a href="'+BASE_URL+'/couponCategory/edit?couponId=' + item.couponId + '" class="load-content" title="编辑"><i class="fa fa-pencil"></i></a>&nbsp;&nbsp;';
        if (item.status == 0) {
        	item.action += '<a href="javascript:;" class="operate-delete" id="couponId_' + item.couponId + '_status" couponId="' + item.couponId + '" title="启用" status="'+item.status+'"><i class="fa fa-road"></i></a>';
        } else if (item.status == 1) {
        	item.action += '<a href="javascript:;" class="operate-delete" id="couponId_' + item.couponId + '_status" couponId="' + item.couponId + '" title="锁定" status="'+item.status+'"><i class="fa fa-lock"></i></a>';
        } else if (item.status == 2) {
        	item.action += '<a href="javascript:;" class="operate-delete" id="couponId_' + item.couponId + '_status" couponId="' + item.couponId + '" title="解锁" status="'+item.status+'"><i class="fa fa-unlock"></i></a>';
        }
        
        var strStatus = '未知';
        if (item.status == 0) {
        	strStatus = '未启用';
        } else if (item.status == 1) {
        	strStatus = '启用';
        } else if (item.status == 2) {
        	strStatus = '锁定';
        }
        item.status = strStatus;
        
        var strCouponType = '未知';
        if (item.couponType == 0) {
        	strCouponType = '全场';
        } else if (item.couponType == 1) {
        	strCouponType = '满减';
        }
        item.couponType = strCouponType;
        
        var strIsExchange = '未知';
        if (item.isExchange == 0) {
        	strIsExchange = '允许';
        } else if (item.isExchange == 1) {
        	strIsExchange = '不允许';
        }
        item.isExchange = strIsExchange;
        
        var ischecked = item.isDel == 1 ? 'checked="checked"' : '';
        item.isDel = '<label class="switch-sm" couponId="' + item.couponId + '">' + 
        		'<input type="checkbox" id="coupon_' + item.couponId + '" ' + ischecked + ' />' + 
        		'<span></span></label>';
		item.validStime = dateConverter(item.validStime, PATTERN_ENUM.date);
		item.validEtime = dateConverter(item.validEtime, PATTERN_ENUM.date);
		item.grantStime = dateConverter(item.grantStime, PATTERN_ENUM.date);
		item.grantEtime = dateConverter(item.grantEtime, PATTERN_ENUM.date);
	});
}