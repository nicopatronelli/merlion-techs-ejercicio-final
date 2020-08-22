import './home.scss';
import React from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { connect } from 'react-redux';
import { Container, Row, Col, Alert } from 'reactstrap';
import StatisticalCharts from '../statistical-charts/StatisticalCharts';
import { hasAnyAuthority } from 'app/shared/auth/private-route';
import { AUTHORITIES } from 'app/config/constants';

export type IHomeProp = StateProps;

export const Home = (props: IHomeProp) => {
  const { account, isAdmin } = props;
  // eslint-disable-next-line no-console
  console.log("account object INFO");
  // eslint-disable-next-line no-console
  console.log(JSON.stringify(account));
  // eslint-disable-next-line no-console
  console.log("Is ADMIN");
  // eslint-disable-next-line no-console
  console.log(JSON.stringify(isAdmin));
  // const isAdmin = hasAnyAuthority(account.authorities, [AUTHORITIES.ADMIN]);


  return (
    <Container>
    <Row>
      <Col>
        <h2>
          <Translate contentKey="home.title">Welcome, Java Hipster!</Translate>
        </h2>
        <p className="lead">
          <Translate contentKey="home.subtitle">This is your homepage</Translate>
        </p>
        {account && account.login ? (
          <div>
            <Alert color="success">
              <Translate contentKey="home.logged.message" interpolate={{ username: account.login }}>
                You are logged in as user {account.login}.
              </Translate>
            </Alert>
          </div>
        ) : (
          <div>
            <Alert color="warning">
              <Translate contentKey="global.messages.info.authenticated.prefix">If you want to </Translate>
              <Link to="/login" className="alert-link">
                <Translate contentKey="global.messages.info.authenticated.link"> sign in</Translate>
              </Link>
              <Translate contentKey="global.messages.info.authenticated.suffix">
                , you can try the default accounts:
                <br />- Administrator (login=&quot;admin&quot; and password=&quot;admin&quot;)
                <br />- User (login=&quot;user&quot; and password=&quot;user&quot;).
              </Translate>
            </Alert>

            <Alert color="warning">
              <Translate contentKey="global.messages.info.register.noaccount">You do not have an account yet?</Translate>&nbsp;
              <Link to="/account/register" className="alert-link">
                <Translate contentKey="global.messages.info.register.link">Register a new account</Translate>
              </Link>
            </Alert>
          </div>
        )}
      {isAdmin ? <StatisticalCharts /> : null}
      </Col>
    </Row>
    </Container>
  );
};

const mapStateToProps = storeState => ({
  account: storeState.authentication.account,
  isAuthenticated: storeState.authentication.isAuthenticated,
  isAdmin: hasAnyAuthority(storeState.authentication.account.authorities, [AUTHORITIES.ADMIN]),
});

type StateProps = ReturnType<typeof mapStateToProps>;

export default connect(mapStateToProps)(Home);