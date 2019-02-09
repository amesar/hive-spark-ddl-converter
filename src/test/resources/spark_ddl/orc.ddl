CREATE table IF NOT EXISTS `Addresses` (
  name string,
  street string,
  city string,
  state string,
  zip int)
USING ORC
OPTIONS ('SERDE' = 'org.apache.hadoop.hive.ql.io.orc.OrcSerde', 'INPUTFORMAT' = 'org.apache.hadoop.hive.ql.io.orc.OrcInputFormat', 'OUTPUTFORMAT' = 'org.apache.hadoop.hive.ql.io.orc.OrcOutputFormat')
