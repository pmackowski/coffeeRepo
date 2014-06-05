create table if not exists TEST_USER (
  ID int identity primary key,
  NAME varchar,
  LAST_NAME varchar,
  AGE  int,
  SALARY  int,
  BONUS  int,
  LAST_LOGIN timestamp,
  DATE_OF_BIRTH date
)