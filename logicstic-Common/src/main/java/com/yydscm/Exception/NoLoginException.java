package com.yydscm.Exception;


/**
 * Created by chenzhaopeng on 2017/8/14
 */
public class NoLoginException extends RuntimeException {
    public NoLoginException(String msg) {
        super(msg);
    }
}
