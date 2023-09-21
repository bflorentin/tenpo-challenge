FROM postgres
COPY postgres/init_database.sql /docker-entrypoint-initdb.d/