import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './permissions.reducer';
import { IPermissions } from 'app/shared/model/permissions.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPermissionsProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Permissions = (props: IPermissionsProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { permissionsList, match, loading } = props;
  return (
    <div>
      <h2 id="permissions-heading">
        <Translate contentKey="testApp.permissions.home.title">Permissions</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="testApp.permissions.home.createLabel">Create new Permissions</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {permissionsList && permissionsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="testApp.permissions.perm">Perm</Translate>
                </th>
                <th>
                  <Translate contentKey="testApp.permissions.userWithPerms">User With Perms</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {permissionsList.map((permissions, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${permissions.id}`} color="link" size="sm">
                      {permissions.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`testApp.Perms.${permissions.perm}`} />
                  </td>
                  <td>
                    {permissions.userWithPerms ? (
                      <Link to={`user-with-perms/${permissions.userWithPerms.id}`}>{permissions.userWithPerms.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${permissions.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${permissions.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${permissions.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="testApp.permissions.home.notFound">No Permissions found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ permissions }: IRootState) => ({
  permissionsList: permissions.entities,
  loading: permissions.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Permissions);
