import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { EntrepriseComponent } from './list/entreprise.component';
import { EntrepriseDetailComponent } from './detail/entreprise-detail.component';
import { EntrepriseUpdateComponent } from './update/entreprise-update.component';
import EntrepriseResolve from './route/entreprise-routing-resolve.service';

const entrepriseRoute: Routes = [
  {
    path: '',
    component: EntrepriseComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EntrepriseDetailComponent,
    resolve: {
      entreprise: EntrepriseResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EntrepriseUpdateComponent,
    resolve: {
      entreprise: EntrepriseResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EntrepriseUpdateComponent,
    resolve: {
      entreprise: EntrepriseResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default entrepriseRoute;
