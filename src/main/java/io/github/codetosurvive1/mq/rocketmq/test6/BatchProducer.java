package io.github.codetosurvive1.mq.rocketmq.test6;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaojiao.li
 * @version V1.0
 * @title: 批量提交
 * @description: 批量操作
 * @date 2017/11/26 12:40
 */
public class BatchProducer {

    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer batchProducer = new DefaultMQProducer("batchGroup");
        batchProducer.start();

        int batchSize = 10;

        //总大小不能超过1M，具有相同的topic，相同的waitStoreMsgOK，都没有调度
        List<Message> batchList = new ArrayList<Message>(batchSize);
        for (int i = 0; i < batchSize; i++) {
            Message message = new Message("batchmq", "i".getBytes());
            batchList.add(message);
        }

        SendResult result = batchProducer.send(batchList);
        System.out.println(result);
    }
}
