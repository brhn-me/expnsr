import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './transaction.reducer';

export const TransactionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const transactionEntity = useAppSelector(state => state.transaction.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="transactionDetailsHeading">Transaction</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{transactionEntity.id}</dd>
          <dt>
            <span id="date">Date</span>
          </dt>
          <dd>{transactionEntity.date ? <TextFormat value={transactionEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{transactionEntity.type}</dd>
          <dt>
            <span id="primaryCategory">Primary Category</span>
          </dt>
          <dd>{transactionEntity.primaryCategory}</dd>
          <dt>
            <span id="secondaryCategory">Secondary Category</span>
          </dt>
          <dd>{transactionEntity.secondaryCategory}</dd>
          <dt>
            <span id="amount">Amount</span>
          </dt>
          <dd>{transactionEntity.amount}</dd>
          <dt>
            <span id="due">Due</span>
          </dt>
          <dd>{transactionEntity.due}</dd>
          <dt>
            <span id="title">Title</span>
          </dt>
          <dd>{transactionEntity.title}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{transactionEntity.description}</dd>
          <dt>
            <span id="tags">Tags</span>
          </dt>
          <dd>{transactionEntity.tags}</dd>
        </dl>
        <Button tag={Link} to="/transaction" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/transaction/${transactionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TransactionDetail;
