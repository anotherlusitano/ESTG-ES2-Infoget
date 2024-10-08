PGDMP  8                	    |           infoget    17.0    17.0 '    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                           false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                           false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                           false            �           1262    16387    infoget    DATABASE     ~   CREATE DATABASE infoget WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Portuguese_Brazil.1252';
    DROP DATABASE infoget;
                     postgres    false            �            1259    16439    Alunos    TABLE     �   CREATE TABLE public."Alunos" (
    idaluno integer NOT NULL,
    nome_aluno text NOT NULL,
    email text NOT NULL,
    password text NOT NULL,
    idcurso integer NOT NULL
);
    DROP TABLE public."Alunos";
       public         heap r       postgres    false            �            1259    16438    Alunos_idaluno_seq    SEQUENCE     �   ALTER TABLE public."Alunos" ALTER COLUMN idaluno ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."Alunos_idaluno_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    227            �            1259    16405    Cursos    TABLE     ]   CREATE TABLE public."Cursos" (
    idcurso integer NOT NULL,
    nome_curso text NOT NULL
);
    DROP TABLE public."Cursos";
       public         heap r       postgres    false            �            1259    16404    Cursos_idcurso_seq    SEQUENCE     �   ALTER TABLE public."Cursos" ALTER COLUMN idcurso ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."Cursos_idcurso_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    222            �            1259    16413    Disciplinas    TABLE     �   CREATE TABLE public."Disciplinas" (
    iddisciplina integer NOT NULL,
    nome_disciplina text NOT NULL,
    carga_horaria integer,
    idprofessor integer
);
 !   DROP TABLE public."Disciplinas";
       public         heap r       postgres    false            �            1259    16412    Disciplinas_iddisciplina_seq    SEQUENCE     �   ALTER TABLE public."Disciplinas" ALTER COLUMN iddisciplina ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."Disciplinas_iddisciplina_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    224            �            1259    16396    Professores    TABLE     �   CREATE TABLE public."Professores" (
    idprofessor integer NOT NULL,
    nome_professor text NOT NULL,
    email text NOT NULL,
    password text NOT NULL
);
 !   DROP TABLE public."Professores";
       public         heap r       postgres    false            �            1259    16395    Professores_idprofessor_seq    SEQUENCE     �   ALTER TABLE public."Professores" ALTER COLUMN idprofessor ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."Professores_idprofessor_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    219            �            1259    16388    Secretarios    TABLE     �   CREATE TABLE public."Secretarios" (
    idsecretario integer NOT NULL,
    nome_secretario text NOT NULL,
    email text NOT NULL,
    password text NOT NULL
);
 !   DROP TABLE public."Secretarios";
       public         heap r       postgres    false            �            1259    16403    Secretarios_idsecretario_seq    SEQUENCE     �   ALTER TABLE public."Secretarios" ALTER COLUMN idsecretario ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public."Secretarios_idsecretario_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public               postgres    false    217            �            1259    16451 
   aluno_nota    TABLE     v   CREATE TABLE public.aluno_nota (
    idaluno integer NOT NULL,
    iddisciplina integer NOT NULL,
    nota integer
);
    DROP TABLE public.aluno_nota;
       public         heap r       postgres    false            �            1259    16425    curso_disciplina    TABLE     j   CREATE TABLE public.curso_disciplina (
    idcurso integer NOT NULL,
    iddisciplina integer NOT NULL
);
 $   DROP TABLE public.curso_disciplina;
       public         heap r       postgres    false            �          0    16439    Alunos 
   TABLE DATA           Q   COPY public."Alunos" (idaluno, nome_aluno, email, password, idcurso) FROM stdin;
    public               postgres    false    227   .       �          0    16405    Cursos 
   TABLE DATA           7   COPY public."Cursos" (idcurso, nome_curso) FROM stdin;
    public               postgres    false    222   5.       �          0    16413    Disciplinas 
   TABLE DATA           b   COPY public."Disciplinas" (iddisciplina, nome_disciplina, carga_horaria, idprofessor) FROM stdin;
    public               postgres    false    224   R.       �          0    16396    Professores 
   TABLE DATA           U   COPY public."Professores" (idprofessor, nome_professor, email, password) FROM stdin;
    public               postgres    false    219   o.       �          0    16388    Secretarios 
   TABLE DATA           W   COPY public."Secretarios" (idsecretario, nome_secretario, email, password) FROM stdin;
    public               postgres    false    217   �.       �          0    16451 
   aluno_nota 
   TABLE DATA           A   COPY public.aluno_nota (idaluno, iddisciplina, nota) FROM stdin;
    public               postgres    false    228   �.       �          0    16425    curso_disciplina 
   TABLE DATA           A   COPY public.curso_disciplina (idcurso, iddisciplina) FROM stdin;
    public               postgres    false    225   �.       �           0    0    Alunos_idaluno_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public."Alunos_idaluno_seq"', 1, false);
          public               postgres    false    226            �           0    0    Cursos_idcurso_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public."Cursos_idcurso_seq"', 1, false);
          public               postgres    false    221            �           0    0    Disciplinas_iddisciplina_seq    SEQUENCE SET     M   SELECT pg_catalog.setval('public."Disciplinas_iddisciplina_seq"', 1, false);
          public               postgres    false    223            �           0    0    Professores_idprofessor_seq    SEQUENCE SET     L   SELECT pg_catalog.setval('public."Professores_idprofessor_seq"', 1, false);
          public               postgres    false    218            �           0    0    Secretarios_idsecretario_seq    SEQUENCE SET     M   SELECT pg_catalog.setval('public."Secretarios_idsecretario_seq"', 1, false);
          public               postgres    false    220            F           2606    16445    Alunos Alunos_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY public."Alunos"
    ADD CONSTRAINT "Alunos_pkey" PRIMARY KEY (idaluno);
 @   ALTER TABLE ONLY public."Alunos" DROP CONSTRAINT "Alunos_pkey";
       public                 postgres    false    227            B           2606    16411    Cursos Cursos_pkey 
   CONSTRAINT     Y   ALTER TABLE ONLY public."Cursos"
    ADD CONSTRAINT "Cursos_pkey" PRIMARY KEY (idcurso);
 @   ALTER TABLE ONLY public."Cursos" DROP CONSTRAINT "Cursos_pkey";
       public                 postgres    false    222            D           2606    16419    Disciplinas Disciplinas_pkey 
   CONSTRAINT     h   ALTER TABLE ONLY public."Disciplinas"
    ADD CONSTRAINT "Disciplinas_pkey" PRIMARY KEY (iddisciplina);
 J   ALTER TABLE ONLY public."Disciplinas" DROP CONSTRAINT "Disciplinas_pkey";
       public                 postgres    false    224            @           2606    16402    Professores Professores_pkey 
   CONSTRAINT     g   ALTER TABLE ONLY public."Professores"
    ADD CONSTRAINT "Professores_pkey" PRIMARY KEY (idprofessor);
 J   ALTER TABLE ONLY public."Professores" DROP CONSTRAINT "Professores_pkey";
       public                 postgres    false    219            >           2606    16394    Secretarios Secretarios_pkey 
   CONSTRAINT     h   ALTER TABLE ONLY public."Secretarios"
    ADD CONSTRAINT "Secretarios_pkey" PRIMARY KEY (idsecretario);
 J   ALTER TABLE ONLY public."Secretarios" DROP CONSTRAINT "Secretarios_pkey";
       public                 postgres    false    217            K           2606    16454    aluno_nota idaluno    FK CONSTRAINT     �   ALTER TABLE ONLY public.aluno_nota
    ADD CONSTRAINT idaluno FOREIGN KEY (idaluno) REFERENCES public."Alunos"(idaluno) ON UPDATE CASCADE;
 <   ALTER TABLE ONLY public.aluno_nota DROP CONSTRAINT idaluno;
       public               postgres    false    4678    228    227            H           2606    16428    curso_disciplina idcurso    FK CONSTRAINT     �   ALTER TABLE ONLY public.curso_disciplina
    ADD CONSTRAINT idcurso FOREIGN KEY (idcurso) REFERENCES public."Cursos"(idcurso) ON UPDATE CASCADE ON DELETE CASCADE;
 B   ALTER TABLE ONLY public.curso_disciplina DROP CONSTRAINT idcurso;
       public               postgres    false    225    222    4674            J           2606    16446    Alunos idcurso    FK CONSTRAINT     �   ALTER TABLE ONLY public."Alunos"
    ADD CONSTRAINT idcurso FOREIGN KEY (idcurso) REFERENCES public."Cursos"(idcurso) ON UPDATE CASCADE ON DELETE SET NULL;
 :   ALTER TABLE ONLY public."Alunos" DROP CONSTRAINT idcurso;
       public               postgres    false    227    222    4674            I           2606    16433    curso_disciplina iddisciplina    FK CONSTRAINT     �   ALTER TABLE ONLY public.curso_disciplina
    ADD CONSTRAINT iddisciplina FOREIGN KEY (iddisciplina) REFERENCES public."Disciplinas"(iddisciplina) ON UPDATE CASCADE ON DELETE CASCADE;
 G   ALTER TABLE ONLY public.curso_disciplina DROP CONSTRAINT iddisciplina;
       public               postgres    false    4676    225    224            L           2606    16459    aluno_nota iddisciplina    FK CONSTRAINT     �   ALTER TABLE ONLY public.aluno_nota
    ADD CONSTRAINT iddisciplina FOREIGN KEY (iddisciplina) REFERENCES public."Disciplinas"(iddisciplina) ON UPDATE CASCADE;
 A   ALTER TABLE ONLY public.aluno_nota DROP CONSTRAINT iddisciplina;
       public               postgres    false    4676    228    224            G           2606    16420    Disciplinas idprofessor    FK CONSTRAINT     �   ALTER TABLE ONLY public."Disciplinas"
    ADD CONSTRAINT idprofessor FOREIGN KEY (idprofessor) REFERENCES public."Professores"(idprofessor) ON UPDATE CASCADE ON DELETE SET NULL;
 C   ALTER TABLE ONLY public."Disciplinas" DROP CONSTRAINT idprofessor;
       public               postgres    false    224    219    4672            �      x������ � �      �      x������ � �      �      x������ � �      �      x������ � �      �      x������ � �      �      x������ � �      �      x������ � �     