import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './permissions.reducer';
import { IPermissions } from 'app/shared/model/permissions.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPermissionsDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PermissionsDetail = (props: IPermissionsDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { permissionsEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="testApp.permissions.detail.title">Permissions</Translate> [<b>{permissionsEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="perm">
              <Translate contentKey="testApp.permissions.perm">Perm</Translate>
            </span>
          </dt>
          <dd>{permissionsEntity.perm}</dd>
          <dt>
            <Translate contentKey="testApp.permissions.userWithPerms">User With Perms</Translate>
          </dt>
          <dd>{permissionsEntity.userWithPerms ? permissionsEntity.userWithPerms.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/permissions" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/permissions/${permissionsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ permissions }: IRootState) => ({
  permissionsEntity: permissions.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PermissionsDetail);
