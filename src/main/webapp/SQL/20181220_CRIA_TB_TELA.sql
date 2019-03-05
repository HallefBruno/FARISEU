-- Table: tela

-- DROP TABLE tela;

CREATE TABLE tela
(
  id integer NOT NULL,
  descricao character varying(100) NOT NULL, -- Descricao da tela
  url character varying(255) NOT NULL, -- Caminho da tela
  id_menu integer NOT NULL, -- id da tabela menu
  CONSTRAINT pk_tela PRIMARY KEY (id),
  CONSTRAINT fk_menu FOREIGN KEY (id_menu)
      REFERENCES menu (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uq_descricao UNIQUE (descricao),
  CONSTRAINT uq_url UNIQUE (url)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE tela
  OWNER TO postgres;
COMMENT ON COLUMN tela.descricao IS 'Descricao da tela';
COMMENT ON COLUMN tela.url IS 'Caminho da tela';
COMMENT ON COLUMN tela.id_menu IS 'id da tabela menu';

