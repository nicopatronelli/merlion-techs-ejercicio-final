import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Product from './product';
import ProductDetail from './product-detail';
import ProductUpdate from './product-update';
import ProductDeleteDialog from './product-delete-dialog';
import PrivateRoute from 'app/shared/auth/private-route';
import PermissionRoute from 'app/shared/auth/permission-route';
import { Perms } from 'app/shared/model/enumerations/perms.model';

const Routes = ({ match }) => (
    <Switch>
      <PermissionRoute exact path={`${match.url}/new`} component={ProductUpdate} permissionRequired={Perms.TO_PRODUCT_CREATE_AND_UPDATE}/>
      <PermissionRoute exact path={`${match.url}/:id/edit`} component={ProductUpdate} permissionRequired={Perms.TO_PRODUCT_CREATE_AND_UPDATE} />
      <PermissionRoute exact path={`${match.url}/:id`} component={ProductDetail} permissionRequired={Perms.TO_PRODUCT_DETAIL}/>
      <PermissionRoute exact path={`${match.url}/:id/delete`} component={ProductDeleteDialog} permissionRequired={Perms.TO_PRODUCT_DELETE}/>
      <PermissionRoute path={match.url} component={Product} permissionRequired={Perms.TO_SHOW_PRODUCT_LIST}/> 
    </Switch>
);

export default Routes;
