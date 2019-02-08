create external table simple (
  id  int,
  name string
)   
STORED AS PARQUET
PARTITIONED BY (day int) 
LOCATION '/tmp/simple'
