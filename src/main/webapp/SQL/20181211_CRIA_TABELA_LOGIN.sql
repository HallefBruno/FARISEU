-- Table: login

-- DROP TABLE login;

CREATE TABLE login
(
  id serial NOT NULL,
  login character varying(50),
  password character varying(50),
  id_licenca integer,
  CONSTRAINT login_pkey PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE login
  OWNER TO postgres;

