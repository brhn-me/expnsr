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

import { IInterest } from 'app/shared/model/interest.model';
import { getEntities, reset } from './interest.reducer';

export const Interest = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const interestList = useAppSelector(state => state.interest.entities);
  const loading = useAppSelector(state => state.interest.loading);
  const totalItems = useAppSelector(state => state.interest.totalItems);
  const links = useAppSelector(state => state.interest.links);
  const entity = useAppSelector(state => state.interest.entity);
  const updateSuccess = useAppSelector(state => state.interest.updateSuccess);

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
      <h2 id="interest-heading" data-cy="InterestHeading">
        Interests
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/interest/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Interest
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={interestList ? interestList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {interestList && interestList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    ID <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('date')}>
                    Date <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('interestRate')}>
                    Interest Rate <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('taxRate')}>
                    Tax Rate <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('amount')}>
                    Amount <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('tax')}>
                    Tax <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('verified')}>
                    Verified <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    Fixed Deposit <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {interestList.map((interest, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/interest/${interest.id}`} color="link" size="sm">
                        {interest.id}
                      </Button>
                    </td>
                    <td>{interest.date ? <TextFormat type="date" value={interest.date} format={APP_DATE_FORMAT} /> : null}</td>
                    <td>{interest.interestRate}</td>
                    <td>{interest.taxRate}</td>
                    <td>{interest.amount}</td>
                    <td>{interest.tax}</td>
                    <td>{interest.verified ? 'true' : 'false'}</td>
                    <td>
                      {interest.fixedDeposit ? (
                        <Link to={`/fixed-deposit/${interest.fixedDeposit.id}`}>{interest.fixedDeposit.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/interest/${interest.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                        </Button>
                        <Button tag={Link} to={`/interest/${interest.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                        </Button>
                        <Button tag={Link} to={`/interest/${interest.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                          <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && <div className="alert alert-warning">No Interests found</div>
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Interest;
