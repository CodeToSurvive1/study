package io.github.codetosurvive1.mq.rocketmq.test1;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * @author xiaojiao.li
 */
public class Producer {

	public static void main(String args[]) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {

		DefaultMQProducer producer = new DefaultMQProducer("producerGroup");

		producer.start();

		for (int i = 0; i < 2; i++) {
			Message message = new Message("testTopic", "produce", ("message" + i).getBytes());
			SendResult result = producer.send(message);
			System.out.println("produce message:" + result);
		}

		producer.shutdown();
	}

}
