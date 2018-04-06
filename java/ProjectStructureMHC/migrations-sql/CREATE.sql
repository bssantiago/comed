CREATE TABLE public.comed_clients
(
    id serial NOT NULL,
    name text NOT NULL,
    highmark_client_id bigint NOT NULL,
    highmark_site_code bigint NOT NULL,
    contact_name text,
    contact_phone text,
    contact_fax text,
    email_address text,
    addr1 text,
    addr2 text,
    addr3 text,
    city text,
    state text,
    postal_code bigint,
    phys_last_name text,
    phys_first_name text,
    phys_middle_name text,
    phys_academic_degree text,
    vendor text,
    created_by text,
    creation_date timestamp without time zone,
    last_update_by bigint,
    last_update_date timestamp without time zone,
    PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
);

ALTER TABLE public.comed_clients
    OWNER to postgres;