package com.yydscm.Enum;

/**
 * 运单状态
 *
 * @author Administrator
 */
public enum InvoiceStatus {

    已开票(1), 已装车(2), 运输中(3), 已到货(4), 已签收(5), 中转(6), 运单作废(9);

    private final Integer value;

    //构造器默认只能是private, 从而保证构造函数只能在内部使用  
    InvoiceStatus(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

}
