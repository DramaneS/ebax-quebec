import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IHydroQuebecRate } from '../hydro-quebec-rate.model';
import { HydroQuebecRateService } from '../service/hydro-quebec-rate.service';

export const hydroQuebecRateResolve = (route: ActivatedRouteSnapshot): Observable<null | IHydroQuebecRate> => {
  const id = route.params['id'];
  if (id) {
    return inject(HydroQuebecRateService)
      .find(id)
      .pipe(
        mergeMap((hydroQuebecRate: HttpResponse<IHydroQuebecRate>) => {
          if (hydroQuebecRate.body) {
            return of(hydroQuebecRate.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default hydroQuebecRateResolve;
