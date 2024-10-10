import { ICursos } from 'app/shared/model/cursos.model';

export interface IAlunos {
  id?: number;
  nomealuno?: string;
  email?: string;
  password?: string;
  cursos?: ICursos | null;
}

export const defaultValue: Readonly<IAlunos> = {};
