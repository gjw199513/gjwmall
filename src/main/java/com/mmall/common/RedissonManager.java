package com.mmall.common;

import com.mmall.util.PropertiesUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by gjw19 on 2018/4/21.
 */
@Component
@Slf4j
public class RedissonManager {
    private Config config = new Config();

    private Redisson redisson = null;

    public Redisson getRedisson() {
        return redisson;
    }

    private static String redis1Ip = PropertiesUtil.getProperty("redis1.ip");
    private static Integer redis1Port = Integer.parseInt(PropertiesUtil.getProperty("redis1.port"));
    private static String redis2Ip = PropertiesUtil.getProperty("redis2.ip");
    private static Integer redis2Port = Integer.parseInt(PropertiesUtil.getProperty("redis2.port"));

    // 构造器执行之后执行该方法
    @PostConstruct
    private void init() {
        // 格式为ip:port,因此使用字符串拼接上面的ip和port
        try {
            config.useSingleServer().setAddress(new StringBuilder().append(redis1Ip).append(":").append(redis1Port).toString());

            redisson = (Redisson) Redisson.create(config);

            log.info("初始化Redison结束");
        } catch (Exception e) {
            log.error("reddisson init error", e);
        }
    }


}
