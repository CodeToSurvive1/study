package io.github.codetosurvive1.mq.rocketmq.test5;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * @author xiaojiao.li
 * @version V1.0
 * @title: 调度消息生产者
 * @description:消息生产者
 * @date 2017/11/26 12:19
 */
public class ScheduleMessageProducer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer scheduleMessageProducer = new DefaultMQProducer("scheduleGroup");
        scheduleMessageProducer.start();

        int totalMessageLength = 2;
        for (int i = 0; i < totalMessageLength; i++) {
            Message message = new Message("schedulemq", ("schedule message" + i).getBytes());
            message.setDelayTimeLevel(3);
            SendResult result = scheduleMessageProducer.send(message);
            System.out.println(result);
        }
    }
}
