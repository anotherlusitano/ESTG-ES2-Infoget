export interface IProfessores {
  id?: number;
  nomeprofessor?: string;
  email?: string;
  password?: string;
}

export const defaultValue: Readonly<IProfessores> = {};
