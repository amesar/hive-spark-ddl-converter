CREATE TABLE db_table2 (name STRING, age INT)
    COMMENT 'This table is partitioned'
    PARTITIONED BY (hair_color STRING COMMENT 'This is a column comment')
    TBLPROPERTIES ('status'='staging', 'owner'='andrew')
