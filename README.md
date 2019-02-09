# hive-spark-ddl-converter

Converts Hive DDL to Spark DDL.

This is an exploratory first-pass Hive-to-Spark DDL converter that translates a subset of Hive DDL to Spark DDL.

## Build

```
mvn package
```

Test report: [target/surefire-reports/index.html](target/surefire-reports/index.html).

## Run 

### Convert Hive DDL file
```
spark-submit --class org.amm.spark.sql.ConvertHiveFile --master local[2] \
  target/hive-spark-ddl-converter-1.0-SNAPSHOT.jar \
  --hiveFile src/test/resources/hive_ddl/ok/simple_parquet.ddl \
  --sparkOutputDir .
```

HIVE DDL
```
create external table simple (
  id int,
  name string)   
STORED AS PARQUET
PARTITIONED BY (day int) 
LOCATION '/tmp/simple'
```

Spark DDL
```
CREATE table IF NOT EXISTS `simple` (
  id int,
  name string,
  day int)
USING PARQUET
PARTITIONED BY (day)
LOCATION '/tmp/simple'
```

### Convert Hive DDL directory

Converts a directory containing Hive DDL files to Spark DDL files.

```
spark-submit --class org.amm.spark.sql.ConvertHiveDirectory --master local[2] \
  target/hive-spark-ddl-converter-1.0-SNAPSHOT.jar \
  --hiveInputDir src/test/resources/hive_ddl/ok \
  --sparkOutputDir out \
  --extension ddl
```

```
hiveFile:    src/test/resources/hive_ddl/ok/simple_parquet.ddl
  sparkFile: out/simple_parquet.ddl
hiveFile:    src/test/resources/hive_ddl/ok/simple_orc.ddl
  sparkFile: out/simple_orc.ddl
```

## DDL Files

* Hive files that can be converted: [src/test/resources/hive_ddl/ok](src/test/resources/hive_ddl/ok)
  * Spark equivalents: [src/test/resources/spark_ddl](src/test/resources/spark_ddl)
* Hive files that can't yet be converted: [src/test/resources/hive_ddl/notyet](src/test/resources/hive_ddl/notyet)

## Resources

* [LanguageManual DDL - Hive documentation](https://cwiki.apache.org/confluence/display/Hive/LanguageManual+DDL)
* [Create Table - Databricks documentation](https://docs.databricks.com/spark/latest/spark-sql/language-manual/create-table.html)
* [Create Table Statement - Cloudera Documentation](https://www.cloudera.com/documentation/enterprise/6/6.1/topics/impala_create_table.html)

