package com.yydscm.Enum;

/**
 * 费用项目
 *
 * @author Administrator
 */
public enum ExpenseItem {

    运费("freight"), 代收货款("collection_fee"), 保价费("valuation_fee"), 送货费("delivery_fee"), 垫付款("advance"), 接货费("receiving_fee"),
    装卸费("handling_fee"), 包装费("packing_fee"), 上楼费("upstair_fee"), 叉吊费("forklift_fee"), 现返费("return_fee"), 欠返费("under_charge_fee"),
    仓储费("warehousing_fee");

    private final String value;

    //构造器默认只能是private, 从而保证构造函数只能在内部使用  
    ExpenseItem(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
