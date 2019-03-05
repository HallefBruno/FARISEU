
/**
 * Author:  hallef.sud
 * Created: 04/12/2018
 */

-- Table: permirte_tela_login

-- DROP TABLE permirte_tela_login;

CREATE TABLE permirte_tela_login
(
  id serial NOT NULL,
  ip character varying(20),
  host_name character varying(20),
  id_super_usuario integer,
  CONSTRAINT "PK_PERMIRTE_TELA_LOGIN" PRIMARY KEY (id)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE permirte_tela_login
  OWNER TO postgres;
