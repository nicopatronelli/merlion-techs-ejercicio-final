import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './product.reducer';
import { IProduct } from 'app/shared/model/product.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

import { Perms } from '../../shared/model/enumerations/perms.model';

export interface IProductProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Product = (props: IProductProps) => {
  const { productList, match, loading, permissions } = props;

  const canShowList = permissions.includes(Perms.TO_SHOW_PRODUCT_LIST);
  const canViewDetail = permissions.includes(Perms.TO_PRODUCT_DETAIL);
  const canCreateAndUpdate = permissions.includes(Perms.TO_PRODUCT_CREATE_AND_UPDATE);
  const canDelete = permissions.includes(Perms.TO_PRODUCT_DELETE);

  return (
    <div>
      <h2 id="product-heading">
        <Translate contentKey="testApp.product.home.title">Products</Translate>
        {/* Create new product button */}
        {/* <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="testApp.product.home.createLabel">Create new Product</Translate>
        </Link> */}
        <Button tag={Link} to={`${match.url}/new`} color="primary" className="float-right jh-create-entity" id="jh-create-entity" disabled={!canCreateAndUpdate}>
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="testApp.product.home.createLabel">Create new Product</Translate>
        </Button>
      </h2>
      <div className="table-responsive">
        {productList && productList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="testApp.product.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="testApp.product.price">Price</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {productList.map((product, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${product.id}`} color="link" size="sm">
                      {product.id}
                    </Button>
                  </td>
                  <td>{product.name}</td>
                  <td>{product.price}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      {/* View button */}
                      <Button tag={Link} to={`${match.url}/${product.id}`} color="info" size="sm" disabled={!canViewDetail}>
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      {/* Edit button */}
                      <Button tag={Link} to={`${match.url}/${product.id}/edit`} color="primary" size="sm" disabled={!canCreateAndUpdate}>
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      {/* Delete button */}
                      <Button tag={Link} to={`${match.url}/${product.id}/delete`} color="danger" size="sm" disabled={!canDelete}>
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
              <Translate contentKey="testApp.product.home.notFound">No Products found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  productList: storeState.product.entities,
  loading: storeState.product.loading,
  permissions: storeState.authentication.account.permissions,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Product);
