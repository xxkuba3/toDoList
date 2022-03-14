DROP TABLE IF EXISTS todos;
CREATE TABLE todos (
id int unsigned primary key auto_increment,
text varchar(100) not null,
done boolean not null
);