import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './user-with-perms.reducer';
import { IUserWithPerms } from 'app/shared/model/user-with-perms.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUserWithPermsProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const UserWithPerms = (props: IUserWithPermsProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { userWithPermsList, match, loading } = props;
  return (
    <div>
      <h2 id="user-with-perms-heading">
        <Translate contentKey="testApp.userWithPerms.home.title">User With Perms</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="testApp.userWithPerms.home.createLabel">Create new User With Perms</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {userWithPermsList && userWithPermsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="testApp.userWithPerms.user">User</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {userWithPermsList.map((userWithPerms, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${userWithPerms.id}`} color="link" size="sm">
                      {userWithPerms.id}
                    </Button>
                  </td>
                  <td>{userWithPerms.user}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${userWithPerms.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${userWithPerms.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${userWithPerms.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="testApp.userWithPerms.home.notFound">No User With Perms found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ userWithPerms }: IRootState) => ({
  userWithPermsList: userWithPerms.entities,
  loading: userWithPerms.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UserWithPerms);
