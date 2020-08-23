import { IPermissions } from 'app/shared/model/permissions.model';

export interface IUserWithPerms {
  id?: number;
  user?: number;
  users?: IPermissions[];
}

export const defaultValue: Readonly<IUserWithPerms> = {};
