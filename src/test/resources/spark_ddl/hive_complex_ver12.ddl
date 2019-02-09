CREATE table IF NOT EXISTS `parquet_test` (
  id int,
  str string,
  mp map<string,string>,
  lst array<string>,
  strct struct<A:string,B:string>,
  part string)
USING PARQUET
OPTIONS ('SERDE' = 'parquet.hive.serde.ParquetHiveSerDe', 'INPUTFORMAT' = 'parquet.hive.DeprecatedParquetInputFormat', 'OUTPUTFORMAT' = 'parquet.hive.DeprecatedParquetOutputFormat')
PARTITIONED BY (part)
