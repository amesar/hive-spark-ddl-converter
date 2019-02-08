# hive-spark-ddl-converter

Converts Hive DDL to Spark DDL.
This is a first-pass Hive-to-Spark DDL converter.

## Build

```
mvn package
```

Test report: [target/surefire-reports/index.html](target/surefire-reports/index.html).

## Run Example
```
spark-submit --class org.amm.spark.sql.ConvertHiveFile --master local[2] \
  target/hive-spark-ddl-converter-1.0-SNAPSHOT.jar \
  src/test/resources/hive_ddl/ok/simple_parquet.ddl
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

## DDL Files

* Hive files that can be converted: [src/test/resources/hive_ddl/ok](src/test/resources/hive_ddl/ok)
  * Spark equivalents: [src/test/resources/spark_ddl](src/test/resources/spark_ddl)
* Hive files that can't yet be converted: [src/test/resources/hive_ddl/notyet](src/test/resources/hive_ddl/notyet)

