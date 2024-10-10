import { IAlunos } from 'app/shared/model/alunos.model';
import { IDisciplinas } from 'app/shared/model/disciplinas.model';

export interface INotas {
  id?: number;
  nota?: number | null;
  alunos?: IAlunos | null;
  disciplinas?: IDisciplinas | null;
}

export const defaultValue: Readonly<INotas> = {};
