package com.knowind.springboot.es.repository;

import com.knowind.springboot.es.pojo.EsLogs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsLogsRepository extends ElasticsearchRepository<EsLogs,String> {

}
