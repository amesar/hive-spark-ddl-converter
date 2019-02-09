CREATE table IF NOT EXISTS `db_table3` (
  name string,
  age int)
USING ORC
COMMENT 'This table specifies a custom SerDe'
