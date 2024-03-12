import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IEntreprise } from '../entreprise.model';
import { EntrepriseService } from '../service/entreprise.service';

export const entrepriseResolve = (route: ActivatedRouteSnapshot): Observable<null | IEntreprise> => {
  const id = route.params['id'];
  if (id) {
    return inject(EntrepriseService)
      .find(id)
      .pipe(
        mergeMap((entreprise: HttpResponse<IEntreprise>) => {
          if (entreprise.body) {
            return of(entreprise.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default entrepriseResolve;
