import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './interest.reducer';

export const InterestDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const interestEntity = useAppSelector(state => state.interest.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="interestDetailsHeading">Interest</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{interestEntity.id}</dd>
          <dt>
            <span id="date">Date</span>
          </dt>
          <dd>{interestEntity.date ? <TextFormat value={interestEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="interestRate">Interest Rate</span>
          </dt>
          <dd>{interestEntity.interestRate}</dd>
          <dt>
            <span id="taxRate">Tax Rate</span>
          </dt>
          <dd>{interestEntity.taxRate}</dd>
          <dt>
            <span id="amount">Amount</span>
          </dt>
          <dd>{interestEntity.amount}</dd>
          <dt>
            <span id="tax">Tax</span>
          </dt>
          <dd>{interestEntity.tax}</dd>
          <dt>
            <span id="verified">Verified</span>
          </dt>
          <dd>{interestEntity.verified ? 'true' : 'false'}</dd>
          <dt>Fixed Deposit</dt>
          <dd>{interestEntity.fixedDeposit ? interestEntity.fixedDeposit.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/interest" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/interest/${interestEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default InterestDetail;
