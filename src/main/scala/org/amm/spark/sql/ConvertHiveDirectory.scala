package org.amm.spark.sql

import java.io.File
import scala.io.Source
import org.apache.spark.sql.SparkSession

object ConvertHiveDirectory {
  val verbose = false

  def main(args: Array[String]) {
    if (args.size < 3) {
      println("ERROR: Expecting HIVE_DDL_DIRECTORY SPARK_DDL_OUTPUT_DIRECTORY EXTENSION")
      System.exit(1)
    }
    convert(args(0), args(1),  args(2))
  }

  def convert(hiveDirname: String, sparkDirname: String, extension: String) {
    val hiveDir = new File(hiveDirname)
    val sparkDir = new File(sparkDirname)
    println("Arguments:")
    println(s"  hiveDir: $hiveDir")
    println(s"  sparkDir: $sparkDir")
    println(s"  extension: $extension")
    if (!hiveDir.exists) {
      throw new java.io.FileNotFoundException(s"$hiveDir does not exist")
    }
    val spark = SparkSession.builder().appName("ConvertHiveDirectory").getOrCreate()
    println(s"spark.version: ${spark.version}")

    val hiveFiles = hiveDir.listFiles().filter(x => { x.isFile && x.getName.endsWith("."+extension) })
    for (hiveFile <- hiveFiles) {
      println(s"hiveFile:    $hiveFile")

      val hiveDDL = Source.fromFile(hiveFile).mkString
      //println(s"==== HiveDDL:\n\n$hiveDDL")

      val sparkDDL = HiveToSparkDdlConverter.convert(hiveDDL,verbose)
      //println(s"==== SparkDDL:\n\n$sparkDDL")

      val sparkFile = new File(sparkDir,hiveFile.getName)
      println(s"  sparkFile: $sparkFile")
      new java.io.PrintWriter(sparkFile) { write(sparkDDL) ; close }
    }
  }
}
