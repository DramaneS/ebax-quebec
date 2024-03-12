import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { HydroQuebecRateComponent } from './list/hydro-quebec-rate.component';
import { HydroQuebecRateDetailComponent } from './detail/hydro-quebec-rate-detail.component';
import { HydroQuebecRateUpdateComponent } from './update/hydro-quebec-rate-update.component';
import HydroQuebecRateResolve from './route/hydro-quebec-rate-routing-resolve.service';

const hydroQuebecRateRoute: Routes = [
  {
    path: '',
    component: HydroQuebecRateComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HydroQuebecRateDetailComponent,
    resolve: {
      hydroQuebecRate: HydroQuebecRateResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HydroQuebecRateUpdateComponent,
    resolve: {
      hydroQuebecRate: HydroQuebecRateResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HydroQuebecRateUpdateComponent,
    resolve: {
      hydroQuebecRate: HydroQuebecRateResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default hydroQuebecRateRoute;
