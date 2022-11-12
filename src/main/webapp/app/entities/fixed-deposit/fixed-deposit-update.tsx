import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFixedDeposit } from 'app/shared/model/fixed-deposit.model';
import { FDType } from 'app/shared/model/enumerations/fd-type.model';
import { FDStatus } from 'app/shared/model/enumerations/fd-status.model';
import { getEntity, updateEntity, createEntity, reset } from './fixed-deposit.reducer';

export const FixedDepositUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const fixedDepositEntity = useAppSelector(state => state.fixedDeposit.entity);
  const loading = useAppSelector(state => state.fixedDeposit.loading);
  const updating = useAppSelector(state => state.fixedDeposit.updating);
  const updateSuccess = useAppSelector(state => state.fixedDeposit.updateSuccess);
  const fDTypeValues = Object.keys(FDType);
  const fDStatusValues = Object.keys(FDStatus);

  const handleClose = () => {
    navigate('/fixed-deposit');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.issueDate = convertDateTimeToServer(values.issueDate);
    values.lastRenewDate = convertDateTimeToServer(values.lastRenewDate);
    values.maturityDate = convertDateTimeToServer(values.maturityDate);
    values.monthlyDepositDate = convertDateTimeToServer(values.monthlyDepositDate);

    const entity = {
      ...fixedDepositEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          issueDate: displayDefaultDateTime(),
          lastRenewDate: displayDefaultDateTime(),
          maturityDate: displayDefaultDateTime(),
          monthlyDepositDate: displayDefaultDateTime(),
        }
      : {
          type: 'BSP',
          status: 'ACTIVE',
          ...fixedDepositEntity,
          issueDate: convertDateTimeFromServer(fixedDepositEntity.issueDate),
          lastRenewDate: convertDateTimeFromServer(fixedDepositEntity.lastRenewDate),
          maturityDate: convertDateTimeFromServer(fixedDepositEntity.maturityDate),
          monthlyDepositDate: convertDateTimeFromServer(fixedDepositEntity.monthlyDepositDate),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="expnsrApp.fixedDeposit.home.createOrEditLabel" data-cy="FixedDepositCreateUpdateHeading">
            Create or edit a Fixed Deposit
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="fixed-deposit-id" label="Id" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Type" id="fixed-deposit-type" name="type" data-cy="type" type="select">
                {fDTypeValues.map(fDType => (
                  <option value={fDType} key={fDType}>
                    {fDType}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label="Issue Date"
                id="fixed-deposit-issueDate"
                name="issueDate"
                data-cy="issueDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Last Renew Date"
                id="fixed-deposit-lastRenewDate"
                name="lastRenewDate"
                data-cy="lastRenewDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Maturity Date"
                id="fixed-deposit-maturityDate"
                name="maturityDate"
                data-cy="maturityDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Interest Rate"
                id="fixed-deposit-interestRate"
                name="interestRate"
                data-cy="interestRate"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Tax Rate"
                id="fixed-deposit-taxRate"
                name="taxRate"
                data-cy="taxRate"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Tenure"
                id="fixed-deposit-tenure"
                name="tenure"
                data-cy="tenure"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Interest Tenure"
                id="fixed-deposit-interestTenure"
                name="interestTenure"
                data-cy="interestTenure"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Amount"
                id="fixed-deposit-amount"
                name="amount"
                data-cy="amount"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Maturity Amount"
                id="fixed-deposit-maturityAmount"
                name="maturityAmount"
                data-cy="maturityAmount"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Tax"
                id="fixed-deposit-tax"
                name="tax"
                data-cy="tax"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Monthly Deposit"
                id="fixed-deposit-monthlyDeposit"
                name="monthlyDeposit"
                data-cy="monthlyDeposit"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Monthly Deposit Date"
                id="fixed-deposit-monthlyDepositDate"
                name="monthlyDepositDate"
                data-cy="monthlyDepositDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label="Bank"
                id="fixed-deposit-bank"
                name="bank"
                data-cy="bank"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField label="Auto Renew" id="fixed-deposit-autoRenew" name="autoRenew" data-cy="autoRenew" check type="checkbox" />
              <ValidatedField
                label="Renew With Interest"
                id="fixed-deposit-renewWithInterest"
                name="renewWithInterest"
                data-cy="renewWithInterest"
                check
                type="checkbox"
              />
              <ValidatedField label="Status" id="fixed-deposit-status" name="status" data-cy="status" type="select">
                {fDStatusValues.map(fDStatus => (
                  <option value={fDStatus} key={fDStatus}>
                    {fDStatus}
                  </option>
                ))}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/fixed-deposit" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default FixedDepositUpdate;
