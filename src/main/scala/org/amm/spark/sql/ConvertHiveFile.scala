package org.amm.spark.sql

import java.io.File
import scala.io.Source
import org.apache.spark.sql.SparkSession
import com.beust.jcommander.{JCommander, Parameter}

object ConvertHiveFile {
  def main(args: Array[String]) {
    val opts = new Options()
    new JCommander(opts, args.toArray: _*)
    println("Options:")
    println(s"  hiveFile: ${opts.hiveFile}")
    println(s"  sparkOutputDir: ${opts.sparkOutputDir}")
    println(s"  parseSpark: ${opts.parseSpark}")
    println(s"  verbose: ${opts.verbose}")

    val spark = SparkSession.builder().appName("ConvertHiveFile").getOrCreate()
    println(s"spark.version: ${spark.version}")
    println(s"hiveFile: ${opts.hiveFile}")

    val hiveDDL = Source.fromFile(opts.hiveFile).mkString
    println(s"==== HiveDDL:\n\n$hiveDDL")

    val sparkDDL = HiveToSparkDdlConverter.convert(hiveDDL,opts.verbose)
    println(s"==== SparkDDL:\n\n$sparkDDL")

    if (opts.parseSpark) {
      val sparkDDL2 = HiveToSparkDdlConverter.convert(sparkDDL,opts.verbose)
      println(s"==== sparkDDL2:\n$sparkDDL2")
    }

    val sparkPath = new File(opts.sparkOutputDir,new File(opts.hiveFile).getName)
    new java.io.PrintWriter(sparkPath) { write(sparkDDL) ; close }

    println(s"hiveFile : ${opts.hiveFile}")
    println(s"sparkDdlPath: $sparkPath")
  }

  class Options {
    @Parameter(names = Array("--hiveFile" ), description = "hiveFile", required=true)
    var hiveFile = ""

    @Parameter(names = Array("--sparkOutputDir" ), description = "sparkOutputDir", required=false)
    var sparkOutputDir = "."

    @Parameter(names = Array("--parseSpark" ), description = "parseSpark", required=false)
    var parseSpark = false

    @Parameter(names = Array("--verbose" ), description = "verbose", required=false)
    var verbose = false
  }

}
