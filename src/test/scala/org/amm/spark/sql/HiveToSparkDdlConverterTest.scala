package org.amm.spark.sql

import scala.io.Source
import java.io.File
import org.testng.annotations._
import org.testng.Assert._
import org.apache.spark.sql.SparkSession

class HiveToSparkDdlConverterTest {
  val spark = SparkSession.builder().appName("Driver").master("local[2]").getOrCreate()
  println(s"spark.version: ${spark.version}")
  val hiveDir = new File("src/test/resources/hive_ddl")
  val sparkDir = new File("src/test/resources/spark_ddl")
  val verbose = true

  def testFile(file: File) {
    println(s"==== file: $file")
    val hiveDdl = Source.fromFile(file.getAbsolutePath).mkString
    println(s"hiveDdl:\n$hiveDdl")

    val sparkDdl = HiveToSparkDdlConverter.convert(hiveDdl,verbose)
    println(s"sparkDdl:\n$sparkDdl")

    val sparkDdl2 = HiveToSparkDdlConverter.getTableDesc(sparkDdl)
    println(s"sparkDdl2:\n$sparkDdl2")

    val sparkPath = new File(sparkDir,file.getName)
    if (sparkPath.exists) {
      val sparkDdlRef = Source.fromFile(sparkPath).mkString
      println(s"sparkDdlRef:\n$sparkDdlRef")
      equals(sparkDdlRef,sparkDdl)
    } else {
      println(s"WARNING: No $sparkPath for $file")
    }
  }

  def getDdlFiles(d1: File, d2: String) = {
    val files = new File(d1,d2).listFiles().filter(x => { x.isFile && x.getName.endsWith(".ddl") })
    TestNgUtils.buildDataProviderArray(files)
  }

  @DataProvider(name= "okFiles")
  def okFiles = getDdlFiles(hiveDir,"ok")
  @Test(dataProvider = "okFiles")
  def testOkFile(file: File) = testFile(file)

  @DataProvider(name= "badFiles")
  def badFiles = getDdlFiles(hiveDir,"bad")
  @Test(dataProvider = "badFiles", expectedExceptions = Array(classOf[Exception]))
  def testBadFile(file: File) = testFile(file)

  @DataProvider(name= "notyetFiles")
  def notyetFiles = getDdlFiles(hiveDir,"notyet")
  @Test(dataProvider = "notyetFiles", expectedExceptions = Array(classOf[Exception]))
  def testNotyetFile(file: File) = testFile(file)
}
