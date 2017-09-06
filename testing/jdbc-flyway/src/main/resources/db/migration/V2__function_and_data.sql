-- counts rows in a some_table
CREATE FUNCTION my_count() RETURNS bigint AS $$
    select count(1) from some_table;
    $$ LANGUAGE sql;

insert into some_table values (2);