--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.6
-- Dumped by pg_dump version 9.6.0

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: topology; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA topology;


ALTER SCHEMA topology OWNER TO postgres;

--
-- Name: SCHEMA topology; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA topology IS 'PostGIS Topology schema';


--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- Name: postgis; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS postgis WITH SCHEMA public;


--
-- Name: EXTENSION postgis; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION postgis IS 'PostGIS geometry, geography, and raster spatial types and functions';


--
-- Name: postgis_topology; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS postgis_topology WITH SCHEMA topology;


--
-- Name: EXTENSION postgis_topology; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION postgis_topology IS 'PostGIS topology spatial types and functions';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: zw_line; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE zw_line (
    id integer NOT NULL,
    name character varying(64),
    voltage integer
);


ALTER TABLE zw_line OWNER TO postgres;

--
-- Name: zw_line_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE zw_line_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE zw_line_id_seq OWNER TO postgres;

--
-- Name: zw_line_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE zw_line_id_seq OWNED BY zw_line.id;


--
-- Name: zw_task; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE zw_task (
    id integer NOT NULL,
    name character varying(64) NOT NULL,
    user_id integer
);


ALTER TABLE zw_task OWNER TO postgres;

--
-- Name: zw_task_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE zw_task_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE zw_task_id_seq OWNER TO postgres;

--
-- Name: zw_task_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE zw_task_id_seq OWNED BY zw_task.id;


--
-- Name: zw_tower; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE zw_tower (
    id integer NOT NULL,
    serial numeric(4,1),
    line integer,
    name character varying(16),
    center geometry(Point,4326),
    altitude numeric(5,1),
    height numeric(4,1),
    width numeric(4,1)
);


ALTER TABLE zw_tower OWNER TO postgres;

--
-- Name: zw_tower_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE zw_tower_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE zw_tower_id_seq OWNER TO postgres;

--
-- Name: zw_tower_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE zw_tower_id_seq OWNED BY zw_tower.id;


--
-- Name: zw_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE zw_user (
    id integer NOT NULL,
    name character varying(32) NOT NULL,
    password character varying,
    token character varying(64),
    expired date,
    status integer,
    username character varying(16)
);


ALTER TABLE zw_user OWNER TO postgres;

--
-- Name: zw_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE zw_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE zw_user_id_seq OWNER TO postgres;

--
-- Name: zw_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE zw_user_id_seq OWNED BY zw_user.id;


--
-- Name: zw_line id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY zw_line ALTER COLUMN id SET DEFAULT nextval('zw_line_id_seq'::regclass);


--
-- Name: zw_task id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY zw_task ALTER COLUMN id SET DEFAULT nextval('zw_task_id_seq'::regclass);


--
-- Name: zw_tower id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY zw_tower ALTER COLUMN id SET DEFAULT nextval('zw_tower_id_seq'::regclass);


--
-- Name: zw_user id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY zw_user ALTER COLUMN id SET DEFAULT nextval('zw_user_id_seq'::regclass);


--
-- Data for Name: spatial_ref_sys; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY spatial_ref_sys (srid, auth_name, auth_srid, srtext, proj4text) FROM stdin;
\.


--
-- Data for Name: zw_line; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY zw_line (id, name, voltage) FROM stdin;
1	1124西桃二线	110
2	110KV昆户线	110
3	110KV沙站线	110
4	操场后测试线	330
\.


--
-- Name: zw_line_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('zw_line_id_seq', 4, true);


--
-- Data for Name: zw_task; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY zw_task (id, name, user_id) FROM stdin;
1	Task1	1
2	Task2	1
\.


--
-- Name: zw_task_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('zw_task_id_seq', 2, true);


--
-- Data for Name: zw_tower; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY zw_tower (id, serial, line, name, center, altitude, height, width) FROM stdin;
3	9.0	1	9	\N	\N	40.0	10.0
1	7.0	1	7	\N	\N	30.0	8.0
2	8.0	1	8	\N	\N	30.0	8.0
4	10.0	1	10	\N	\N	35.0	6.0
\.


--
-- Name: zw_tower_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('zw_tower_id_seq', 2, true);


--
-- Data for Name: zw_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY zw_user (id, name, password, token, expired, status, username) FROM stdin;
2	bblu	bblu	123456	\N	\N	bblu
1	admin	admin	admin	2019-06-19	1	admin
\.


--
-- Name: zw_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('zw_user_id_seq', 2, true);


SET search_path = topology, pg_catalog;

--
-- Data for Name: topology; Type: TABLE DATA; Schema: topology; Owner: postgres
--

COPY topology (id, name, srid, "precision", hasz) FROM stdin;
\.


--
-- Data for Name: layer; Type: TABLE DATA; Schema: topology; Owner: postgres
--

COPY layer (topology_id, layer_id, schema_name, table_name, feature_column, feature_type, level, child_id) FROM stdin;
\.


SET search_path = public, pg_catalog;

--
-- Name: zw_line line_pk_id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY zw_line
    ADD CONSTRAINT line_pk_id PRIMARY KEY (id);


--
-- Name: zw_task pk_id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY zw_task
    ADD CONSTRAINT pk_id PRIMARY KEY (id);


--
-- Name: zw_tower tower_pk_id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY zw_tower
    ADD CONSTRAINT tower_pk_id PRIMARY KEY (id);


--
-- Name: zw_user user_pk_id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY zw_user
    ADD CONSTRAINT user_pk_id PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

