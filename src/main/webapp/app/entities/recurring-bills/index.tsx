import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import RecurringBills from './recurring-bills';
import RecurringBillsDetail from './recurring-bills-detail';
import RecurringBillsUpdate from './recurring-bills-update';
import RecurringBillsDeleteDialog from './recurring-bills-delete-dialog';

const RecurringBillsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<RecurringBills />} />
    <Route path="new" element={<RecurringBillsUpdate />} />
    <Route path=":id">
      <Route index element={<RecurringBillsDetail />} />
      <Route path="edit" element={<RecurringBillsUpdate />} />
      <Route path="delete" element={<RecurringBillsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RecurringBillsRoutes;
