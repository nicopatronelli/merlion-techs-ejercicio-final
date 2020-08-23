import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUserWithPerms } from 'app/shared/model/user-with-perms.model';
import { getEntities as getUserWithPerms } from 'app/entities/user-with-perms/user-with-perms.reducer';
import { getEntity, updateEntity, createEntity, reset } from './permissions.reducer';
import { IPermissions } from 'app/shared/model/permissions.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPermissionsUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PermissionsUpdate = (props: IPermissionsUpdateProps) => {
  const [userWithPermsId, setUserWithPermsId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { permissionsEntity, userWithPerms, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/permissions');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getUserWithPerms();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...permissionsEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="testApp.permissions.home.createOrEditLabel">
            <Translate contentKey="testApp.permissions.home.createOrEditLabel">Create or edit a Permissions</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : permissionsEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="permissions-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="permissions-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="permLabel" for="permissions-perm">
                  <Translate contentKey="testApp.permissions.perm">Perm</Translate>
                </Label>
                <AvInput
                  id="permissions-perm"
                  type="select"
                  className="form-control"
                  name="perm"
                  value={(!isNew && permissionsEntity.perm) || 'TO_PRODUCT_CREATE_AND_UPDATE'}
                >
                  <option value="TO_PRODUCT_CREATE_AND_UPDATE">{translate('testApp.Perms.TO_PRODUCT_CREATE_AND_UPDATE')}</option>
                  <option value="TO_SHOW_PRODUCT_LIST">{translate('testApp.Perms.TO_SHOW_PRODUCT_LIST')}</option>
                  <option value="TO_PRODUCT_DETAIL">{translate('testApp.Perms.TO_PRODUCT_DETAIL')}</option>
                  <option value="TO_PRODUCT_DELETE">{translate('testApp.Perms.TO_PRODUCT_DELETE')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="permissions-userWithPerms">
                  <Translate contentKey="testApp.permissions.userWithPerms">User With Perms</Translate>
                </Label>
                <AvInput id="permissions-userWithPerms" type="select" className="form-control" name="userWithPerms.id">
                  <option value="" key="0" />
                  {userWithPerms
                    ? userWithPerms.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/permissions" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  userWithPerms: storeState.userWithPerms.entities,
  permissionsEntity: storeState.permissions.entity,
  loading: storeState.permissions.loading,
  updating: storeState.permissions.updating,
  updateSuccess: storeState.permissions.updateSuccess,
});

const mapDispatchToProps = {
  getUserWithPerms,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PermissionsUpdate);
