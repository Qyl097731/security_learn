package com.example.demo.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author qyl
 * @program RedisBean.java
 * @Description redis暂存对象
 * @createTime 2022-08-23 11:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedisBean implements Serializable {
    private String username;
    private List<String> permissionValueList;
}
