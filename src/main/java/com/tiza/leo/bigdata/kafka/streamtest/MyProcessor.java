package com.tiza.leo.bigdata.kafka.streamtest;

import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorContext;

/**
 * @author leowei
 * @date 2021/4/5  - 0:22
 */
public class MyProcessor implements Processor<byte[],byte[]> {
    ProcessorContext context;

    @Override
    public void init(ProcessorContext processorContext) {
        context = processorContext;
    }

    @Override
    public void process(byte[] bytes, byte[] bytes2) {
        //获取一样数据
        String line = new String(bytes2);
        // delete >>>
        line = line.replace(">>>", "");
        bytes2 = line.getBytes();
        context.forward(bytes, bytes2);
    }



    @Override
    public void punctuate(long l) {

    }

    @Override
    public void close() {

    }
}
