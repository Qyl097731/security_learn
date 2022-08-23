package com.example.demo.utils;

public interface ResultCode {

    public static Integer SUCCESS = 200; //成功

    public static Integer ERROR = 201; //失败
    public static Integer UNAUTHORIZED = 401;
    public static Integer FORBIDDEN = 403 ;
    public static Integer NOTFOUND = 404 ;
    public static Integer INTERNALSERVERERROR = 500;
}
