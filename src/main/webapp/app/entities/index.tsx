import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Product from './product';
import UserWithPerms from './user-with-perms';
import Permissions from './permissions';
/* jhipster-needle-add-route-import - JHipster will add routes here */

import { AUTHORITIES } from 'app/config/constants';
import PrivateRoute from 'app/shared/auth/private-route';
import PermissionRoute from 'app/shared/auth/permission-route';
import { Perms } from 'app/shared/model/enumerations/perms.model';

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <PermissionRoute path={`${match.url}product`} component={Product} hasAnyAuthorities={[AUTHORITIES.USER]} permissionRequired={Perms.TO_SHOW_PRODUCT_LIST} />
      <PrivateRoute path={`${match.url}user-with-perms`} component={UserWithPerms} hasAnyAuthorities={[AUTHORITIES.ADMIN]} />
      <PrivateRoute path={`${match.url}permissions`} component={Permissions}  hasAnyAuthorities={[AUTHORITIES.ADMIN]}/>
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
