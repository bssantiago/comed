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
    
CREATE TABLE public.comed_participants
(
    id serial NOT NULL,
    client_id bigint NOT NULL,
    first_name text NOT NULL,
    last_name text NOT NULL,
    middle_initial text,
    sufix text,
    addr1 text,
    addr2 text,
    addr3 text,
    city text NOT NULL,
    state text NOT NULL,
    postal_code bigint,
    gender text NOT NULL,
    date_of_birth timestamp without time zone NOT NULL,
    email_address text,
    phone_number text,
    phone_extension text,
    phone_location text,
    member_id text,
    no_pcp boolean,
    primary_care_physician bigint,
    past_patient boolean,
    last_physical_exam bigint,
    history_heart_disease boolean,
    history_diabetes boolean,
    history_osteoporosis boolean,
    history_cancer boolean,
    cancer_breast boolean,
    cancer_colon boolean,
    cancer_lung boolean,
    cancer_skin boolean,
    cancer_other boolean,
    diagnosis_heart_disease boolean,
    diagnosis_osteoporosis boolean,
    treatment_cholesterol boolean,
    treatment_diabetes boolean,
    treatment_hypertension boolean,
    treatment_osteoporosis boolean,
    active_smoker boolean,
    past_smoker boolean,
    exercise boolean,
    fruits boolean,
    grains boolean,
    smoking bigint,
    exercise2 bigint,
    fruits2 bigint,
    mail_pcp boolean,
    mail_individual boolean,
    provider_assist boolean,
    last_assessment text,
    status text,
    pcp_last_name text,
    pcp_first_name text,
    pcp_middle_name text,
    pcp_academic_degree text,
    pcp_addr1 text,
    pcp_addr2 text,
    pcp_city text,
    pcp_state text,
    pcp_postal_code bigint,
    created_by text,
    creation_date timestamp without time zone NOT NULL,
    last_updated_by text,
    last_update_date timestamp without time zone,
    kordinator_id text,
    external_participant boolean,
    PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
);

ALTER TABLE public.comed_participants
    OWNER to postgres;    
    
    
ALTER TABLE public.comed_participants
    ADD UNIQUE (client_id, member_id);    


CREATE TABLE comed_participants_biometrics
(
	biometric_id serial NOT NULL,
	participant_id serial NOT NULL,
    sistolic text NOT NULL,
	diastolic text NOT NULL,
	height numeric NOT NULL,
	weight numeric NOT NULL,
	waist numeric NOT NULL,
	body_fat numeric NOT NULL,
	cholesterol numeric NOT NULL,
	hdl numeric NOT NULL,
	triglycerides numeric NOT NULL,
	ldl numeric NOT NULL,
	glucose numeric NOT NULL,
	hba1c numeric NOT NULL,
	tobacco_use numeric NOT NULL,
	create_date timestamp without time zone,
    PRIMARY KEY (biometric_id),
    CONSTRAINT fk_client_id_participants FOREIGN KEY (participant_id)
        REFERENCES comed_participants (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)
WITH (
    OIDS = FALSE
);

TABLESPACE pg_default;

ALTER TABLE public.comed_participants_biometrics
    OWNER to postgres;
    

