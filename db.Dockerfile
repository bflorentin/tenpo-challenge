FROM postgres
COPY sql/init_database.sql /docker-entrypoint-initdb.d/