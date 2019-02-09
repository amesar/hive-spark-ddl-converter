create external table simple (
  name string,
  ts timestamp,
  id  int,
  id_tiny tinyint,
  id_small smallint,
  dec1 decimal(8,0),
  dec2 decimal(8,2),
  ch char(2),
  vch varchar(8)
)   
STORED AS PARQUET
PARTITIONED BY (day int) 
LOCATION '/tmp/simple'
