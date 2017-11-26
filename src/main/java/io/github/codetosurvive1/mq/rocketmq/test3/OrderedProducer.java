package io.github.codetosurvive1.mq.rocketmq.test3;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.List;

/**
 * @author xiaojiao.li
 * @version V1.0
 * @title: 有序生产者
 * @description: 有序消息
 * @date 2017/11/19 16:57
 */
public class OrderedProducer {
	public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
		DefaultMQProducer orderedProducer = new DefaultMQProducer("producerGroup");
		orderedProducer.start();


		String[] tags = new String[]{"TagA", "TagB", "TagC", "TagD", "TagE"};

		for (int i = 0; i < 100; i++) {
			int orderId = i % 10;

			Message message = new Message("TopicTestjjj", tags[i % tags.length],
					"key" + i, ("hello rocketmq" + i).getBytes());

			SendResult result = orderedProducer.send(message, new MessageQueueSelector() {
				@Override
				public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
					System.out.println(arg);
					//这里的arg 参数为send的第三个参数orderId
					Integer id = (Integer) arg;
					int index = id % mqs.size();
					return mqs.get(index);
				}
			}, orderId);

			System.out.println("send result:" + result);
		}
		orderedProducer.shutdown();
	}
}
