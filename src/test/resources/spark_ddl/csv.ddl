CREATE table IF NOT EXISTS `my_table` (
  a string,
  b string)
USING CSV
OPTIONS ('SERDE' = 'org.apache.hadoop.hive.serde2.OpenCSVSerde', 'INPUTFORMAT' = 'org.apache.hadoop.mapred.TextInputFormat', 'OUTPUTFORMAT' = 'org.apache.hadoop.hive.ql.io.HiveIgnoreKeyTextOutputFormat')
