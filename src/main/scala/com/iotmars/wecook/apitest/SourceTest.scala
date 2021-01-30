//package com.iotmars.wecook.apitest
//
//import java.util.Properties
//
//import org.apache.flink.api.common.serialization.SimpleStringSchema
//import org.apache.flink.streaming.api.functions.source.SourceFunction
//import org.apache.flink.streaming.api.scala._
//import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011
//import org.apache.kafka.clients.CommonClientConfigs
//import org.apache.kafka.clients.consumer.ConsumerConfig
//import org.apache.kafka.common.serialization.StringDeserializer
//
//import scala.util.Random
//
///**
// * @Description:
// * @Author: CJ
// * @Data: 2020/12/9 17:08
// */
//object SourceTest {
//  // 从kafka中读取流数据
//  //  def main(args: Array[String]): Unit = {
//  //    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
//  //
//  //    // 1.从集合中读取数据
//  //    val properties = new Properties()
//  //    properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"192.168.32.242:9092")
//  //    properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG,"consumer-group")
//  //    properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"latest")
//  //    properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer")
//  //    properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer")
//  //    val stream = env.addSource(new FlinkKafkaConsumer011[String]("sensor", new SimpleStringSchema(), properties))
//  //
//  //    stream.print()
//  //
//  //    env.execute("kafka_source_test")
//  //  }
//
//  // 自定义数据源读取流数据
//  def main(args: Array[String]): Unit = {
//    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
//
//    val stream2 = env.addSource(new MySensorSource())
//
//    stream2.print()
//
//    env.execute("diy_source_test")
//
//  }
//
//}
//
//class MySensorSource() extends SourceFunction[SensorReading] {
//  // 定义一个标志位表示数据源是否正常发出数据
//  var running: Boolean = true
//
//  override def cancel(): Unit = running = false
//
//  override def run(sourceContext: SourceFunction.SourceContext[SensorReading]): Unit = {
//    // 定义一个循环，不停生产数据，除非被cancel
//    val curTemp = 1.to(10).map(i => ("sensor_" + i, Random.nextDouble() * 100))
//
//    while (running) {
//
//      curTemp.map(
//        data => (data._1, data._2 + Random.nextGaussian())
//      )
//      val curTime = System.currentTimeMillis()
//      curTemp.foreach(
//        data => sourceContext.collect(SensorReading(data._1, data._2, curTime))
//      )
//
//      Thread.sleep(1000)
//    }
//  }
//
//}
//
//case class SensorReading(name: String, temp: Double, timestamp: Long)