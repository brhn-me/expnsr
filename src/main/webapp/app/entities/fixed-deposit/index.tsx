import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FixedDeposit from './fixed-deposit';
import FixedDepositDetail from './fixed-deposit-detail';
import FixedDepositUpdate from './fixed-deposit-update';
import FixedDepositDeleteDialog from './fixed-deposit-delete-dialog';

const FixedDepositRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FixedDeposit />} />
    <Route path="new" element={<FixedDepositUpdate />} />
    <Route path=":id">
      <Route index element={<FixedDepositDetail />} />
      <Route path="edit" element={<FixedDepositUpdate />} />
      <Route path="delete" element={<FixedDepositDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FixedDepositRoutes;
