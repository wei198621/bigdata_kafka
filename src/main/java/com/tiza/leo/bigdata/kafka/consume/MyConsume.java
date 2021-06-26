package com.tiza.leo.bigdata.kafka.consume;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import javax.lang.model.element.VariableElement;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

/**
 * @author leowei
 * @date 2021/4/3  - 22:58
 */
public class MyConsume {
    Properties prop = new Properties();

    public static void main(String[] args) {
        MyConsume myConsume = new MyConsume();
        myConsume.loadProp();
        myConsume.recvMsg();
    }

    private void loadProp(){
        //bootstrap.servers      链接的集群
        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka01:9092");
        //enable.auto.commit    开启自动提交
        prop.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        //auto.commit.interval.ms    自动提交延时
        prop.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1000);
        //     key value  反序列化
        prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        //配置消费者组
        prop.put(ConsumerConfig.GROUP_ID_CONFIG, "consumerGroupConfigTest01");

    }


    private void recvMsg() {
        //创建消费者
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(prop);
        //订阅主题  可以订阅没有的主题，自动回新建的
        consumer.subscribe(Arrays.asList("topic03","second","third","fourth"));
        //获取(拉取)数据   批量拉取
        while (true){
            ConsumerRecords<String, String> consumerRecords = consumer.poll(10);
            //解析并打印数据
            for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
            }
            consumerRecords.forEach(record-> System.out.println(record.topic()
                    +"---"+record.offset()
                    +"---"+record.key()
                    +"---" + record.value()));
        }
        //关闭链接
        //consumer.close();

    }
}
