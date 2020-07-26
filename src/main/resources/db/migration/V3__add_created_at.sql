alter table donations
    add created_at timestamp not null;

UPDATE freecycle.donations SET created_at = '2020-06-29 01:07:53' WHERE id = 2;
UPDATE freecycle.donations SET created_at = '2020-07-04 08:12:56' WHERE id = 4;
UPDATE freecycle.donations SET created_at = '2020-07-12 17:28:56' WHERE id = 6;
UPDATE freecycle.donations SET created_at = '2020-06-26 08:32:29' WHERE id = 7;
UPDATE freecycle.donations SET created_at = '2020-06-06 13:01:23' WHERE id = 8;
UPDATE freecycle.donations SET created_at = '2020-07-23 17:42:30' WHERE id = 9;
UPDATE freecycle.donations SET created_at = '2020-07-12 21:36:32' WHERE id = 10;
UPDATE freecycle.donations SET created_at = '2020-06-27 02:19:57' WHERE id = 11;
UPDATE freecycle.donations SET created_at = '2020-07-14 11:54:23' WHERE id = 12;
