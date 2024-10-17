-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3307
-- Tempo de geração: 16-Out-2024 às 22:19
-- Versão do servidor: 11.2.2-MariaDB
-- versão do PHP: 8.2.13

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `infoget`
--

-- --------------------------------------------------------

--
-- Estrutura da tabela `alunos`
--

DROP TABLE IF EXISTS `alunos`;
CREATE TABLE IF NOT EXISTS `alunos` (
  `idaluno` int(11) NOT NULL AUTO_INCREMENT,
  `nome_aluno` int(60) NOT NULL,
  `email` int(60) NOT NULL,
  `password` int(60) NOT NULL,
  `idcurso` int(11) NOT NULL,
  PRIMARY KEY (`idaluno`),
  KEY `idcurso` (`idcurso`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `aluno_nota`
--

DROP TABLE IF EXISTS `aluno_nota`;
CREATE TABLE IF NOT EXISTS `aluno_nota` (
  `idaluno` int(11) NOT NULL,
  `iddisciplina` int(11) NOT NULL,
  `nota` int(11) DEFAULT NULL,
  KEY `idaluno` (`idaluno`),
  KEY `iddisciplina` (`iddisciplina`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `cursos`
--

DROP TABLE IF EXISTS `cursos`;
CREATE TABLE IF NOT EXISTS `cursos` (
  `idcurso` int(11) NOT NULL AUTO_INCREMENT,
  `nome_curso` varchar(60) NOT NULL,
  PRIMARY KEY (`idcurso`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `curso_disciplina`
--

DROP TABLE IF EXISTS `curso_disciplina`;
CREATE TABLE IF NOT EXISTS `curso_disciplina` (
  `idcurso` int(11) NOT NULL,
  `iddisciplina` int(11) NOT NULL,
  KEY `idcurso` (`idcurso`),
  KEY `iddisciplina` (`iddisciplina`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `disciplinas`
--

DROP TABLE IF EXISTS `disciplinas`;
CREATE TABLE IF NOT EXISTS `disciplinas` (
  `iddisciplina` int(11) NOT NULL AUTO_INCREMENT,
  `nome_disciplina` varchar(60) NOT NULL,
  `carga_horaria` int(11) DEFAULT NULL,
  `idprofessor` int(11) DEFAULT NULL,
  PRIMARY KEY (`iddisciplina`),
  KEY `idprofessor` (`idprofessor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `professores`
--

DROP TABLE IF EXISTS `professores`;
CREATE TABLE IF NOT EXISTS `professores` (
  `idprofessor` int(11) NOT NULL AUTO_INCREMENT,
  `nome_professor` varchar(60) NOT NULL,
  `email` varchar(60) NOT NULL,
  `password` varchar(60) NOT NULL,
  PRIMARY KEY (`idprofessor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Estrutura da tabela `secretarios`
--

DROP TABLE IF EXISTS `secretarios`;
CREATE TABLE IF NOT EXISTS `secretarios` (
  `idsecretario` int(11) NOT NULL AUTO_INCREMENT,
  `nome_secretario` varchar(60) NOT NULL,
  `email` varchar(60) NOT NULL,
  `password` varchar(60) NOT NULL,
  PRIMARY KEY (`idsecretario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Restrições para despejos de tabelas
--

--
-- Limitadores para a tabela `alunos`
--
ALTER TABLE `alunos`
  ADD CONSTRAINT `alunos_ibfk_1` FOREIGN KEY (`idcurso`) REFERENCES `cursos` (`idcurso`);

--
-- Limitadores para a tabela `aluno_nota`
--
ALTER TABLE `aluno_nota`
  ADD CONSTRAINT `aluno_nota_ibfk_1` FOREIGN KEY (`idaluno`) REFERENCES `alunos` (`idaluno`),
  ADD CONSTRAINT `aluno_nota_ibfk_2` FOREIGN KEY (`iddisciplina`) REFERENCES `disciplinas` (`iddisciplina`);

--
-- Limitadores para a tabela `curso_disciplina`
--
ALTER TABLE `curso_disciplina`
  ADD CONSTRAINT `curso_disciplina_ibfk_1` FOREIGN KEY (`idcurso`) REFERENCES `cursos` (`idcurso`),
  ADD CONSTRAINT `curso_disciplina_ibfk_2` FOREIGN KEY (`iddisciplina`) REFERENCES `disciplinas` (`iddisciplina`);

--
-- Limitadores para a tabela `disciplinas`
--
ALTER TABLE `disciplinas`
  ADD CONSTRAINT `disciplinas_ibfk_1` FOREIGN KEY (`idprofessor`) REFERENCES `professores` (`idprofessor`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
