package com.tiza.leo.bigdata.kafka.interceptor;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * @author leowei
 * @date 2021/4/4  - 8:45
 */
public class TimeInterceptor implements org.apache.kafka.clients.producer.ProducerInterceptor<String,String> {



    @Override
    public void configure(Map<String, ?> configs) {

    }

    //// 返回值前增加时间戳
    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
        return new ProducerRecord<>(record.topic(),record.partition(),record.key(), System.currentTimeMillis()+"," + record.value());
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {

    }

    @Override
    public void close() {

    }

}
