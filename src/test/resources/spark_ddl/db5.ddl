CREATE table IF NOT EXISTS `db_table6` (
  name string,
  age int)
USING HIVE
OPTIONS ('SERDE' = 'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe', 'INPUTFORMAT' = 'org.apache.hadoop.mapred.TextInputFormat', 'OUTPUTFORMAT' = 'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat')
LOCATION 'spark-warehouse/tables/my_existing_table'
COMMENT 'This table is created with existing data'
