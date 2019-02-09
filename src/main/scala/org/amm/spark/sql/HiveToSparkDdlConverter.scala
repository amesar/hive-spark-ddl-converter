package org.amm.spark.sql

import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.execution.SparkSqlParser
import org.apache.spark.sql.internal.{HiveSerDe, SQLConf}
import org.apache.spark.sql.execution.datasources.CreateTable
import org.apache.spark.sql.catalyst.catalog._
import org.apache.spark.sql.SaveMode
import scala.collection.JavaConversions._
import scala.io.Source

object HiveToSparkDdlConverter {

  val serdeMap = Map(
    "org.apache.hadoop.hive.ql.io.parquet.serde.ParquetHiveSerDe" -> "PARQUET",
    "parquet.hive.serde.ParquetHiveSerDe" -> "PARQUET", // hive 12
    "org.apache.hadoop.hive.ql.io.orc.OrcSerde" -> "ORC",
    "org.apache.hadoop.hive.serde2.OpenCSVSerde" -> "CSV"
  )

  def getTableDesc(sql: String): CatalogTable = {
    val parser = new SparkSqlParser(new SQLConf)
    parser.parsePlan(sql).collect {
      case CreateTable(tableDesc, mode, _) => (tableDesc, mode == SaveMode.Ignore)
    }.head._1
  }

  def fmtUsing(serde: Option[String], provider: Option[String], serdeMap: Map[String,String]) : String = {
    serde match {
      case Some(serde) => { 
        if (serde == "org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe") {
          return "HIVE"
        }
        serdeMap.get(serde) match {
          case Some(format) => return format
          case None => throw new Exception(s"Unknown FORMAT SERDE: $serde")
        }
      }
      case None => {
        provider match {
          case Some(a) => {
            a match {
              case "HIVE" => "HIVE"
              case _ => throw new Exception(s"Unknown Provider $provider") // TODO
            }}
          case None => throw new Exception(s"No Provider") // TODO
        }
      }
    }
  }

  def fmtLocation(str: Option[java.net.URI]) = {
    str match {
      case Some(x) => s"LOCATION '$x'\n"
      case None => ""
    }
  }
  
  def fmtColumns(schema: StructType) = {
    schema.fields.map(x => s"  ${x.name} ${x.dataType.catalogString}").mkString(",\n") 
  }
  
  def fmtProperties(props:  Map[String, String]) = {
    if (props.size == 0) "" else "OPTIONS (" + props.map(x => s"'${x._1}' = '${x._2}'").mkString(", ") + ")\n"
  }

  def fmtPartitionColumnNames(columns:  Seq[String]) = {
    if (columns == Seq.empty) "" else "PARTITIONED BY (" + columns.mkString(", ") + ")\n"
  }

  def fmtComment(comment: Option[String]) = {
    comment match {
      case Some(x) => s"COMMENT '$x'\n"
      case None => ""
    }
  }

  def readSerdeMap(path: String) = {
    val lines = Source.fromFile(path).getLines.toSeq
    lines.map(x => (x.split(" ")(0), x.split(" ")(1))).toMap
  }

  def _convert(hiveDDL: String, verbose: Boolean = false) : (CatalogTable,String) = {
    val desc = getTableDesc(hiveDDL)
    if (verbose) println(s"==== DESC:\n$desc")
    if (desc.provider.get.toLowerCase != "hive") {
      if (verbose) println(s"WARNING: table ${desc.identifier} is not in Hive format")
      return (desc,hiveDDL)
    }

    val sparkDDL = new StringBuilder(s"CREATE table IF NOT EXISTS ${desc.identifier} ")
      .append("(\n"+fmtColumns(desc.schema)+")\n")

    val using = fmtUsing(desc.storage.serde,desc.provider,serdeMap)
    sparkDDL.append(s"USING $using\n")
    val map = if (desc.storage.properties.size > 0) desc.storage.properties else
      Map("SERDE" -> desc.storage.serde.get, 
          "INPUTFORMAT" -> desc.storage.inputFormat.get,
          "OUTPUTFORMAT" -> desc.storage.outputFormat.get)
    sparkDDL.append(fmtProperties(map))

    sparkDDL
      .append(fmtPartitionColumnNames(desc.partitionColumnNames))
      .append(fmtLocation(desc.storage.locationUri))
      .append(fmtComment(desc.comment))
    (desc,sparkDDL.toString)
  }

  def convert(hiveDDL: String, verbose: Boolean = false) : String = {
    _convert(hiveDDL, verbose)._2
  }
}
