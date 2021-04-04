package com.tiza.leo.bigdata.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Properties;

/**
 * @author leowei
 * @date 2021/4/4  - 8:56
 */
public class InterceptorProducer {
    Properties props = new Properties();

    public static void main(String[] args) {
        InterceptorProducer producer = new InterceptorProducer();
        producer.loadProp();
        producer.sendMsg();
    }

    private void loadProp() {
        //指定kafka链接集群
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka01:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        // 添加分区器   PARTITIONER_CLASS_CONFIG = "partitioner.class";
        //props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "com.tiza.leo.bigdata.kafka.partitioner.MyPartitioner");

        //添加拦截器
        ArrayList interceptorList = new ArrayList();
        interceptorList.add("com.tiza.leo.bigdata.kafka.interceptor.TimeInterceptor");
        interceptorList.add("com.tiza.leo.bigdata.kafka.interceptor.CounterInterceptor");
        //interceptor.classes
       props.put(ProducerConfig.INTERCEPTOR_CLASSES_CONFIG, interceptorList);

    }
    private void sendMsg() {
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 10; i++) {
            ProducerRecord<String, String> record = new ProducerRecord<>("first", "tizaKey", "tizaValue---" + i);
            producer.send(record);
        }
        System.out.println("0304");
        try {
            System.out.println("===============");
            Thread.sleep(2222);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // 关闭资源  且要  调用拦截器中的close 方法
         producer.close();
        System.out.println("0305");
    }
}
