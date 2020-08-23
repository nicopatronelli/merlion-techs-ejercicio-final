import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './user-with-perms.reducer';
import { IUserWithPerms } from 'app/shared/model/user-with-perms.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUserWithPermsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const UserWithPermsDetail = (props: IUserWithPermsDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { userWithPermsEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="testApp.userWithPerms.detail.title">UserWithPerms</Translate> [<b>{userWithPermsEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="user">
              <Translate contentKey="testApp.userWithPerms.user">User</Translate>
            </span>
          </dt>
          <dd>{userWithPermsEntity.user}</dd>
        </dl>
        <Button tag={Link} to="/user-with-perms" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/user-with-perms/${userWithPermsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ userWithPerms }: IRootState) => ({
  userWithPermsEntity: userWithPerms.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UserWithPermsDetail);
