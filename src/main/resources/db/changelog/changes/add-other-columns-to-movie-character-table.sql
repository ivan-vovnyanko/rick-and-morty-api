ALTER TABLE public.movie_character
    ADD COLUMN species character varying(256),
    ADD COLUMN type character varying(256),
    ADD COLUMN api_image_url character varying(256),
    ADD COLUMN local_image_url character varying(256),
    ADD COLUMN created character varying(256);
