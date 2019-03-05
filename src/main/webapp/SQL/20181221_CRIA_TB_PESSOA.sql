-- Table: pessoa

-- DROP TABLE pessoa;

CREATE TABLE pessoa
(
  id serial NOT NULL,
  nome character varying(255),
  sobre_nome character varying(255),
  email character varying(150),
  sexo character varying(10),
  rg character varying(10),
  cpf character varying(15),
  data_registro timestamp without time zone,
  CONSTRAINT pk_pessoa PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE pessoa
  OWNER TO postgres;
