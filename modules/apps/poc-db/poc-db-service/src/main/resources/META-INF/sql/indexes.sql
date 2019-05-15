create index IX_CFFD06FF on FOO_Foo (field2);
create index IX_B2FCA947 on FOO_Foo (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_905CD589 on FOO_Foo (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_C056B7FD on POC_Foo (field2);
create index IX_BDE3B4C5 on POC_Foo (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_3FECA887 on POC_Foo (uuid_[$COLUMN_LENGTH:75$], groupId);