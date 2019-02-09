CREATE table IF NOT EXISTS `parquet_test` (
  id int,
  str string,
  mp map<string,string>,
  lst array<string>,
  strct struct<A:string,B:string>,
  part string)
USING PARQUET
OPTIONS ('SERDE' = 'org.apache.hadoop.hive.ql.io.parquet.serde.ParquetHiveSerDe', 'INPUTFORMAT' = 'org.apache.hadoop.hive.ql.io.parquet.MapredParquetInputFormat', 'OUTPUTFORMAT' = 'org.apache.hadoop.hive.ql.io.parquet.MapredParquetOutputFormat')
PARTITIONED BY (part)
