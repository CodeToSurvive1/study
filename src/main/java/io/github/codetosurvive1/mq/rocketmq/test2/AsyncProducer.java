package io.github.codetosurvive1.mq.rocketmq.test2;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * @author xiaojiao.li
 * @version V1.0
 * @title: 异步producer
 * @description: 异步发送消息
 * @date 2017/11/19 14:12
 */
public class AsyncProducer {
	public static void main(String args[]) throws MQClientException, RemotingException, InterruptedException {
		DefaultMQProducer asyncProducer = new DefaultMQProducer("producerGroup");
		asyncProducer.start();

		asyncProducer.setRetryTimesWhenSendAsyncFailed(0);

		for (int i = 0; i < 100; i++) {

			Message message = new Message("testTopic", ("async message" + i).getBytes());

			final int index = i;
			asyncProducer.send(message, new SendCallback() {
				@Override
				public void onSuccess(SendResult sendResult) {
					System.out.println("produce success " + index);
				}

				@Override
				public void onException(Throwable e) {

					System.out.println("produce failed " + index);
				}
			});
		}

		asyncProducer.shutdown();
	}
}
