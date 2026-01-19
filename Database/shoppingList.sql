--
-- PostgreSQL database dump
--

\restrict qMVwDOhkoxnM8cY8ozIO35bCdRKtvnSJNT6cm2Zge13BXDi8FH8wGhTedFFCSnW

-- Dumped from database version 18.0
-- Dumped by pg_dump version 18.0

-- Started on 2026-01-19 22:37:50

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 219 (class 1259 OID 42618)
-- Name: shoppinglist; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.shoppinglist (
    namabarang character varying(30) NOT NULL,
    catatanbarang character varying(30) NOT NULL
);


ALTER TABLE public.shoppinglist OWNER TO postgres;

--
-- TOC entry 5004 (class 0 OID 42618)
-- Dependencies: 219
-- Data for Name: shoppinglist; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.shoppinglist (namabarang, catatanbarang) FROM stdin;
Pensil	Penting !
Pulpen	Penting !
\.


--
-- TOC entry 4856 (class 2606 OID 42624)
-- Name: shoppinglist shoppinglist_namabarang_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.shoppinglist
    ADD CONSTRAINT shoppinglist_namabarang_key UNIQUE (namabarang);


-- Completed on 2026-01-19 22:37:50

--
-- PostgreSQL database dump complete
--

\unrestrict qMVwDOhkoxnM8cY8ozIO35bCdRKtvnSJNT6cm2Zge13BXDi8FH8wGhTedFFCSnW

