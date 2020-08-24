import { Perms } from '../../shared/model/enumerations/perms.model';

export const checkPermissionsFor = (permRequired: Perms, permissions: Perms[], isAdmin: boolean) => {
  return isAdmin || permissions.includes(permRequired);
};
