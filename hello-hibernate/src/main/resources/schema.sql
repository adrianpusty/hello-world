---- org.h2.jdbc.JdbcSQLDataException: Value too long for column "PROPERTY_WITH_NO_SIZE_SPECIFIED CHARACTER VARYING(255)"
--insert into Sample_Class (id, PROPERTY_WITH_NO_SIZE_SPECIFIED) values (101, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.');
insert into A (id) values (101);
insert into A (id) values (102);
insert into B (id) values (1);
insert into B (id) values (2);
insert into A_B (A_ID, b_id) values (102, 1);
insert into Sample_Class (id, XO) values (100, 'hello world');