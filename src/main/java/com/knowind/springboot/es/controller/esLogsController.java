package com.knowind.springboot.es.controller;

import com.knowind.springboot.es.pojo.EsLogs;
import com.knowind.springboot.es.repository.EsLogsRepository;
import com.knowind.springboot.es.vo.KnoResult;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/es")
public class esLogsController {
    @Autowired
    private EsLogsRepository logsRepository;

    @PostMapping("/save")
    public EsLogs save(@RequestBody EsLogs esLogs){
        EsLogs esLogs1 = logsRepository.save(esLogs);
        System.out.println("esLogs1======="+esLogs1);
        return esLogs1;
    }
    @GetMapping("/find")
    public KnoResult findById(@RequestParam(name = "id",required = false) String id){
        if(id.isEmpty()){
            return KnoResult.build("","id不能为空");
        }
        Optional<EsLogs> esLogs = logsRepository.findById(id);
        return KnoResult.ok(esLogs);
    }
    @GetMapping("/findAll")
    public List<EsLogs> findAll(){
        Page<EsLogs> all = (Page<EsLogs>) logsRepository.findAll();
        List<EsLogs> esLogs = all.getContent();
        return esLogs;
    }
//    GET /logs/_search
//    {
//        "query": {
//        "bool": {
//            "must": [
//            {
//                "match": {
//                "ip": "192.168.0.111"
//            }
//            },
//            {
//                "match": {
//                "userName": "wanghd"
//            }
//            },
//            {
//                "match": {
//                "trueName": "王海东"
//            }
//            },
//            {
//                "match": {
//                "securityType": "user"
//            }
//            }
//      ],
//            "filter": {
//                "range": {
//                    "createTime.keyword" :{
//                        "gte": "2021-08-24 00:00:00",
//                                "lte": "2021-08-26 00:00:00"
//                    }
//                }
//            }
//        }
//    }
//    }
    @GetMapping("/search")
    public KnoResult search(
            @RequestParam(name = "ip",required = false) String ip,
            @RequestParam(name = "userName",required = false) String userName,
            @RequestParam(name = "trueName",required = false) String trueName,
            @RequestParam(name = "logLevel",required = false) String logLevel,
            @RequestParam(name = "securityType",required = false) String securityType,
            @RequestParam(name = "startTime",required = false) String startTime,
            @RequestParam(name = "endTime",required = false) String endTime
            ){
        //HashMap<String, Object> map = new HashMap<>();
        StopWatch watch = new StopWatch();
        watch.start();
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        if( ip !=null ){
            builder.must(QueryBuilders.matchPhraseQuery("ip",ip));
        }
        if( userName !=null ){
            builder.must(QueryBuilders.matchPhraseQuery("userName",userName));
        }
        if( trueName !=null ){
            builder.must(QueryBuilders.matchPhraseQuery("trueName",trueName));
        }
        if( logLevel !=null ){
            builder.must(QueryBuilders.matchPhraseQuery("logLevel",logLevel));
        }
        if( securityType !=null ){
            builder.must(QueryBuilders.matchPhraseQuery("securityType",securityType));
        }

        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("createTime.keyword").from(startTime);
        if( endTime !=null ){
            rangeQuery.to(endTime);
        }
        builder.filter(rangeQuery);
        String s = builder.toString();
        System.out.println("s====="+s);
        Page<EsLogs> search = (Page<EsLogs>) logsRepository.search(builder);
        List<EsLogs> content = search.getContent();
        watch.stop();
        long totalTimeMillis = watch.getTotalTimeMillis();
        System.out.println("totalTimeMillis====="+totalTimeMillis);
        return KnoResult.ok(content);
    }

    @PostMapping("/delete")
    public KnoResult delete(String id){
        logsRepository.deleteById(id);
        return KnoResult.ok("已删除");
    }
}
