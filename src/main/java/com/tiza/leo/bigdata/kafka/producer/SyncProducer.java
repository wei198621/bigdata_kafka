package com.tiza.leo.bigdata.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * @author leowei
 * @date 2021/4/3  - 18:53
 */
public class SyncProducer {

    private long times=10000;
    Properties props = new Properties();

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        SyncProducer producer = new SyncProducer();
        producer.loadProp();

        //异步发送  10000条用时 750 毫秒
        long startTime =System.currentTimeMillis();  //  获取当前时间戳
        producer.sendMsgAsync();
        Long Time1 = (System.currentTimeMillis() - startTime);
        System.out.println("producer.sendMsgAsync()  " + Time1);

        //producer.sendMsgAsync()  750
        //producer.sendMsgSync()  22863

        //同步 发送  10000 条数据用时  22 秒
        long startTime2 =System.currentTimeMillis();  //  获取当前时间戳
        producer.sendMsgSync();
        Long Time2 = (System.currentTimeMillis() - startTime2) ;
        System.out.println("producer.sendMsgSync()  " + Time2);
    }

    private void loadProp() {
        //指定kafka链接集群
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka01:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        // 添加分区器   PARTITIONER_CLASS_CONFIG = "partitioner.class";
        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "com.tiza.leo.bigdata.kafka.partitioner.MyPartitioner");
    }
    private void sendMsgAsync() {
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < times; i++) {
            ProducerRecord<String, String> record2 = new ProducerRecord<>("aaa", "tiza22222", "tiza---" + i);
            producer.send(record2, (metadata, e) -> {
                if (e == null) {
                  //  System.out.println(metadata.topic() + "----" + metadata.partition() + "--" + metadata.offset());
                }
            });
        }
        producer.close();
    }

    //同步的形式发送数据  ---不常用
    private void sendMsgSync() throws ExecutionException, InterruptedException {
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < times; i++) {
            ProducerRecord<String, String> record2 = new ProducerRecord<>("aaa", "tiza22222", "tiza---" + i);
            producer.send(record2, (metadata, e) -> {
                if (e == null) {
                  //  System.out.println(metadata.topic() + "----" + metadata.partition() + "--" + metadata.offset());
                }
            }).get();
        }
        producer.close();
    }

}
