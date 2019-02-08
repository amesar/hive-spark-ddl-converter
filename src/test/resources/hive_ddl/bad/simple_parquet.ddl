create external table simple (
  id  int,
  name string
)   
STORED AS Unknown_Format
PARTITIONED BY (day int) 
LOCATION '/tmp/simple'
