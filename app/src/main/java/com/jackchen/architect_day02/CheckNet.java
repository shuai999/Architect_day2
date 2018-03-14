package com.jackchen.architect_day02;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Email: 2185134304@qq.com
 * Created by JackChen 2018/3/14 14:48
 * Version 1.0
 * Params:
 * Description:   标记切点  注解
*/
//FIELD：如果是在MainActivity中使用的话，就只能用于 private String name类似的这些属性，不能用于方法、类上边
//METHOD：如果是在MainActivity中使用的话，就只能用于 方法上边，不能用于属性、类中
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)    // RUNTIME：用于app运行    CLASS：用于编译时 比如ButterKnife注解   SOURCE：代表资源
public @interface CheckNet {

}
