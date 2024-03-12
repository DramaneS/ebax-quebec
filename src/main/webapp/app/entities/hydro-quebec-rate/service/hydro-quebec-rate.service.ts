import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IHydroQuebecRate, NewHydroQuebecRate } from '../hydro-quebec-rate.model';

export type PartialUpdateHydroQuebecRate = Partial<IHydroQuebecRate> & Pick<IHydroQuebecRate, 'id'>;

export type EntityResponseType = HttpResponse<IHydroQuebecRate>;
export type EntityArrayResponseType = HttpResponse<IHydroQuebecRate[]>;

@Injectable({ providedIn: 'root' })
export class HydroQuebecRateService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/hydro-quebec-rates');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(hydroQuebecRate: NewHydroQuebecRate): Observable<EntityResponseType> {
    return this.http.post<IHydroQuebecRate>(this.resourceUrl, hydroQuebecRate, { observe: 'response' });
  }

  update(hydroQuebecRate: IHydroQuebecRate): Observable<EntityResponseType> {
    return this.http.put<IHydroQuebecRate>(`${this.resourceUrl}/${this.getHydroQuebecRateIdentifier(hydroQuebecRate)}`, hydroQuebecRate, {
      observe: 'response',
    });
  }

  partialUpdate(hydroQuebecRate: PartialUpdateHydroQuebecRate): Observable<EntityResponseType> {
    return this.http.patch<IHydroQuebecRate>(`${this.resourceUrl}/${this.getHydroQuebecRateIdentifier(hydroQuebecRate)}`, hydroQuebecRate, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IHydroQuebecRate>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IHydroQuebecRate[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getHydroQuebecRateIdentifier(hydroQuebecRate: Pick<IHydroQuebecRate, 'id'>): number {
    return hydroQuebecRate.id;
  }

  compareHydroQuebecRate(o1: Pick<IHydroQuebecRate, 'id'> | null, o2: Pick<IHydroQuebecRate, 'id'> | null): boolean {
    return o1 && o2 ? this.getHydroQuebecRateIdentifier(o1) === this.getHydroQuebecRateIdentifier(o2) : o1 === o2;
  }

  addHydroQuebecRateToCollectionIfMissing<Type extends Pick<IHydroQuebecRate, 'id'>>(
    hydroQuebecRateCollection: Type[],
    ...hydroQuebecRatesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const hydroQuebecRates: Type[] = hydroQuebecRatesToCheck.filter(isPresent);
    if (hydroQuebecRates.length > 0) {
      const hydroQuebecRateCollectionIdentifiers = hydroQuebecRateCollection.map(
        hydroQuebecRateItem => this.getHydroQuebecRateIdentifier(hydroQuebecRateItem)!,
      );
      const hydroQuebecRatesToAdd = hydroQuebecRates.filter(hydroQuebecRateItem => {
        const hydroQuebecRateIdentifier = this.getHydroQuebecRateIdentifier(hydroQuebecRateItem);
        if (hydroQuebecRateCollectionIdentifiers.includes(hydroQuebecRateIdentifier)) {
          return false;
        }
        hydroQuebecRateCollectionIdentifiers.push(hydroQuebecRateIdentifier);
        return true;
      });
      return [...hydroQuebecRatesToAdd, ...hydroQuebecRateCollection];
    }
    return hydroQuebecRateCollection;
  }
}
