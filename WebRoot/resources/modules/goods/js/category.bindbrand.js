$(document).ready(function() {
	/**
	 * 加载品牌列表
	 */
	loadDataGridContent(columnsDefined(), 'formatData');

	/**
	 * 刷新搜索
	 */
	$(".action-refresh,#action_search").on('click', function() {
		$('#brand_listing').datagrid('reload');
	});

	/**
	 * 关键字搜索 - 支持回车
	 */
	$('input[name=key]').on('keypress', function(event) {
		if (event.which == '13') {
			$('#brand_listing').datagrid("reload");
			return false;
		}
	});

	/**
	 * 重载已选属性列表的拖拽事件
	 */
	$('#selected_brand').find('ul').sortable('refresh');

	/**
	 * 选择/移除一个关联属性
	 */
	$("#brand_listing>tbody").delegate('.select-single', 'change', function() {
		if ($(this).is(':checked')) {
			select_brand($(this));
		} else {
			remove_brand($('li#li_brand_' + $(this).val()));
		}
	});

	/**
	 * 移除一个已选择的关联属性
	 */
	$("#selected_brand").delegate('.fa-remove-brand', 'click', function() {
		remove_brand($(this).parent().parent());
	});

	/**
	 * 提交按钮处理
	 */
	$(".input-submit").click(function() {
		var submitId = $(this).attr("id");
		switch (submitId) {
		case 'submit_cancel':
			form_cancel();
			break;
		case 'submit_save_back':
			back_listing = true;
			form_submit();
			break;
		case 'submit_save_continue':
			back_listing = false;
			form_submit();
			break;
		}
	});
});

/**
 * 将属性池中已被选中的备选属性置为已选中状态 - 用于编辑及列表翻页的情况
 */
function reset_brand_listing() {
	$('#selected_brand').find('ul>li').each(function() {
		var brandId = $(this).attr('brand_id');
		$('#select_single_' + brandId).prop('checked', true);
	});
}

/**
 * 选择一个关联属性
 */
function select_brand(obj) {
	var template_selected_brand = '<li class="b-b m-t-none-reset" id="li_brand_{#brandId#}" brand_id="{#brandId#}" draggable="true">' + '<a href="javascript:;">'
			+ '<i title="移除该品牌" class="fa fa-times pull-right m-t-xs fa-remove-brand"></i>' + '<i class="fa fa-fw fa-ellipsis-v"></i><font class="brand-name">{#brandName#}</font>'
			+ '</a></li>';

	var objTr = obj.parent().parent();
	var brandId = obj.val();
	var brandName = objTr.find('td').eq(3).text();

	var tsb = template_selected_brand.replace(/{#brandId#}/g, brandId).replace('{#brandName#}', brandName);

	/* 将新选择属性加入到已选列表，并重载该列表的拖拽事件 */
	$('#selected_brand').find('ul').append(tsb).sortable('refresh');
}

/**
 * 移除一个已选择的关联属性
 */
function remove_brand(obj) {
	$('#select_single_' + obj.attr('brand_id')).removeAttr('checked');
	obj.remove();
}

/**
 * 取消处理
 */
function form_cancel() {
	window.location.href = BASE_URL + "/goodsCategory/index";
}

/**
 * 表单提交处理
 */
function form_submit() {
	/* 处理所选品牌 */
	var brand_ids = '';
	var brandIds;
	var sb = new Array();
	$('#selected_brand').find('ul>li').each(function(k) {
		var brandId = $(this).attr('brand_id');
		sb[k] = brandId;
	});
	if (sb.length > 0) {
		brandIds = sb.join(',');
	}
	$('input[name=brandIds]').val(brandIds);

	$(".input-submit").attr('disabled', true);

	var saveCallBack = form_save_edited;
	var options = {
		dataType : 'json',
		timeout : 60000,
		success : saveCallBack
	};
	$("#edit_form").ajaxSubmit(options);
	return false;
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

function columnsDefined() {
	return [ {
				property : 'checkbox',
				label : ''
			}, {
				property : 'brandId',
				label : 'ID',
				sortable : true
			}, {
				property : 'brandLogo',
				label : '标志',
				sortable : true
			}, {
				property : 'brandName',
				label : '品牌名称',
				sortable : false
			}, {
				property : 'brandUrl',
				label : '链接网址',
				sortable : false
			}, {
				property : 'ctime',
				label : '创建时间',
				sortable : true
			} ];
}

function formatData(items) {
	$.each(items, function(index, item) {
		item.checkbox = '<input type="checkbox" name="post[]" id="select_single_' + item.brandId + '" class="select-single" value="' + item.brandId + '">';

		if (item.brandLogo) {
			item.brandLogo = '<a class="thumb m-l" href="javascript:;">' + '<img src="' + IMG_URL + item.brandLogo + '">' + '</a>';
		} else {
			item.brandLogo = '';
		}

		item.operate = '<a href="' + BASE_URL + '/goodsBrand/edit/?brandId=' + item.brandId
				+ '" data-toggle="ajaxModal" class="operate-edit" title="编辑"><i class="fa fa-pencil"></i></a>&nbsp;&nbsp;'
				+ '<a href="javascript:;" class="operate-delete" id="brand_' + item.brandId + '" brand_id="' + item.brandId
				+ '" title="删除"><i class="fa fa-times"></i></a>';
		
		item.ctime = dateConverter(item.ctime, PATTERN_ENUM.datetime);
	});
	reset_brand_listing();
}