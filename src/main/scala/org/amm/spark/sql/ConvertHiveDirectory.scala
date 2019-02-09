package org.amm.spark.sql

import java.io.File
import scala.io.Source
import org.apache.spark.sql.SparkSession
import com.beust.jcommander.{JCommander, Parameter}

object ConvertHiveDirectory {

  def main(args: Array[String]) {
    val opts = new Options()
    new JCommander(opts, args.toArray: _*)
    convert(opts.hiveInputDir, opts.sparkOutputDir, opts.extension, opts.verbose)
  }

  def convert(hiveDirname: String, sparkDirname: String, extension: String = "ddl", verbose: Boolean = false) {
    val hiveDir = new File(hiveDirname)
    val sparkDir = new File(sparkDirname)
    println("Arguments:")
    println(s"  hiveDir: $hiveDir")
    println(s"  sparkDir: $sparkDir")
    println(s"  extension: $extension")
    println(s"  verbose: $verbose")
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

  class Options {
    @Parameter(names = Array("--hiveInputDir" ), description = "hiveInputDir", required=true)
    var hiveInputDir = "."

    @Parameter(names = Array("--sparkOutputDir" ), description = "sparkOutputDir", required=true)
    var sparkOutputDir = "."

    @Parameter(names = Array("--extension" ), description = "extension", required=false)
    var extension = "ddl"

    @Parameter(names = Array("--verbose" ), description = "verbose", required=false)
    var verbose = false
  }
}
