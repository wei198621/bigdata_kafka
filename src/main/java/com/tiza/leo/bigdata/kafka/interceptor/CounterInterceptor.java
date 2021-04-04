package com.tiza.leo.bigdata.kafka.interceptor;

import org.apache.kafka.clients.producer.ProducerInterceptor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Map;

/**
 * @author leowei
 * @date 2021/4/4  - 8:53
 */
public class CounterInterceptor implements ProducerInterceptor<String,String> {

    int success=0;
    int error=0;

    @Override
    public void configure(Map<String, ?> configs) {

    }

    @Override
    public ProducerRecord<String, String> onSend(ProducerRecord<String, String> record) {
        return record;
    }

    @Override
    public void onAcknowledgement(RecordMetadata metadata, Exception exception) {

        if(metadata!=null){
            success++;
        }else{
            error++;
        }
    }

    @Override
    public void close() {
        System.out.println("success:"+success);
        System.out.println("error:"+error);
    }

}
