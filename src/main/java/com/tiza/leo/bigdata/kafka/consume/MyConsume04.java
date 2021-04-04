package com.tiza.leo.bigdata.kafka.consume;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.consumer.OffsetCommitCallback;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

/**
 * @author leowei
 * @date 2021/4/3  - 22:58
 */
public class MyConsume04 {
    Properties prop = new Properties();

    public static void main(String[] args) {
        MyConsume04 myConsume = new MyConsume04();
        myConsume.loadProp();
        myConsume.recvMsg();
    }

    private void loadProp(){
        //bootstrap.servers      链接的集群
        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka01:9092");
        //enable.auto.commit    关闭自动提交
        prop.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);       // 变化点
        //auto.commit.interval.ms    自动提交延时
        prop.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1000);

        // 如何重新消费一组消息  1  换个组名 ，2 配置从earliest 开始消费 
        {
            //配置消费者组
            prop.put(ConsumerConfig.GROUP_ID_CONFIG, "con_bigdata");
            //重置消费者的offset  auto.offset.reset   ;  latest （默认）将offset设置为最近     earliest 将offset设置为最开始；
            prop.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        }

        //     key value  反序列化
        prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");



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


        //异步提交
        consumer.commitAsync(new OffsetCommitCallback() {
            @Override
            public void onComplete(Map<TopicPartition,
                                OffsetAndMetadata> offsets, Exception exception) {
                if (exception != null) {
                    System.err.println("Commit failed for" +
                            offsets);
                }
            }
        });

        }
        //关闭链接
        //consumer.close();
    }
}
