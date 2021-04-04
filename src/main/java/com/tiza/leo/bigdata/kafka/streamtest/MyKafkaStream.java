package com.tiza.leo.bigdata.kafka.streamtest;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorSupplier;
import org.apache.kafka.streams.processor.TopologyBuilder;

import java.util.Properties;

/**
 * @author leowei
 * @date 2021/4/5  - 0:06
 */
public class MyKafkaStream {
    public static void main(String[] args) {
        //创建拓扑对象
        TopologyBuilder builder = new TopologyBuilder();
        //创建配置文件
        Properties props = new Properties();
        props.put("application.id", "kafkaStream");
        props.put("bootstrap.servers", "kafka01:9092");

        //构建拓扑结构
        builder.addSource("SOURCE","first")
                .addProcessor("PROCESSOR", () -> new MyProcessor(), "SOURCE")
                .addSink("SINK","second","PROCESSOR")
                ;


        KafkaStreams kafkaStreams = new KafkaStreams(builder,props);
        kafkaStreams.start();
    }
}
