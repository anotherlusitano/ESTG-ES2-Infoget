import { ICursos } from 'app/shared/model/cursos.model';
import { IDisciplinas } from 'app/shared/model/disciplinas.model';

export interface ICursoDisciplina {
  id?: number;
  cursos?: ICursos | null;
  disciplinas?: IDisciplinas | null;
}

export const defaultValue: Readonly<ICursoDisciplina> = {};
