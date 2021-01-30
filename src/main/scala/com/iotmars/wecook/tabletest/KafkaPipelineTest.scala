package com.iotmars.wecook.tabletest

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.table.api.bridge.scala.StreamTableEnvironment
import org.apache.flink.table.descriptors.Kafka

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/12/18 14:00
 */
object KafkaPipelineTest {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    env.setParallelism(1)


    val tableEnv = StreamTableEnvironment.create(env)

    tableEnv.connect(new Kafka()
    .version("v0.11")
    .topic("sensor")
        .property("zookeeper.connect","192.168.32.242:")
    )

  }
}
