package com.tiza.leo.bigdata.kafka.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;

/**
 * @author leowei
 * @date 2021/4/3  - 18:22
 */
public class PartitionProducer {

    Properties props = new Properties();

    public static void main(String[] args) {
        PartitionProducer producer = new PartitionProducer();
        producer.loadProp();
        producer.sendMsg();
    }

    private void loadProp() {
        //指定kafka链接集群
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka01:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        // 添加分区器   PARTITIONER_CLASS_CONFIG = "partitioner.class";
        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "com.tiza.leo.bigdata.kafka.partitioner.MyPartitioner");
    }
    private void sendMsg() {
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 10; i++) {
            //ProducerRecord<String, String> record2 = new ProducerRecord<>("aaa",0, "tiza", "tiza---" + i);
            ProducerRecord<String, String> record = new ProducerRecord<>("topic03", "tiza", "tiza---" + i);
            producer.send(record, (metadata, e) -> {
                if (e == null) {
                    System.out.println(metadata.topic() + "----" + metadata.partition() + "--" + metadata.offset());
                }else
                {
                    System.out.println(e.getMessage());
                }
            });
        }
        System.out.println("0304");
        producer.close();
        System.out.println("0305");
    }

}
