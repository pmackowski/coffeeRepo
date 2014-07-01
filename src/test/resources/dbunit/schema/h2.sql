create table if not exists TEST_USER (
  ID int identity primary key,
  NAME varchar,
  LAST_NAME varchar,
  AGE INT,
  SALARY  INT,
  BONUS  INT,
  LAST_LOGIN TIMESTAMP,
  DATE_OF_BIRTH DATE,
  HAS_CAR BOOLEAN
)