package com.iotmars.wecook

import org.apache.flink.api.scala.{DataSet, ExecutionEnvironment}
import org.apache.flink.api.scala._

/**
 * @Description:
 * @Author: CJ
 * @Data: 2020/12/9 10:38
 */
object WordCount {
  //  **_  的用法：①包中所有类②系统默认初始化③将函数不执行返回④参数占位符⑤隐藏导入的类⑥标识符⑦绝对路径**⑧case _ 不管什么值都匹配⑨case _:BigInt =>...  当后面不用该变量，不关心变量时，可以用 _ 代替。
  def main(args: Array[String]):Unit = {
    // 创建一个批处理的执行环境
    val env: ExecutionEnvironment = ExecutionEnvironment.getExecutionEnvironment

    // 从文件中读取数据
    val inputPath: String = "C:\\Users\\Administrator\\Desktop\\不常用的项目\\flink_demo\\src\\main\\resources\\test.txt"
    val inputDataSet: DataSet[String] = env.readTextFile(inputPath)

    // 对数据进行转换处理统计，先分词，再按照word进行分组，最后进行聚合统计
    val resultDataSet: DataSet[(String,Int)] = inputDataSet
      .flatMap(_.split(" "))
      .map((_, 1))
      .groupBy(0)  // 以第一个元素作为key进行分组
      .sum(1)

    resultDataSet.print()
  }
}
