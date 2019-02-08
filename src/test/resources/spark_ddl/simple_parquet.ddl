CREATE table IF NOT EXISTS `simple` (
  id int,
  name string,
  day int)
USING PARQUET
PARTITIONED BY (day)
LOCATION '/tmp/simple'
