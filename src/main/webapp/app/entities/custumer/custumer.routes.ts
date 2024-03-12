import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { CustumerComponent } from './list/custumer.component';
import { CustumerDetailComponent } from './detail/custumer-detail.component';
import { CustumerUpdateComponent } from './update/custumer-update.component';
import CustumerResolve from './route/custumer-routing-resolve.service';

const custumerRoute: Routes = [
  {
    path: '',
    component: CustumerComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CustumerDetailComponent,
    resolve: {
      custumer: CustumerResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CustumerUpdateComponent,
    resolve: {
      custumer: CustumerResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CustumerUpdateComponent,
    resolve: {
      custumer: CustumerResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default custumerRoute;
