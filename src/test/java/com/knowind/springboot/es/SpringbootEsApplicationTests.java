package com.knowind.springboot.es;

//import org.junit.jupiter.api.Test;
import com.knowind.springboot.es.pojo.EsLogs;
import com.knowind.springboot.es.repository.EsLogsRepository;
import org.apache.commons.lang.time.DateFormatUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootEsApplicationTests {
    @Autowired
    private EsLogsRepository logsRepository;
    @Test
     public void contextLoads() {
        EsLogs esLogs = new EsLogs();
        esLogs.setId("5");
        esLogs.setIp("192.168.0.111");
        //String format = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
        esLogs.setCreateTime("2021-8-25 00:00:00");
        esLogs.setIsSuccess(1);
        esLogs.setUserId("5");
        esLogs.setLogType("portal");
        esLogs.setMessage("测试集成es");
        esLogs.setMethod("方法");
        esLogs.setParams("无");
        esLogs.setOperation("测试集成es");
        esLogs.setSecretLevel(1);
        esLogs.setUserName("wanghd");
        esLogs.setTrueName("王海东");
        esLogs.setUpdateTime(null);
        esLogs.setSecurityType("user");
        logsRepository.save(esLogs);
    }
    @Test
    public void findAll() {
        Page<EsLogs> esLogs = (Page<EsLogs>) logsRepository.findAll();
        List<EsLogs> content = esLogs.getContent();
        System.out.println(content);
    }
}
