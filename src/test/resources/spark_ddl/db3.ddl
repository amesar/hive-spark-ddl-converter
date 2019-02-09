CREATE table IF NOT EXISTS `db_table3` (
  name string,
  age int)
USING ORC
OPTIONS ('SERDE' = 'org.apache.hadoop.hive.ql.io.orc.OrcSerde', 'INPUTFORMAT' = 'org.apache.hadoop.hive.ql.io.orc.OrcInputFormat', 'OUTPUTFORMAT' = 'org.apache.hadoop.hive.ql.io.orc.OrcOutputFormat')
COMMENT 'This table specifies a custom SerDe'
