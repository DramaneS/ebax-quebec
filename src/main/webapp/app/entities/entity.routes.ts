import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'service',
    data: { pageTitle: 'ebaxquebecApp.service.home.title' },
    loadChildren: () => import('./service/service.routes'),
  },
  {
    path: 'custumer',
    data: { pageTitle: 'ebaxquebecApp.custumer.home.title' },
    loadChildren: () => import('./custumer/custumer.routes'),
  },
  {
    path: 'invoice',
    data: { pageTitle: 'ebaxquebecApp.invoice.home.title' },
    loadChildren: () => import('./invoice/invoice.routes'),
  },
  {
    path: 'entreprise',
    data: { pageTitle: 'ebaxquebecApp.entreprise.home.title' },
    loadChildren: () => import('./entreprise/entreprise.routes'),
  },
  {
    path: 'project',
    data: { pageTitle: 'ebaxquebecApp.project.home.title' },
    loadChildren: () => import('./project/project.routes'),
  },
  {
    path: 'hydro-quebec-rate',
    data: { pageTitle: 'ebaxquebecApp.hydroQuebecRate.home.title' },
    loadChildren: () => import('./hydro-quebec-rate/hydro-quebec-rate.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
