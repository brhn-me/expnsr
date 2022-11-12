import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './deposit.reducer';

export const DepositDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const depositEntity = useAppSelector(state => state.deposit.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="depositDetailsHeading">Deposit</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{depositEntity.id}</dd>
          <dt>
            <span id="date">Date</span>
          </dt>
          <dd>{depositEntity.date ? <TextFormat value={depositEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="amount">Amount</span>
          </dt>
          <dd>{depositEntity.amount}</dd>
          <dt>
            <span id="verified">Verified</span>
          </dt>
          <dd>{depositEntity.verified ? 'true' : 'false'}</dd>
          <dt>Fixed Deposit</dt>
          <dd>{depositEntity.fixedDeposit ? depositEntity.fixedDeposit.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/deposit" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/deposit/${depositEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DepositDetail;
