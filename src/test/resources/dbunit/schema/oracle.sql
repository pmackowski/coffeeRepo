drop table TEST_USER;

create table TEST_USER (
  ID NUMBER(19,0) primary key,
  NAME varchar2(100),
  LAST_NAME varchar2(100),
  AGE  NUMBER(19,0),
  SALARY  NUMBER(19,0),
  BONUS  NUMBER(19,0),
  LAST_LOGIN timestamp,
  DATE_OF_BIRTH date,
  HAS_CAR NUMBER(1,0)
);