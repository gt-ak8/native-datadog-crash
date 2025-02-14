create table if not exists todos (
  id UUID primary key not null,
  content text not null
);