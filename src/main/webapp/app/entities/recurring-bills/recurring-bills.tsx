import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRecurringBills } from 'app/shared/model/recurring-bills.model';
import { getEntities } from './recurring-bills.reducer';

export const RecurringBills = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const recurringBillsList = useAppSelector(state => state.recurringBills.entities);
  const loading = useAppSelector(state => state.recurringBills.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  return (
    <div>
      <h2 id="recurring-bills-heading" data-cy="RecurringBillsHeading">
        Recurring Bills
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/recurring-bills/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Recurring Bills
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {recurringBillsList && recurringBillsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Amount</th>
                <th>Tenure</th>
                <th>Primary Category</th>
                <th>Secondary Category</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {recurringBillsList.map((recurringBills, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/recurring-bills/${recurringBills.id}`} color="link" size="sm">
                      {recurringBills.id}
                    </Button>
                  </td>
                  <td>{recurringBills.amount}</td>
                  <td>{recurringBills.tenure}</td>
                  <td>{recurringBills.primaryCategory}</td>
                  <td>{recurringBills.secondaryCategory}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/recurring-bills/${recurringBills.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/recurring-bills/${recurringBills.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/recurring-bills/${recurringBills.id}/delete`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Recurring Bills found</div>
        )}
      </div>
    </div>
  );
};

export default RecurringBills;
