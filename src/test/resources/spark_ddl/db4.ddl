CREATE table IF NOT EXISTS `db_table4` (
  name string,
  age int)
USING HIVE
OPTIONS ('field.delim' = ',', 'serialization.format' = ',')
COMMENT 'This table uses the CSV format'
