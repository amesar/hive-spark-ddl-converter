CREATE table IF NOT EXISTS `simple` (
  name string,
  ts timestamp,
  id int,
  id_tiny tinyint,
  id_small smallint,
  dec1 decimal(8,0),
  dec2 decimal(8,2),
  ch string,
  vch string,
  day int)
USING PARQUET
PARTITIONED BY (day)
LOCATION '/tmp/simple'
