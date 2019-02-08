CREATE TABLE parquet_test (
  id int,
  str string,
  mp MAP<STRING,STRING>,
  lst ARRAY<STRING>,
  strct STRUCT<A:STRING,B:STRING>) 
PARTITIONED BY (part string)
ROW FORMAT SERDE 'parquet.hive.serde.ParquetHiveSerDe'
  STORED AS
  INPUTFORMAT 'parquet.hive.DeprecatedParquetInputFormat'
  OUTPUTFORMAT 'parquet.hive.DeprecatedParquetOutputFormat'
