package io.github.codetosurvive1.mq.rocketmq.test3;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author xiaojiao.li
 * @version V1.0
 * @title: 有序消费
 * @description: 有序消息
 * @date 2017/11/19 17:26
 */
public class OrderedConsumer {

	public static void main(String[] args) throws MQClientException {
		DefaultMQPushConsumer orderedConsumer = new DefaultMQPushConsumer("consumerGroup");
		orderedConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

		orderedConsumer.subscribe("TopicTestjjj", "TagA || TagC || TagD");

		orderedConsumer.registerMessageListener(new MessageListenerOrderly() {

			AtomicLong consumeTimes = new AtomicLong(0);

			@Override
			public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
				context.setAutoCommit(false);

				System.out.println("current thread " + Thread.currentThread().getName() + " receives message :" + msgs);

				this.consumeTimes.incrementAndGet();

				if ((this.consumeTimes.get() % 2) == 0) {
					return ConsumeOrderlyStatus.SUCCESS;
				} else if ((this.consumeTimes.get() % 3) == 0) {
					return ConsumeOrderlyStatus.ROLLBACK;
				} else if ((this.consumeTimes.get() % 4) == 0) {
					return ConsumeOrderlyStatus.COMMIT;
				} else if ((this.consumeTimes.get() % 5) == 0) {
					context.setSuspendCurrentQueueTimeMillis(3000);
					return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
				}

				return ConsumeOrderlyStatus.SUCCESS;
			}
		});

		orderedConsumer.start();
	}
}
