package com.yydscm.Enum;

/**
 * 付款方式
 *
 * @author Administrator
 */
public enum PaymentMethodEnum {

    现付(1), 到付(2), 欠付(3), 回付(4), 月结(5), 货款扣(6), 多笔付(7);

    private final Integer value;

    //构造器默认只能是private, 从而保证构造函数只能在内部使用  
    PaymentMethodEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
