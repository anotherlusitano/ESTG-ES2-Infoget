export interface ISecretarios {
  id?: number;
  nomesecretario?: string;
  email?: string;
  password?: string;
}

export const defaultValue: Readonly<ISecretarios> = {};
