import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFixedDeposit } from 'app/shared/model/fixed-deposit.model';
import { getEntities as getFixedDeposits } from 'app/entities/fixed-deposit/fixed-deposit.reducer';
import { IInterest } from 'app/shared/model/interest.model';
import { getEntity, updateEntity, createEntity, reset } from './interest.reducer';

export const InterestUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const fixedDeposits = useAppSelector(state => state.fixedDeposit.entities);
  const interestEntity = useAppSelector(state => state.interest.entity);
  const loading = useAppSelector(state => state.interest.loading);
  const updating = useAppSelector(state => state.interest.updating);
  const updateSuccess = useAppSelector(state => state.interest.updateSuccess);

  const handleClose = () => {
    navigate('/interest');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getFixedDeposits({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.date = convertDateTimeToServer(values.date);

    const entity = {
      ...interestEntity,
      ...values,
      fixedDeposit: fixedDeposits.find(it => it.id.toString() === values.fixedDeposit.toString()),
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
          date: displayDefaultDateTime(),
        }
      : {
          ...interestEntity,
          date: convertDateTimeFromServer(interestEntity.date),
          fixedDeposit: interestEntity?.fixedDeposit?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="expnsrApp.interest.home.createOrEditLabel" data-cy="InterestCreateUpdateHeading">
            Create or edit a Interest
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="interest-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Date"
                id="interest-date"
                name="date"
                data-cy="date"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Interest Rate"
                id="interest-interestRate"
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
                id="interest-taxRate"
                name="taxRate"
                data-cy="taxRate"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Amount"
                id="interest-amount"
                name="amount"
                data-cy="amount"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField
                label="Tax"
                id="interest-tax"
                name="tax"
                data-cy="tax"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField label="Verified" id="interest-verified" name="verified" data-cy="verified" check type="checkbox" />
              <ValidatedField id="interest-fixedDeposit" name="fixedDeposit" data-cy="fixedDeposit" label="Fixed Deposit" type="select">
                <option value="" key="0" />
                {fixedDeposits
                  ? fixedDeposits.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/interest" replace color="info">
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

export default InterestUpdate;
