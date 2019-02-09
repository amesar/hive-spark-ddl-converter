CREATE table IF NOT EXISTS `parquet_test` (
  id int,
  str string,
  mp map<string,string>,
  lst array<string>,
  strct struct<A:string,B:string>,
  part string)
USING PARQUET
PARTITIONED BY (part)
