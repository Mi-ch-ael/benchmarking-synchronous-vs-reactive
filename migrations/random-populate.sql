--liquibase formatted sql

--changeset nvoxland:1
CREATE TABLE IF NOT EXISTS database_entity (
    text_id BIGSERIAL PRIMARY KEY,
    text_content CHAR(1000) NOT NULL
);

INSERT INTO database_entity (text_content)
SELECT '' from generate_series(1, 100);

UPDATE database_entity
SET text_content = array_to_string(
    array(
        SELECT substr('ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789',((random()*(36-1)+1)::integer),1)
        FROM generate_series(1,1000) where text_content is distinct from 'reserved'
        ),''
    );