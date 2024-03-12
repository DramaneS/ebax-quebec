import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICustumer } from '../custumer.model';
import { CustumerService } from '../service/custumer.service';

export const custumerResolve = (route: ActivatedRouteSnapshot): Observable<null | ICustumer> => {
  const id = route.params['id'];
  if (id) {
    return inject(CustumerService)
      .find(id)
      .pipe(
        mergeMap((custumer: HttpResponse<ICustumer>) => {
          if (custumer.body) {
            return of(custumer.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default custumerResolve;
