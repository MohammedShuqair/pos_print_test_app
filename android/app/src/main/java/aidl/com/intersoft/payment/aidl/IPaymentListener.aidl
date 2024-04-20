package com.intersoft.payment.aidl;
interface IPaymentListener{

	/**
     * 请求处理成功
     * @param code 返回码
	* @param data  放回数据 Json格式
     * @return 返回打印张数
     */
	void onSuccess(String code,String data);
	/**
     * 请求处理失败
     * @param failCode返回码
	* @param msg 返回码提示
     * @return 返回打印张数
     */
	void onFail(String failCode,String msg);

}
