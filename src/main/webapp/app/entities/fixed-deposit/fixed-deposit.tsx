import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFixedDeposit } from 'app/shared/model/fixed-deposit.model';
import { getEntities, reset } from './fixed-deposit.reducer';

export const FixedDeposit = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const fixedDepositList = useAppSelector(state => state.fixedDeposit.entities);
  const loading = useAppSelector(state => state.fixedDeposit.loading);
  const totalItems = useAppSelector(state => state.fixedDeposit.totalItems);
  const links = useAppSelector(state => state.fixedDeposit.links);
  const entity = useAppSelector(state => state.fixedDeposit.entity);
  const updateSuccess = useAppSelector(state => state.fixedDeposit.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  return (
    <div>
      <h2 id="fixed-deposit-heading" data-cy="FixedDepositHeading">
        Fixed Deposits
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/fixed-deposit/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Fixed Deposit
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={fixedDepositList ? fixedDepositList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {fixedDepositList && fixedDepositList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    Id <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('type')}>
                    Type <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('issueDate')}>
                    Issue Date <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('lastRenewDate')}>
                    Last Renew Date <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('maturityDate')}>
                    Maturity Date <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('interestRate')}>
                    Interest Rate <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('taxRate')}>
                    Tax Rate <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('tenure')}>
                    Tenure <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('interestTenure')}>
                    Interest Tenure <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('amount')}>
                    Amount <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('maturityAmount')}>
                    Maturity Amount <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('tax')}>
                    Tax <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('monthlyDeposit')}>
                    Monthly Deposit <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('monthlyDepositDate')}>
                    Monthly Deposit Date <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('bank')}>
                    Bank <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('autoRenew')}>
                    Auto Renew <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('renewWithInterest')}>
                    Renew With Interest <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('status')}>
                    Status <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {fixedDepositList.map((fixedDeposit, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/fixed-deposit/${fixedDeposit.id}`} color="link" size="sm">
                        {fixedDeposit.id}
                      </Button>
                    </td>
                    <td>{fixedDeposit.type}</td>
                    <td>
                      {fixedDeposit.issueDate ? <TextFormat type="date" value={fixedDeposit.issueDate} format={APP_DATE_FORMAT} /> : null}
                    </td>
                    <td>
                      {fixedDeposit.lastRenewDate ? (
                        <TextFormat type="date" value={fixedDeposit.lastRenewDate} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {fixedDeposit.maturityDate ? (
                        <TextFormat type="date" value={fixedDeposit.maturityDate} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{fixedDeposit.interestRate}</td>
                    <td>{fixedDeposit.taxRate}</td>
                    <td>{fixedDeposit.tenure}</td>
                    <td>{fixedDeposit.interestTenure}</td>
                    <td>{fixedDeposit.amount}</td>
                    <td>{fixedDeposit.maturityAmount}</td>
                    <td>{fixedDeposit.tax}</td>
                    <td>{fixedDeposit.monthlyDeposit}</td>
                    <td>
                      {fixedDeposit.monthlyDepositDate ? (
                        <TextFormat type="date" value={fixedDeposit.monthlyDepositDate} format={APP_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{fixedDeposit.bank}</td>
                    <td>{fixedDeposit.autoRenew ? 'true' : 'false'}</td>
                    <td>{fixedDeposit.renewWithInterest ? 'true' : 'false'}</td>
                    <td>{fixedDeposit.status}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/fixed-deposit/${fixedDeposit.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/fixed-deposit/${fixedDeposit.id}/edit`}
                          color="primary"
                          size="sm"
                          data-cy="entityEditButton"
                        >
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/fixed-deposit/${fixedDeposit.id}/delete`}
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
            !loading && <div className="alert alert-warning">No Fixed Deposits found</div>
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default FixedDeposit;
