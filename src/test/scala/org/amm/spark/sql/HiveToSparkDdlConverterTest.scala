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

  def testFile(file: File) {
    println(s"==== file: $file")
    val hiveDdl = Source.fromFile(file.getAbsolutePath).mkString
    println(s"hiveDdl:\n$hiveDdl")

    val sparkDdl = HiveToSparkDdlConverter.convert(hiveDdl)
    println(s"sparkDdl:\n$sparkDdl")

    val sparkDdl2 = HiveToSparkDdlConverter.getTableDesc(sparkDdl)
    println(s"sparkDdl2:\n$sparkDdl2")

    val sparkDdlRef = Source.fromFile(new File(sparkDir,file.getName)).mkString
    println(s"sparkDdlRef:\n$sparkDdlRef")

    equals(sparkDdlRef,sparkDdl)
  }

  @DataProvider(name= "okFiles")
  def okFiles = TestNgUtils.buildDataProviderArray(new File(hiveDir,"ok").listFiles())
  @Test(dataProvider = "okFiles")
  def testOkFile(file: File) = testFile(file)

  @DataProvider(name= "badFiles")
  def badFiles = TestNgUtils.buildDataProviderArray(new File(hiveDir,"bad").listFiles())
  @Test(dataProvider = "badFiles", expectedExceptions = Array(classOf[Exception]))
  def testBadFile(file: File) = testFile(file)

  @DataProvider(name= "notyetFiles")
  def notyetFiles = TestNgUtils.buildDataProviderArray(new File(hiveDir,"notyet").listFiles())
  @Test(dataProvider = "notyetFiles", expectedExceptions = Array(classOf[Exception]))
  def testNotyetFile(file: File) = testFile(file)
}
