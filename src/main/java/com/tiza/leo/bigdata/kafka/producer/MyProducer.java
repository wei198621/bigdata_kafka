package com.tiza.leo.bigdata.kafka.producer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @author leowei
 * @date 2021/4/3  - 12:01
 */

public class MyProducer {
    Properties props = new Properties();

    //要写一个客户端  往kafka 扔对象
    public static void main(String[] args) throws InterruptedException {
        System.out.println("11111");
        MyProducer myProducer = new MyProducer();
        System.out.println("222222");
        myProducer.loadProp();
        System.out.println("333333333");
       // Thread.sleep(5000);
        myProducer.sendMsg();
        System.out.println("444444444");

    }

    private void loadProp(){
        //指定kafka链接集群
       // props.put("bootstrap.servers","192.168.121.131:9092");
        props.put("bootstrap.servers","kafka02:9092");
        //ack应答级别
        props.put("acks", "all");
        //重试次数
        props.put("retries", "1");
        //批次大小
        props.put("batch.size", "16384");
        //等待时间
        props.put("linger.ms", 1);
        //RecordAccumulator缓冲区大小
        props.put("buffer.memory", 33554432);
        //key  value 的序列化类
        props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

    }

    //上面的prop.put 中的Key 都是字符串，不能保证一定可以写对，kafka给我们提供了一个类ProducerConfig
    //ProducerConfig上面记录了所有的静态字段 key ，方便使用
    private void loadProp2(){

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka02:9092");
        // ...
        /*

    public class ProducerConfig extends AbstractConfig {
    private static final ConfigDef CONFIG;
    public static final String BOOTSTRAP_SERVERS_CONFIG = "bootstrap.servers";
    public static final String METADATA_MAX_AGE_CONFIG = "metadata.max.age.ms";
    public static final String BATCH_SIZE_CONFIG = "batch.size";
    ...
         */
    }


    private void sendMsg() throws InterruptedException {
        System.out.println("===================  start  ================");
        //创建生产者对象
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(props);
        //发送数据  10 次
        for (int i = 0; i < 10; i++) {
            // first   这个topic 需要先创建
            ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>("bigdata", "key----" + Integer.toString(i), "value----" + Integer.toString(i));
            System.out.println("i is :"+i);
            kafkaProducer.send(producerRecord);
        }
        ProducerRecord<String, String> producerRecordEnd = new ProducerRecord<String, String>("bigdata", "-----------------  end ----------------------");
        kafkaProducer.send(producerRecordEnd);

        // 关闭链接  会触发将没有发送的数据发送
        kafkaProducer.close();

        //注意需要在后台开启一个消费者消费此生产者的数据
         System.out.println("==================  end  =================");
    }
}
