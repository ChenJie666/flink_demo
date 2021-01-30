package com.iotmars.wecook

import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.streaming.api.scala._

/**
 * @Description: 流处理word count
 * @Author: CJ
 * @Data: 2020/12/9 11:02
 */
object StreamWordCount {
  def main(args: Array[String]): Unit = {
    // 创建一个流处理的执行环境  DataStreamApi
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    env.setParallelism(8) // 设置最大并行度，测试环境默认是本机核心数

    // 接收一个socket文本流
    val paramTool: ParameterTool = ParameterTool.fromArgs(args)
    val host: String = paramTool.get("host")
    val port: Int = paramTool.getInt("port")
    val inputDataStream: DataStream[String] = env.socketTextStream(host, port)

    // 进行转化处理统计
    val resultDataStream: DataStream[(String, Int)] = inputDataStream
      .flatMap(_.split(" "))
      .filter(_.nonEmpty)
      .map((_, 1))
      .keyBy(0) // 流处理没有groupBy，使用keyBy进行聚合
      .sum(1)

    resultDataStream.print().setParallelism(1)

    // 定义任务后，开始执行
    env.execute("stream word count")
  }
}
