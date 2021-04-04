package com.tiza.leo.bigdata.kafka.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @author leowei
 * @date 2021/4/3  - 17:20
 */
public class CallbackProducer {

    Properties props = new Properties();


    public static void main(String[] args) {
        CallbackProducer producer = new CallbackProducer();
        producer.loadProp();
        producer.sendMsg();
    }

    private void loadProp() {
        //指定kafka链接集群
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka01:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

    }


    private void sendMsg(){
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
        for (int i = 0; i < 10; i++) {
            ProducerRecord<String, String> record = new ProducerRecord<String, String>("aaa",0,"tiza", "tiza---" + i);
            producer.send(record,( metadata,  e) ->{
                    if(e==null){
                        System.out.println("topic:"+metadata.topic()+"---- partition:"+metadata.partition()+"--offset:"+metadata.offset() );
                    }
                } );
           /* producer.send(record, new Callback() {
                public void onCompletion(RecordMetadata metadata, Exception e) {
                    if(e==null){
                        System.out.println(metadata.topic()+"----"+metadata.partition()+"--"+metadata.offset());
                    }
                }
            });*/
        }

        producer.close();

    }

}
