CREATE table IF NOT EXISTS `my_table` (
  name string,
  age int,
  hair_color string)
USING HIVE
OPTIONS ('INPUTFORMAT' = 'org.apache.hadoop.mapred.SequenceFileInputFormat', 'OUTPUTFORMAT' = 'org.apache.hadoop.hive.ql.io.HiveSequenceFileOutputFormat', 'SERDE' = 'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe')
PARTITIONED BY (hair_color)
