CREATE DATABASE tenpo;
\c tenpo;

CREATE TABLE REQUEST_LOG (
        ID bigserial NOT NULL PRIMARY KEY,
        PATH varchar(255) ,
        HTTP_STATUS varchar(255),
        DATE_TIME TIMESTAMP NOT NULL
);