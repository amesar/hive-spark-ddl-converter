CREATE table IF NOT EXISTS `db_table1` (
  name string,
  age int)
USING HIVE
OPTIONS ('SERDE' = 'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe', 'INPUTFORMAT' = 'org.apache.hadoop.mapred.TextInputFormat', 'OUTPUTFORMAT' = 'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat')
