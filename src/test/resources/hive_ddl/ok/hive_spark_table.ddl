CREATE TABLE my_table (name STRING, age INT, hair_color STRING)
  USING HIVE
  OPTIONS(
    INPUTFORMAT 'org.apache.hadoop.mapred.SequenceFileInputFormat',
    OUTPUTFORMAT 'org.apache.hadoop.hive.ql.io.HiveSequenceFileOutputFormat',
    SERDE 'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe')
  PARTITIONED BY (hair_color)
  TBLPROPERTIES ('status'='staging', 'owner'='andrew')
