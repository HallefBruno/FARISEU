-- Table: menu

-- DROP TABLE menu;

CREATE TABLE menu
(
  id integer NOT NULL,
  descricao character varying(50) NOT NULL,
  CONSTRAINT menu_pkey PRIMARY KEY (id),
  CONSTRAINT menu_descricao_key UNIQUE (descricao)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE menu
  OWNER TO postgres;
