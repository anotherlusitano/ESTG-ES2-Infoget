import { IProfessores } from 'app/shared/model/professores.model';

export interface IDisciplinas {
  id?: number;
  nomedisciplina?: string;
  cargahoraria?: number | null;
  professores?: IProfessores | null;
}

export const defaultValue: Readonly<IDisciplinas> = {};
