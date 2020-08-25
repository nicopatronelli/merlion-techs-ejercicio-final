import React from 'react';
import { connect } from 'react-redux';
import { RouteProps } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { IRootState } from 'app/shared/reducers';
import PrivateRoute, { hasAnyAuthority } from './private-route';
import { AUTHORITIES } from 'app/config/constants';

interface IOwnProps extends RouteProps {
  permissionRequired: string,
  hasAnyAuthorities?: string[];
}

export interface IPermissionRoute extends IOwnProps, StateProps {}

export const PermissionRouteComponent = ({
  permissionRequired,
  hasPermission,
  hasAnyAuthorities = [],
  isAdmin,
  ...rest
}: IPermissionRoute) => {
  return (
    isAdmin || hasPermission ? (
      <PrivateRoute hasAnyAuthorities={hasAnyAuthorities}  {...rest}/>
    ) : (
      <div className="insufficient-authority">
        <div className="alert alert-danger">
          <Translate contentKey="error.http.403">You have not permissions to access this page.</Translate>
        </div>
      </div>
    )
  );
};

export const checkPermission = (permissionsOfTheCurrentUser: string[], requiredPermission: string) => {
  return permissionsOfTheCurrentUser.some(perm => perm === requiredPermission);
};

const mapStateToProps = (
  { authentication: { account } }: IRootState,
  { permissionRequired }: IOwnProps
) => ({
  hasPermission: checkPermission(account.permissions, permissionRequired),
  isAdmin: hasAnyAuthority(account.authorities, [AUTHORITIES.ADMIN]),
});

type StateProps = ReturnType<typeof mapStateToProps>;

export const PermissionRoute = connect(mapStateToProps, null, null, { pure: false })(PermissionRouteComponent);

export default PermissionRoute;
