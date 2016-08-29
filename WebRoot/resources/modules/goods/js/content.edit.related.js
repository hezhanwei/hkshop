$(document).ready(function() {

	refreshListing();

	/**
	 * 刷新或搜索
	 */
	$('.action-refresh').on('click', function() {
		$('#content_listing').datagrid('reload');
		return false;
	});

	/**
	 * 关键字搜索 - 支持回车
	 */
	$("input[name=relatedKey]").on('keypress', function(event) {
		if (event.which == '13') {
			$('#content_listing').datagrid('reload');
			return false;
		}
	});

	/**
	 * 编辑时，加载默认品牌信息
	 */
	if (content_id) {
		related_load_brand();
	}

	/**
	 * 动态加载分类相关联的品牌信息
	 */
	$('#relatedCategoryId').change(function() {
		related_load_brand();
	});

	/**
	 * 重载已选关联商品列表的拖拽事件
	 */
	$('#selected_related_goods').find('ul').sortable('refresh');

	/**
	 * 选择一个关联商品
	 */
	$("#content_listing>tbody").delegate('.select-single', 'change', function() {
		if ($(this).is(':checked')) {
			select_related_goods($(this).parent().parent());
		} else {
			remove_related_goods($('li#li_related_goods_' + $(this).val()));
		}
	});

	/**
	 * 移除一个已选择的关联商品
	 */
	$("#selected_related_goods").delegate('.fa-remove-related-goods', 'click', function() {
		remove_related_goods($(this).parent().parent());
	});
});

/**
 * 动态加载分类相关联的品牌信息
 */
function related_load_brand() {
	var category_id = $('#relatedCategoryId').val();
	var str_option = '';

	if (category_id == 0 || category_id == '') {
		/* 清空现有品牌信息 */
		$("select[name=relatedBrandId]").find("option:gt(0)").remove();
		return false;
	}

	$.ajax({
		type : 'post',
		url : BASE_URL + '/goodsContent/getBrandByCategoryId',
		data : 'categoryId=' + category_id,
		dataType : 'json',
		timeout : 60000,
		success : function(data) {
			if (data.status == 0) {
				/* 清空现有品牌信息 */
				$("select[name=relatedBrandId]").find("option:gt(0)").remove();

				/* 加载新的品牌信息 */
				var d = data.brands;
				if (d != null && d.length > 0) {
					for ( var i = 0; i < d.length; i++) {
						str_option += '<option value="' + d[i]['brandId'] + '">' + d[i]['brandName'] + '</option>';
					}
					$("select[name=relatedBrandId]").append(str_option);
				}
			}
			return false;
		}
	});
}

/**
 * 将商品池中已被选中的关联商品置为已选中状态 - 用于编辑及列表翻页的情况
 */
function reset_related_goods_listing() {
	$('#selected_related_goods').find('ul>li').each(function() {
		var content_id = $(this).attr('content_id');
		$('#select_single_' + content_id).prop('checked', true);
	});
}

/**
 * 选择一个关联商品
 */
function select_related_goods(obj) {
	var template_selected_related_goods = '<li class="b-b m-t-none-reset" id="li_related_goods_{#content_id#}" content_id="{#content_id#}" draggable="true">'
			+ '<a href="javascript:;">' + '<i title="移除该商品" class="fa fa-times pull-right m-t-xs fa-remove-related-goods"></i>'
			+ '<i class="fa fa-fw fa-ellipsis-v"></i><font class="related-goods-name">{#name#}</font>' + '</a></li>';

	var content_id = obj.find('td').eq(1).text();
	var name = obj.find('td').eq(3).text();

	var tsg = template_selected_related_goods.replace(/{#content_id#}/g, content_id).replace('{#name#}', name);

	/* 将新选择属性加入到已选列表，并重载该列表的拖拽事件 */
	$('#selected_related_goods').find('ul').append(tsg).sortable('refresh');
}

/**
 * 移除一个已选择的关联商品
 */
function remove_related_goods(obj) {
	$('#select_single_' + obj.attr('content_id')).removeAttr('checked');
	obj.remove();
}

function refreshListing() {
	/* fuelux datagrid */
	var DataGridDataSource = function(options) {
		this._formatter = options.formatter;
		this._columns = options.columns;
		this._delay = options.delay;
	};

	DataGridDataSource.prototype = {
		columns : function() {
			return this._columns;
		},
		data : function(options, callback) {
			var url = BASE_URL + '/goodsContent/storeAddPage';
			var self = this;

			setTimeout(function() {

				var data = $.extend(true, [], self._data);

				$.ajax(url, {
					data : {
						rstype : "json",
						pageIndex : options.pageIndex + 1,
						pageSize : options.pageSize,
						key : $('input[name=relatedKey]').val(),
						category : $('select[name=relatedCategoryId]').val(),
						brandId : $('select[name=relatedBrandId]').val(),
						contentId : $('#contentId').val()
					},
					dataType : 'json',
					async : true,
					type : 'GET'
				}).done(function(response) {
					var data = response.data;
					if (!data) {
						return false;
					}

					var count = response.count;// 设置data.total
					// PAGING
					var startIndex = options.pageIndex * options.pageSize;
					var endIndex = startIndex + options.pageSize;
					var end = (endIndex > count) ? count : endIndex;
					var pages = Math.ceil(count / options.pageSize);
					var page = options.pageIndex + 1;
					var start = startIndex + 1;

					if (self._formatter)
						self._formatter(data);

					callback({
						data : data,
						start : start,
						end : end,
						count : count,
						pages : pages,
						page : page
					});

					// 将属性池中已被选中的备选属性置为已选中状态 - 用于编辑的情况
					reset_related_goods_listing();
				}).fail(function(e) {

				});
			}, self._delay);
		}
	};

	$('#content_listing').datagrid({
		dataSource : new DataGridDataSource({
			// Column definitions for Datagrid
			columns : [ {
				property : 'checkbox',
				label : ''
			}, {
				property : 'contentId',
				label : 'ID',
				sortable : false
			}, {
				property : 'thumbsrc',
				label : '缩略图',
				sortable : false
			}, {
				property : 'name',
				label : '商品名称',
				sortable : false
			}, {
				property : 'categoryName',
				label : '所属分类',
				sortable : false
			}, {
				property : 'sku',
				label : '商品货号',
				sortable : false
			}, {
				property : 'price',
				label : '价格',
				sortable : false
			}, {
				property : 'stock',
				label : '库存',
				sortable : false
			} ],
			formatter : function(items) {
				$.each(items, function(index, item) {
					item.checkbox = '<input type="checkbox" name="post[]" id="select_single_' + item.contentId + '" class="select-single" value="' + item.contentId + '">';

					// item.thumbsrc = '<a href="' + item.image_default + '"
					// rel="prettyPhoto[gallery]" title="' + item.name + '"><img
					// style="width:30px;height:30px" src="' +
					// item.image_default + '"/></a>';
					item.thumbsrc = '<img style="width:30px;height:30px" src="' + IMG_URL + item.imageDefault + '"/>';
				});
			}
		}),
		loadingHTML : '<span><img src="' + STATIC_URL + '/images/loading.gif"><i class="fa fa-info-sign text-muted" "></i>正在加载……</span>',
		itemsText : '项',
		itemText : '项',
		dataOptions : {
			pageIndex : 0,
			pageSize : 15
		}
	});
}
