import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './fixed-deposit.reducer';

export const FixedDepositDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const fixedDepositEntity = useAppSelector(state => state.fixedDeposit.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="fixedDepositDetailsHeading">Fixed Deposit</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Id</span>
          </dt>
          <dd>{fixedDepositEntity.id}</dd>
          <dt>
            <span id="type">Type</span>
          </dt>
          <dd>{fixedDepositEntity.type}</dd>
          <dt>
            <span id="issueDate">Issue Date</span>
          </dt>
          <dd>
            {fixedDepositEntity.issueDate ? <TextFormat value={fixedDepositEntity.issueDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="lastRenewDate">Last Renew Date</span>
          </dt>
          <dd>
            {fixedDepositEntity.lastRenewDate ? (
              <TextFormat value={fixedDepositEntity.lastRenewDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="maturityDate">Maturity Date</span>
          </dt>
          <dd>
            {fixedDepositEntity.maturityDate ? (
              <TextFormat value={fixedDepositEntity.maturityDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="interestRate">Interest Rate</span>
          </dt>
          <dd>{fixedDepositEntity.interestRate}</dd>
          <dt>
            <span id="taxRate">Tax Rate</span>
          </dt>
          <dd>{fixedDepositEntity.taxRate}</dd>
          <dt>
            <span id="tenure">Tenure</span>
          </dt>
          <dd>{fixedDepositEntity.tenure}</dd>
          <dt>
            <span id="interestTenure">Interest Tenure</span>
          </dt>
          <dd>{fixedDepositEntity.interestTenure}</dd>
          <dt>
            <span id="amount">Amount</span>
          </dt>
          <dd>{fixedDepositEntity.amount}</dd>
          <dt>
            <span id="maturityAmount">Maturity Amount</span>
          </dt>
          <dd>{fixedDepositEntity.maturityAmount}</dd>
          <dt>
            <span id="tax">Tax</span>
          </dt>
          <dd>{fixedDepositEntity.tax}</dd>
          <dt>
            <span id="monthlyDeposit">Monthly Deposit</span>
          </dt>
          <dd>{fixedDepositEntity.monthlyDeposit}</dd>
          <dt>
            <span id="monthlyDepositDate">Monthly Deposit Date</span>
          </dt>
          <dd>
            {fixedDepositEntity.monthlyDepositDate ? (
              <TextFormat value={fixedDepositEntity.monthlyDepositDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="bank">Bank</span>
          </dt>
          <dd>{fixedDepositEntity.bank}</dd>
          <dt>
            <span id="autoRenew">Auto Renew</span>
          </dt>
          <dd>{fixedDepositEntity.autoRenew ? 'true' : 'false'}</dd>
          <dt>
            <span id="renewWithInterest">Renew With Interest</span>
          </dt>
          <dd>{fixedDepositEntity.renewWithInterest ? 'true' : 'false'}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{fixedDepositEntity.status}</dd>
        </dl>
        <Button tag={Link} to="/fixed-deposit" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/fixed-deposit/${fixedDepositEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default FixedDepositDetail;
