CREATE table IF NOT EXISTS `Addresses` (
  name string,
  street string,
  city string,
  state string,
  zip int)
USING ORC
