package com.mmall.pojo;

import lombok.*;

import java.util.Date;

/*
// 写入get、set、equals、hashcode、tostring方法
// @Data
// 无参构造器
@NoArgsConstructor
// 全参构造器
@AllArgsConstructor
// toString方法
@ToString
*/

// 常用方式
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cart {
    private Integer id;

    private Integer userId;

    private Integer productId;

    private Integer quantity;

    private Integer checked;

    private Date createTime;

    private Date updateTime;


}