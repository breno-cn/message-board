BEGIN;

SET CONSTRAINTS ALL DEFERRED;

INSERT INTO board (name, description) VALUES ('test', 'description test'), ('second table', 'second description');

COMMIT;