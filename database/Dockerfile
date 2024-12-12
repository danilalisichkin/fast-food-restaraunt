FROM postgres:17

WORKDIR /docker-entrypoint-initdb.d

COPY scripts/01-create-database.sql /docker-entrypoint-initdb.d/
COPY scripts/02-create-tables.sql /docker-entrypoint-initdb.d/
COPY scripts/03-insert-data.sql /docker-entrypoint-initdb.d/

ENV POSTGRES_USER=postgres
ENV POSTGRES_PASSWORD=root