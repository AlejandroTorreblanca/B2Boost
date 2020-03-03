create table partner(id LONGTEXT, companyName varchar(30), ref varchar(30), locale varchar(30), expires DATE);
insert into partner(id,companyName,ref,locale,expires) values('00001','Partner1','xxxxx1','en_GB',TO_DATE('2017-10-03T12:03:46+00:00','YYYY-MM-DDThh:mm:ssTZD'));
insert into partner(id,companyName,ref,locale,expires) values('00002','Partner2','xxxxx2','en_GB',TO_DATE('2019-10-03T12:03:46+00:00','YYYY-MM-DDThh:mm:ssTZD'));
insert into partner(id,companyName,ref,locale,expires) values('00003','Partner3','xxxxx3','es_ES',TO_DATE('2018-10-03T12:03:46+00:00','YYYY-MM-DDThh:mm:ssTZD'));

