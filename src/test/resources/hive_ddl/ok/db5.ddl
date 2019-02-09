CREATE EXTERNAL TABLE IF NOT EXISTS db_table6 (name STRING, age INT)
    COMMENT 'This table is created with existing data'
    LOCATION 'spark-warehouse/tables/my_existing_table'
