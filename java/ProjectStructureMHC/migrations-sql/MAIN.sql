CREATE TABLE http_access_logs
(
  id BIGSERIAL PRIMARY KEY,
  requested_by character varying,
  source_ip character varying,
  document_id character varying,
  action_time timestamp with time zone NOT NULL DEFAULT now(),
  nonce character varying,
  path character varying,
  method character varying
)
WITH (
  OIDS=FALSE
);

CREATE UNIQUE INDEX http_access_logs_nonce_idx
  ON http_access_logs
  USING btree
  (nonce COLLATE pg_catalog."default");
  

CREATE TABLE app_keys
(
  id BIGSERIAL PRIMARY KEY,
  salt character varying,
  doc_key character varying,
  log_key character varying
)
WITH (
  OIDS=FALSE
);