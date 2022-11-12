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
import { IDeposit } from 'app/shared/model/deposit.model';
import { getEntity, updateEntity, createEntity, reset } from './deposit.reducer';

export const DepositUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const fixedDeposits = useAppSelector(state => state.fixedDeposit.entities);
  const depositEntity = useAppSelector(state => state.deposit.entity);
  const loading = useAppSelector(state => state.deposit.loading);
  const updating = useAppSelector(state => state.deposit.updating);
  const updateSuccess = useAppSelector(state => state.deposit.updateSuccess);

  const handleClose = () => {
    navigate('/deposit');
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
      ...depositEntity,
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
          ...depositEntity,
          date: convertDateTimeFromServer(depositEntity.date),
          fixedDeposit: depositEntity?.fixedDeposit?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="expnsrApp.deposit.home.createOrEditLabel" data-cy="DepositCreateUpdateHeading">
            Create or edit a Deposit
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="deposit-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Date"
                id="deposit-date"
                name="date"
                data-cy="date"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Amount"
                id="deposit-amount"
                name="amount"
                data-cy="amount"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField label="Verified" id="deposit-verified" name="verified" data-cy="verified" check type="checkbox" />
              <ValidatedField id="deposit-fixedDeposit" name="fixedDeposit" data-cy="fixedDeposit" label="Fixed Deposit" type="select">
                <option value="" key="0" />
                {fixedDeposits
                  ? fixedDeposits.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/deposit" replace color="info">
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

export default DepositUpdate;
