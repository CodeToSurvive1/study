package io.github.codetosurvive1.mq.rocketmq.test1;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;

/**
 * @author xiaojiao.li
 */
public class Consumer {

	public static void main(String[] args) throws MQClientException {

		DefaultMQPushConsumer pushConsumer = new DefaultMQPushConsumer("pushConsumerGroup");
		pushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

		pushConsumer.subscribe("testTopic", "*");


		pushConsumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {

			//默认每次msgs只有一条消息
			System.out.printf("%s receive new messages %s %d %n:", Thread.currentThread().getName(), msgs, msgs.size());
			//rocketmq服务端知道消息消费成功
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;

			//如果异常之类的场景，则返回ConsumeConcurrentlyStatus.RECONSUME_LATER稍后重试，则rocketmq会将
			//消息重新放回broker，不过topic不是原先的topic，而是放到retry topic，在延迟的时间点到来时候会再次投递默认为10s
			//如果投递16次都失败，则会放入到DLQ死信队列
		});
		pushConsumer.start();

		System.out.printf("Consumer started . %n");
	}

}
