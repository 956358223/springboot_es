package com.knowind.springboot.es.vo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.util.List;

@Data
public class KnoResult {
    /**
     * 定义jackson对象
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * 响应业务状态
     */
    private String code;

    /**
     * 响应中的数据
     */
    private Object data;

    /**
     * 响应消息
     */
    private String msg;

    public static KnoResult build(String code, Object data, String msg) {
        return new KnoResult(code, data, msg);
    }

    public static KnoResult ok(Object data) {
        return new KnoResult(data);
    }

    public static KnoResult ok() {
        return new KnoResult(null);
    }

    public KnoResult() {

    }

    public static KnoResult build(String code, String msg) {
        return new KnoResult(code, null,msg);
    }

    public KnoResult(String code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public KnoResult(Object data) {
        this.code = "success";
        this.data = data;
        this.msg = "操作成功";
    }

    /**
     * 将json结果集转化为DocResult对象
     *
     * @param jsonData json数据
     * @param clazz DocResult中的object类型
     * @return
     */
    public static KnoResult formatToPojo(String jsonData, Class<?> clazz) {
        try {
            if (clazz == null) {
                return MAPPER.readValue(jsonData, KnoResult.class);
            }
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (clazz != null) {
                if (data.isObject()) {
                    obj = MAPPER.readValue(data.traverse(), clazz);
                } else if (data.isTextual()) {
                    obj = MAPPER.readValue(data.asText(), clazz);
                }
            }
            return build(jsonNode.get("code").asText(), obj,jsonNode.get("msg").asText());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 没有object对象的转化
     *
     * @param json
     * @return
     */
    public static KnoResult format(String json) {
        try {
            return MAPPER.readValue(json, KnoResult.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Object是集合转化
     *
     * @param jsonData json数据
     * @param clazz 集合中的类型
     * @return
     */
    public static KnoResult formatToList(String jsonData, Class<?> clazz) {
        try {
            JsonNode jsonNode = MAPPER.readTree(jsonData);
            JsonNode data = jsonNode.get("data");
            Object obj = null;
            if (data.isArray() && data.size() > 0) {
                obj = MAPPER.readValue(data.traverse(),
                        MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
            }
            return build(jsonNode.get("code").asText(), obj,jsonNode.get("msg").asText());
        } catch (Exception e) {
            return null;
        }
    }
}
