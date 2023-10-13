package cn.rl520.encrypt.annotation;

import java.lang.annotation.*;

/**
 * @Author wenbo
 * @Date 2023/10/13 15:47
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface EnableEncrypt {
}
