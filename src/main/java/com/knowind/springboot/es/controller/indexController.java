package com.knowind.springboot.es.controller;

import com.knowind.springboot.es.pojo.EsLogs;
import com.knowind.springboot.es.repository.EsLogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index")
public class indexController {
    @Autowired
    private EsLogsRepository logsRepository;

    @RequestMapping("/index")
    public String index (){
        //Iterable<EsLogs> all = logsRepository.findAll();
        return "你好，index";
    }

}
