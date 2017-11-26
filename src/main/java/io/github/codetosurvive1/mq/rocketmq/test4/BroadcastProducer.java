package io.github.codetosurvive1.mq.rocketmq.test4;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * @author xiaojiao.li
 * @version V1.0
 * @title: 生产者
 * @description: 消息生产者
 * @date 2017/11/26 10:57
 */
public class BroadcastProducer {


    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {

        DefaultMQProducer broadcastProducer = new DefaultMQProducer("broadcastGroup");

        broadcastProducer.start();

        int length = 2;
        for (int i = 0; i < length; i++) {
            Message message = new Message("broadcastmq", ("broadcast message " + i).getBytes());
            SendResult result = broadcastProducer.send(message);
            System.out.println(result);
        }

        broadcastProducer.shutdown();


    }
}
