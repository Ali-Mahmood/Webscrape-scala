CREATE KEYSPACE IF NOT EXISTS web_scrape
WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 3};

CREATE TABLE IF NOT EXISTS headlines (
  date_time timestamp PRIMARY KEY,
  headlines list<text>,
  hrefs list<text>,
  website text
);
