import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import UserWithPerms from './user-with-perms';
import UserWithPermsDetail from './user-with-perms-detail';
import UserWithPermsUpdate from './user-with-perms-update';
import UserWithPermsDeleteDialog from './user-with-perms-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={UserWithPermsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={UserWithPermsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={UserWithPermsDetail} />
      <ErrorBoundaryRoute path={match.url} component={UserWithPerms} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={UserWithPermsDeleteDialog} />
  </>
);

export default Routes;
