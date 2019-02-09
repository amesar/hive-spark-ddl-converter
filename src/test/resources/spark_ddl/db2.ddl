CREATE table IF NOT EXISTS `db_table2` (
  name string,
  age int,
  hair_color string)
USING HIVE
OPTIONS ('SERDE' = 'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe', 'INPUTFORMAT' = 'org.apache.hadoop.mapred.TextInputFormat', 'OUTPUTFORMAT' = 'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat')
PARTITIONED BY (hair_color)
COMMENT 'This table is partitioned'
