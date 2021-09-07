package com.knowind.springboot.es.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.apache.commons.lang.time.DateFormatUtils;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author:wanghd
 * @description:
 * @date:2021/8/25
 */
@Data
@Document(indexName = "logs",type = "doc",
        useServerConfiguration = true,createIndex = false)
public class EsLogs {
    @Id
    private String id;
    @Field(type = FieldType.Text,analyzer = "ik_max_word")//standard
    private String ip;
    @Field(type = FieldType.Text,format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss || yyyy-MM-DD || epoch_millis")
    private String createTime;          //创建时间
    @Field(type = FieldType.Date,format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss || yyyy-MM-DD || epoch_millis")
    private Date updateTime;            //修改时间
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String logLevel = "INFO";   //日志级别
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String logType;             //日志类型
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String message;             //消息
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String operation;           //操作
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String method;              //请求方法
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String params;              //请求参数
    @Field(type = FieldType.Long,analyzer = "ik_max_word")
    private long times;                //请求时间(毫秒)
    @Field(type = FieldType.Integer,analyzer = "ik_max_word")
    private Integer secretLevel;        //被操作的客体的密级
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String userId;              //用户id
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String userName;            //拼音用户名
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String trueName;            //正确的用户名
    @Field(type = FieldType.Integer,analyzer = "ik_max_word")
    private Integer isSuccess = 1;      //是否成功：1是，，0否
    @Field(type = FieldType.Text,analyzer = "ik_max_word")
    private String securityType;        //安全类型例如：securitySystem    审计安全类型：securityAudit
    public EsLogs() {
    }
    public EsLogs(HttpServletRequest request){
        //this.id = PrimaryKey.getUUID();
//        this.ip = request.getRemoteAddr();
        //this.ip = IpUtil.getIpAddr(request);
        this.createTime = DateFormatUtils.format(new Date(),"yyyy-MM-dd HH:mm:ss");
        this.logLevel = "INFO";
        this.logType = "APP";
    }
}
