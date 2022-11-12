import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './recurring-bills.reducer';

export const RecurringBillsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const recurringBillsEntity = useAppSelector(state => state.recurringBills.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="recurringBillsDetailsHeading">Recurring Bills</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{recurringBillsEntity.id}</dd>
          <dt>
            <span id="amount">Amount</span>
          </dt>
          <dd>{recurringBillsEntity.amount}</dd>
          <dt>
            <span id="tenure">Tenure</span>
          </dt>
          <dd>{recurringBillsEntity.tenure}</dd>
          <dt>
            <span id="primaryCategory">Primary Category</span>
          </dt>
          <dd>{recurringBillsEntity.primaryCategory}</dd>
          <dt>
            <span id="secondaryCategory">Secondary Category</span>
          </dt>
          <dd>{recurringBillsEntity.secondaryCategory}</dd>
        </dl>
        <Button tag={Link} to="/recurring-bills" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/recurring-bills/${recurringBillsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default RecurringBillsDetail;
