--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.13
-- Dumped by pg_dump version 9.6.13

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: tiger; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA tiger;


ALTER SCHEMA tiger OWNER TO postgres;

--
-- Name: tiger_data; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA tiger_data;


ALTER SCHEMA tiger_data OWNER TO postgres;

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
-- Name: address_standardizer; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS address_standardizer WITH SCHEMA public;


--
-- Name: EXTENSION address_standardizer; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION address_standardizer IS 'Used to parse an address into constituent elements. Generally used to support geocoding address normalization step.';


--
-- Name: fuzzystrmatch; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS fuzzystrmatch WITH SCHEMA public;


--
-- Name: EXTENSION fuzzystrmatch; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION fuzzystrmatch IS 'determine similarities and distance between strings';


--
-- Name: ogr_fdw; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS ogr_fdw WITH SCHEMA public;


--
-- Name: EXTENSION ogr_fdw; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION ogr_fdw IS 'foreign-data wrapper for GIS data access';


--
-- Name: postgis; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS postgis WITH SCHEMA public;


--
-- Name: EXTENSION postgis; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION postgis IS 'PostGIS geometry, geography, and raster spatial types and functions';


--
-- Name: pgrouting; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS pgrouting WITH SCHEMA public;


--
-- Name: EXTENSION pgrouting; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION pgrouting IS 'pgRouting Extension';


--
-- Name: pointcloud; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS pointcloud WITH SCHEMA public;


--
-- Name: EXTENSION pointcloud; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION pointcloud IS 'data type for lidar point clouds';


--
-- Name: pointcloud_postgis; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS pointcloud_postgis WITH SCHEMA public;


--
-- Name: EXTENSION pointcloud_postgis; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION pointcloud_postgis IS 'integration for pointcloud LIDAR data and PostGIS geometry data';


--
-- Name: postgis_sfcgal; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS postgis_sfcgal WITH SCHEMA public;


--
-- Name: EXTENSION postgis_sfcgal; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION postgis_sfcgal IS 'PostGIS SFCGAL functions';


--
-- Name: postgis_tiger_geocoder; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS postgis_tiger_geocoder WITH SCHEMA tiger;


--
-- Name: EXTENSION postgis_tiger_geocoder; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION postgis_tiger_geocoder IS 'PostGIS tiger geocoder and reverse geocoder';


--
-- Name: postgis_topology; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS postgis_topology WITH SCHEMA topology;


--
-- Name: EXTENSION postgis_topology; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION postgis_topology IS 'PostGIS topology spatial types and functions';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: zw_line; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.zw_line (
    id integer NOT NULL,
    name character varying(64),
    voltage integer
);


ALTER TABLE public.zw_line OWNER TO postgres;

--
-- Name: zw_line_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.zw_line_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.zw_line_id_seq OWNER TO postgres;

--
-- Name: zw_line_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.zw_line_id_seq OWNED BY public.zw_line.id;


--
-- Name: zw_pointcloud; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.zw_pointcloud (
    id character varying(32) NOT NULL,
    path character varying(128)
);


ALTER TABLE public.zw_pointcloud OWNER TO postgres;

--
-- Name: zw_task; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.zw_task (
    id integer NOT NULL,
    name character varying(64) NOT NULL,
    user_id integer
);


ALTER TABLE public.zw_task OWNER TO postgres;

--
-- Name: zw_task_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.zw_task_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.zw_task_id_seq OWNER TO postgres;

--
-- Name: zw_task_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.zw_task_id_seq OWNED BY public.zw_task.id;


--
-- Name: zw_tower; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.zw_tower (
    id integer NOT NULL,
    serial numeric(4,1),
    line integer,
    name character varying(16),
    center public.geometry(Point,4326),
    altitude numeric(6,2),
    height numeric,
    width numeric,
    pointmd5 character varying
);


ALTER TABLE public.zw_tower OWNER TO postgres;

--
-- Name: zw_tower_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.zw_tower_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.zw_tower_id_seq OWNER TO postgres;

--
-- Name: zw_tower_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.zw_tower_id_seq OWNED BY public.zw_tower.id;


--
-- Name: zw_tower_style; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.zw_tower_style (
    id integer NOT NULL,
    name character varying(32),
    box numeric(4,2)[],
    pointcloud character varying(32),
    target json,
    flight json,
    voltage integer
);


ALTER TABLE public.zw_tower_style OWNER TO postgres;

--
-- Name: zw_tower_style_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.zw_tower_style_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.zw_tower_style_id_seq OWNER TO postgres;

--
-- Name: zw_tower_style_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.zw_tower_style_id_seq OWNED BY public.zw_tower_style.id;


--
-- Name: zw_user; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.zw_user (
    id integer NOT NULL,
    name character varying(32) NOT NULL,
    password character varying,
    token character varying(64),
    expired date,
    status integer,
    username character varying(16)
);


ALTER TABLE public.zw_user OWNER TO postgres;

--
-- Name: zw_user_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.zw_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.zw_user_id_seq OWNER TO postgres;

--
-- Name: zw_user_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.zw_user_id_seq OWNED BY public.zw_user.id;


--
-- Name: zw_line id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.zw_line ALTER COLUMN id SET DEFAULT nextval('public.zw_line_id_seq'::regclass);


--
-- Name: zw_task id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.zw_task ALTER COLUMN id SET DEFAULT nextval('public.zw_task_id_seq'::regclass);


--
-- Name: zw_tower id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.zw_tower ALTER COLUMN id SET DEFAULT nextval('public.zw_tower_id_seq'::regclass);


--
-- Name: zw_tower_style id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.zw_tower_style ALTER COLUMN id SET DEFAULT nextval('public.zw_tower_style_id_seq'::regclass);


--
-- Name: zw_user id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.zw_user ALTER COLUMN id SET DEFAULT nextval('public.zw_user_id_seq'::regclass);


--
-- Data for Name: pointcloud_formats; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pointcloud_formats (pcid, srid, schema) FROM stdin;
\.


--
-- Data for Name: spatial_ref_sys; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.spatial_ref_sys (srid, auth_name, auth_srid, srtext, proj4text) FROM stdin;
\.


--
-- Data for Name: zw_line; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.zw_line (id, name, voltage) FROM stdin;
1	1124西桃二线	110
2	110KV昆户线	110
3	110KV沙站线	110
4	操场后测试线	330
\.


--
-- Name: zw_line_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.zw_line_id_seq', 4, true);


--
-- Data for Name: zw_pointcloud; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.zw_pointcloud (id, path) FROM stdin;
\.


--
-- Data for Name: zw_task; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.zw_task (id, name, user_id) FROM stdin;
1	Task1	1
2	Task2	1
\.


--
-- Name: zw_task_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.zw_task_id_seq', 2, true);


--
-- Data for Name: zw_tower; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.zw_tower (id, serial, line, name, center, altitude, height, width, pointmd5) FROM stdin;
3	9.0	1	9	\N	\N	40.0	10.0	\N
1	7.0	1	7	\N	\N	30.0	8.0	\N
2	8.0	1	8	\N	\N	30.0	8.0	\N
4	10.0	1	10	\N	\N	35.0	6.0	\N
\.


--
-- Name: zw_tower_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.zw_tower_id_seq', 2, true);


--
-- Data for Name: zw_tower_style; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.zw_tower_style (id, name, box, pointcloud, target, flight, voltage) FROM stdin;
1	酒杯塔	{6.00,10.00,34.00}	\N	\N	\N	110
2	猫头塔	{5.00,11.00,35.00}	\N	\N	\N	110
\.


--
-- Name: zw_tower_style_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.zw_tower_style_id_seq', 2, true);


--
-- Data for Name: zw_user; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.zw_user (id, name, password, token, expired, status, username) FROM stdin;
2	bblu	bblu	123456	\N	\N	bblu
1	admin	admin	admin	2019-07-25	1	admin
\.


--
-- Name: zw_user_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.zw_user_id_seq', 2, true);


--
-- Data for Name: geocode_settings; Type: TABLE DATA; Schema: tiger; Owner: postgres
--

COPY tiger.geocode_settings (name, setting, unit, category, short_desc) FROM stdin;
\.


--
-- Data for Name: pagc_gaz; Type: TABLE DATA; Schema: tiger; Owner: postgres
--

COPY tiger.pagc_gaz (id, seq, word, stdword, token, is_custom) FROM stdin;
\.


--
-- Data for Name: pagc_lex; Type: TABLE DATA; Schema: tiger; Owner: postgres
--

COPY tiger.pagc_lex (id, seq, word, stdword, token, is_custom) FROM stdin;
\.


--
-- Data for Name: pagc_rules; Type: TABLE DATA; Schema: tiger; Owner: postgres
--

COPY tiger.pagc_rules (id, rule, is_custom) FROM stdin;
\.


--
-- Data for Name: topology; Type: TABLE DATA; Schema: topology; Owner: postgres
--

COPY topology.topology (id, name, srid, "precision", hasz) FROM stdin;
\.


--
-- Data for Name: layer; Type: TABLE DATA; Schema: topology; Owner: postgres
--

COPY topology.layer (topology_id, layer_id, schema_name, table_name, feature_column, feature_type, level, child_id) FROM stdin;
\.


--
-- Name: zw_line line_pk_id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.zw_line
    ADD CONSTRAINT line_pk_id PRIMARY KEY (id);


--
-- Name: zw_task pk_id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.zw_task
    ADD CONSTRAINT pk_id PRIMARY KEY (id);


--
-- Name: zw_tower_style style_pk_id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.zw_tower_style
    ADD CONSTRAINT style_pk_id PRIMARY KEY (id);


--
-- Name: zw_tower tower_pk_id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.zw_tower
    ADD CONSTRAINT tower_pk_id PRIMARY KEY (id);


--
-- Name: zw_user user_pk_id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.zw_user
    ADD CONSTRAINT user_pk_id PRIMARY KEY (id);


--
-- Name: zw_pointcloud zw_point_cloud_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.zw_pointcloud
    ADD CONSTRAINT zw_point_cloud_pkey PRIMARY KEY (id);


--
-- PostgreSQL database dump complete
--

