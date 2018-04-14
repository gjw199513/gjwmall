package com.mmall.util;

import com.google.common.collect.Lists;
import com.mmall.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by gjw19 on 2018/4/14.
 */
// 处理json的通用工具类
@Slf4j
public class JsonUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        // 对象的所有字段全部列入
        /**
         * NON_NULL：当为空值时不会显示
         * NON_DEFAULT:当设置默认值，并与默认值相同时，不会显示
         * NON_EMPTY：同NON_NULL类似，但是对于空的对象，不会显示
         * ALWAYS：所有都显示
         */
        objectMapper.setSerializationInclusion(Inclusion.ALWAYS);

        // 取消默认转换timestamps（以毫秒数表示）形式，便于显示
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);

        // 忽略空Bean转json的错误，不会抛出异常信息
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);

        // 所有的日期格式都统一为以下的样式，即yyyy-MM-dd HH:mm:ss
        objectMapper.setDateFormat(new SimpleDateFormat(DateTimeUtil.STANDARD_FORMAT));

        // 忽略 在json字符串中存在，但是在java对象中不存在对应属性的情况，防止错误
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_NULL_FOR_PRIMITIVES, false);

    }


    /**
     * 对象转字符串
     *
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String obj2String(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("Parse object to String error", e);
            return null;
        }

    }

    /**
     * 返回格式化好的字符串
     *
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String obj2StringPreety(T obj) {
        if (obj == null) {
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            log.warn("Parse Object to String error", e);
            return null;
        }
    }

    public static <T> T string2Obj(String str, Class<T> clazz) {
        if (StringUtils.isEmpty(str) || clazz == null) {
            return null;
        }

        try {
            return clazz.equals(String.class) ? (T) str : objectMapper.readValue(str, clazz);
        } catch (IOException e) {
            log.warn("Parse String to Object error", e);
            return null;
        }
    }


    /**
     * 传入字符串和类型，将该字符串转换为该类型
     *
     * @param str
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T> T string2Obj(String str, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(str) || typeReference == null) {
            return null;
        }

        try {
            return (T) (typeReference.getType().equals(String.class) ? str : objectMapper.readValue(str, typeReference));
        } catch (IOException e) {
            log.warn("Parse String to Object error", e);
            return null;
        }
    }

    public static <T> T string2Obj(String str, Class<?> collectionClass, Class<?>... elementClasses) {
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass, elementClasses);

        try {
            return objectMapper.readValue(str, javaType);
        } catch (IOException e) {
            log.warn("Parse String to Object error", e);
            return null;
        }
    }

    public static void main(String[] args) {
        User user = new User();
        user.setId(1);
        user.setEmail("gjw1995@163.com");
        String userJsonPretty = JsonUtil.obj2StringPreety(user);
        log.info("userJson:{}", userJsonPretty);
//        User u2 = new User();
//        u2.setId(2);
//        u2.setEmail("gjw19952@163.com");
//
//
//        String user1Json = JsonUtil.obj2String(u1);
//
//        String user1JsonPretty = JsonUtil.obj2StringPreety(u1);
//
//        log.info("user1Json:{}", user1Json);
//
//        log.info("user1JsonPretty: {}", user1JsonPretty);
//
//        User user = JsonUtil.string2Obj(user1Json, User.class);
//
//        List<User> userList = Lists.newArrayList();
//        userList.add(u1);
//        userList.add(u2);
//
//        String userListStr = JsonUtil.obj2StringPreety(userList);
//
//        log.info("==========");
//
//        log.info(userListStr);
//
//        List<User> userListobj1 = JsonUtil.string2Obj(userListStr, new TypeReference<List<User>>() {
//        });
//
//        List<User> userListobj2 = JsonUtil.string2Obj(userListStr,List.class, User.class);

        System.out.println("end");
    }
}
