import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITransaction } from 'app/shared/model/transaction.model';
import { TransactionType } from 'app/shared/model/enumerations/transaction-type.model';
import { getEntity, updateEntity, createEntity, reset } from './transaction.reducer';

export const TransactionUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const transactionEntity = useAppSelector(state => state.transaction.entity);
  const loading = useAppSelector(state => state.transaction.loading);
  const updating = useAppSelector(state => state.transaction.updating);
  const updateSuccess = useAppSelector(state => state.transaction.updateSuccess);
  const transactionTypeValues = Object.keys(TransactionType);

  const handleClose = () => {
    navigate('/transaction');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.date = convertDateTimeToServer(values.date);

    const entity = {
      ...transactionEntity,
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
          date: displayDefaultDateTime(),
        }
      : {
          type: 'Earning',
          ...transactionEntity,
          date: convertDateTimeFromServer(transactionEntity.date),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="expnsrApp.transaction.home.createOrEditLabel" data-cy="TransactionCreateUpdateHeading">
            Create or edit a Transaction
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="transaction-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Date"
                id="transaction-date"
                name="date"
                data-cy="date"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField label="Type" id="transaction-type" name="type" data-cy="type" type="select">
                {transactionTypeValues.map(transactionType => (
                  <option value={transactionType} key={transactionType}>
                    {transactionType}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                label="Primary Category"
                id="transaction-primaryCategory"
                name="primaryCategory"
                data-cy="primaryCategory"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                }}
              />
              <ValidatedField
                label="Secondary Category"
                id="transaction-secondaryCategory"
                name="secondaryCategory"
                data-cy="secondaryCategory"
                type="text"
              />
              <ValidatedField
                label="Amount"
                id="transaction-amount"
                name="amount"
                data-cy="amount"
                type="text"
                validate={{
                  required: { value: true, message: 'This field is required.' },
                  validate: v => isNumber(v) || 'This field should be a number.',
                }}
              />
              <ValidatedField label="Due" id="transaction-due" name="due" data-cy="due" type="text" />
              <ValidatedField label="Title" id="transaction-title" name="title" data-cy="title" type="text" />
              <ValidatedField label="Description" id="transaction-description" name="description" data-cy="description" type="text" />
              <ValidatedField label="Tags" id="transaction-tags" name="tags" data-cy="tags" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/transaction" replace color="info">
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

export default TransactionUpdate;
