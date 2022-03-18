CREATE TABLE public.post (
	id bigserial NOT NULL,
	"content" varchar(255) NOT NULL,
	description varchar(255) NOT NULL,
	title varchar(255) NOT NULL,
	CONSTRAINT post_pkey PRIMARY KEY (id),
	CONSTRAINT uk2jm25hjrq6iv4w8y1dhi0d9p4 UNIQUE (title)
);

select * from post p ;