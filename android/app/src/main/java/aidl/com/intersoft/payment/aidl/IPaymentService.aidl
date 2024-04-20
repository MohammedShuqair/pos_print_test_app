package com.intersoft.payment.aidl;
import com.intersoft.payment.aidl.IPaymentListener;
interface IPaymentService{
	 /**
     * 设置参数
     * @param key 参数key
     * @param value 参数值
     */
    boolean setParam(String key, String value);
    /**
     * 获取参数
     * @param key 参数key
     * @return 返回打印张数
     */
	String getParam(String key);
	/**
     * 查询单笔流水
     * @param key 参数key
     * @return 返回打印张数
     */
	void findFlow(String oriOutOrderNo, IPaymentListener iPaymentListener);
}
