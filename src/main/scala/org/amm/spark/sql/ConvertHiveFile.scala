package org.amm.spark.sql

import java.io.File
import scala.io.Source
import org.apache.spark.sql.SparkSession

object ConvertHiveFile {
  def main(args: Array[String]) {
    if (args.size < 1) {
      println("ERROR: Expecting HIVE_DDL_FILE SPARK_DDL_OUTPUT_DIRECTORY VERBOSE")
      System.exit(1)
    }
    val hivePath = args(0)
    val sparkOutputDir = if (args.size > 1) args(1) else "."
    val verbose = if (args.size > 2) args(2).toBoolean else false

    val spark = SparkSession.builder().appName("ConvertHiveFile").getOrCreate()
    println(s"spark.version: ${spark.version}")
    println(s"hiveDdlPath: $hivePath")

    val hiveDDL = Source.fromFile(hivePath).mkString
    println(s"==== HiveDDL:\n\n$hiveDDL")

    val sparkDDL = HiveToSparkDdlConverter.convert(hiveDDL,verbose)
    println(s"==== SparkDDL:\n\n$sparkDDL")

    val sparkPath = new File(sparkOutputDir,new File(hivePath).getName)
    new java.io.PrintWriter(sparkPath) { write(sparkDDL) ; close }

    println(s"hiveDdlPath : $hivePath")
    println(s"sparkDdlPath: $sparkPath")
  }
}
