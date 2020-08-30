BEGIN;

SET CONSTRAINTS ALL DEFERRED;

INSERT INTO board (name) VALUES ('test'), ('second table');

COMMIT;