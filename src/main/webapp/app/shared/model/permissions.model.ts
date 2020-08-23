import { IUserWithPerms } from 'app/shared/model/user-with-perms.model';
import { Perms } from 'app/shared/model/enumerations/perms.model';

export interface IPermissions {
  id?: number;
  perm?: Perms;
  userWithPerms?: IUserWithPerms;
}

export const defaultValue: Readonly<IPermissions> = {};
