package com.cunjunwang.shanghai.bus.query.entity;

import lombok.*;

import java.io.Serializable;

/**
 * 定义前后端交互数据传输对象
 * Created by CunjunWang on 2018/12/16.
 */
@Data
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class ResultData<T> implements Serializable {
    private static final long serialVersionUID = -1L;

    private int status;

    private String errCode;

    private String errMsg;

    private T data;

    public static final int SUCCESS = 1;

    public static final int FAILURE = 0;
}
