-- public.users definition

-- Drop table

-- DROP TABLE public.users;

CREATE TABLE public.users (
	id uuid NULL,
	username varchar NULL,
	chatid varchar NULL
);

-- НУЖНО ДОБАВИТЬ ПЕРВИЧНЫЙ КЛЮЧ В ТАБЛИЦУ users
ALTER TABLE public.users
ADD PRIMARY KEY (id);

CREATE TABLE public.notifications (
    id UUID PRIMARY KEY,
    user_id UUID REFERENCES public.users(id),
    title VARCHAR(255),
    notify_date_time TIMESTAMP
);

