CREATE TABLE comed_clients
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

ALTER TABLE comed_clients
    OWNER to postgres;
    
    
CREATE TABLE comed_client_assessment
(
    client_id bigint NOT NULL,
    program_id text NOT NULL,
    calendar_year bigint NOT NULL,
    program_start_date timestamp without time zone NOT NULL,
    program_end_date timestamp without time zone NOT NULL,
    program_display_name text NOT NULL,
    extended_screenings bigint NOT NULL,
    created_by text,
    creation_date timestamp without time zone NOT NULL,
    last_updated_by text,
    last_update_date time without time zone,
    PRIMARY KEY (client_id, program_id),
    CONSTRAINT fk_client_id_client_assessment FOREIGN KEY (client_id)
        REFERENCES comed_clients (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
);

ALTER TABLE comed_client_assessment
    OWNER to postgres;    
    
ALTER TABLE comed_client_assessment
ADD COLUMN file_name text;
    