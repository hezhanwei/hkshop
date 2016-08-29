package com.bluemobi.constant;

import java.util.HashMap;
import java.util.Map;

public class OrderConstant {

	// ============================= 订单模块 ================================
	/** 订单类型--普通订单 */
	public static final byte COMMON_ORDER_TYPE = 0;
	/** 订单类型--团购订单 */
	public static final byte GROUPONBULK_ORDER_TYPE = 1;
	/** 订单类型--抢购订单 */
	public static final byte GROUPONGRAB_ORDER_TYPE = 2;
	/** 支付状态--未支付 */
	public static final byte PAY_WAITED_STATUS = 0;
	/** 支付状态--已支付 */
	public static final byte PAY_FINISHED_STATUS = 1;
	/** 支付类型--在线支付 */
	public static final byte ONLINE_PAYMENT_TYPE = 1;
	/** 支付类型--线下付款 */
	public static final byte OFFLINE_PAYMENT_TYPE = 2;
	/** 支付方式--支付宝 */
	public static final byte ALIPAY_PAYMENT = 1;
	/** 支付方式--微信 */
	public static final byte WECHAT_PAYMENT = 2;
	/** 支付方式--银联 */
	public static final byte UNIONPAY_PAYMENT = 3;
	/** 支付方式--货到付款 */
	public static final byte COD_PAYMENT = 4;
	/** 支付方式-支付类型关系映射 */
	public static Map<Byte, Byte> paymentMap;
	
	static {
	    paymentMap = new HashMap<Byte, Byte>();
	    paymentMap.put(OrderConstant.ALIPAY_PAYMENT, OrderConstant.ONLINE_PAYMENT_TYPE);
	    paymentMap.put(OrderConstant.WECHAT_PAYMENT, OrderConstant.ONLINE_PAYMENT_TYPE);
	    paymentMap.put(OrderConstant.UNIONPAY_PAYMENT, OrderConstant.ONLINE_PAYMENT_TYPE);
	    paymentMap.put(OrderConstant.COD_PAYMENT, OrderConstant.OFFLINE_PAYMENT_TYPE);
	}

	/** 订单状态-待处理 */
	public static final byte WAIT_PROCESS_ORDER_STATUS = 0;
	/** 订单状态-已签收 */
	public static final byte SIGNED_ORDER_STATUS = 1;
	/** 订单状态-待付款 */
	public static final byte WAIT_PAY_ORDER_STATUS = 2;
	/** 订单状态-付款成功 */
	public static final byte PAYED_ORDER_STATUS = 3;
	/** 订单状态-待发货 */
	public static final byte WAIT_DELIVERY_ORDER_STATUS = 4;
	/** 订单状态-已发货 */
	public static final byte DELIVERIED_ORDER_STATUS = 5;

}
