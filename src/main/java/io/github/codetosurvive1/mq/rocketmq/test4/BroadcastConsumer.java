package io.github.codetosurvive1.mq.rocketmq.test4;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * @author xiaojiao.li
 * @version V1.0
 * @title: 广播模式消费
 * @description: 消费信息
 * @date 2017/11/26 11:05
 */
public class BroadcastConsumer {
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer broadcastConsumer = new DefaultMQPushConsumer("broadcast");
        broadcastConsumer.setMessageModel(MessageModel.BROADCASTING);
        broadcastConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        broadcastConsumer.subscribe("broadcastmq", "*");
        broadcastConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.println(Thread.currentThread().getName() + " consumer " + msgs);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        broadcastConsumer.start();
    }
}
