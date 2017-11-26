package io.github.codetosurvive1.mq.rocketmq.test2;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * @author xiaojiao.li
 * @version V1.0
 * @title: 单方向发送消息
 * @description: 只负责发送消息，不等待服务器回应并且没有回调函数，对可靠性要求不高的场景，如日志收集
 * @date 2017/11/19 14:31
 */
public class OneWayProducer {

	public static void main(String[] arg) throws MQClientException, RemotingException, InterruptedException {

		DefaultMQProducer oneWayProducer = new DefaultMQProducer("producerGroup");

		oneWayProducer.start();


		for (int i = 0; i < 100; i++) {
			Message message = new Message("testTopic", ("one way message " + i).getBytes());
			oneWayProducer.sendOneway(message);
		}


		oneWayProducer.shutdown();
	}

}
