# hive-spark-ddl-converter

Converts Hive DDL to Spark DDL.

## Example
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
