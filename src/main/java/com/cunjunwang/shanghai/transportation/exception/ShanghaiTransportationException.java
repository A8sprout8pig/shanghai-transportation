package com.cunjunwang.shanghai.transportation.exception;

import lombok.*;

/**
 * Created by CunjunWang on 2018-12-22.
 */

@Setter
@Getter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class ShanghaiTransportationException extends RuntimeException {

    private String errCode;

    private String errMsg;

    public static String ERR_PREFIX = "SH_TRANSPORTATION-";

    public ShanghaiTransportationException(String errCode, String errMsg){
        this.errCode = ERR_PREFIX + errCode;
        this.errMsg = errMsg;
    }

}
