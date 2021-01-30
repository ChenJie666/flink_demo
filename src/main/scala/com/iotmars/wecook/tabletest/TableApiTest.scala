package com.iotmars.wecook.tabletest

import org.apache.flink.streaming.api.scala._
import org.apache.flink.table.api.bridge.scala.StreamTableEnvironment
import org.apache.flink.table.api.{DataTypes, EnvironmentSettings, Table}
import org.apache.flink.table.descriptors.{Csv, FileSystem, Kafka, Schema}


/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/12/17 14:36
 */
object TableApiTest {

  def main(args: Array[String]): Unit = {
    // 1. 创建环境
    val bsEnv = StreamExecutionEnvironment.getExecutionEnvironment
    val bsSettings = EnvironmentSettings.newInstance().useBlinkPlanner().inStreamingMode().build()
    val bsTableEnv = StreamTableEnvironment.create(bsEnv, bsSettings)


    val path = "C:\\Users\\Administrator\\Desktop\\不常用的项目\\flink_demo\\src\\main\\resources\\test.txt";
    bsTableEnv
      .connect(new FileSystem().path(path))
      .withFormat(new Csv())
      .withSchema(new Schema()
        .field("name", DataTypes.STRING())
        .field("age", DataTypes.INT()))
      .createTemporaryTable("fileInputTable")

    val fileInputTable: Table = bsTableEnv.from("fileInputTable")
    fileInputTable.executeInsert("fileInputTable")
//    val dataStream = bsTableEnv.toAppendStream[(String, Int)](fileInputTable)
//    dataStream.print()


    // 2.2 从Kafka读取数据，进行表注册并读取数据
    //    bsTableEnv
    //      .connect(new Kafka()
    //        .version("0.11")
    //        .topic("sensor")
    //        .property("zookeeper.connect", "192.168.32.242:2181")
    //        .property("bootstrap.servers", "192.168.32.242:9092")
    //      )
    //      .withFormat(new Csv())
    //      .withSchema(new Schema()
    //        .field("name", DataTypes.STRING())
    //        .field("age", DataTypes.INT())
    //      )
    //      .createTemporaryTable("kafkaInputTable")
    //
    //    val kafkaInputTable = bsTableEnv.from("kafkaInputTable")
    //    val dataStream = bsTableEnv.toAppendStream[(String, Int)](kafkaInputTable)
    //    dataStream.print()

    bsEnv.execute("table api test")
  }
}
