import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICustumer, NewCustumer } from '../custumer.model';

export type PartialUpdateCustumer = Partial<ICustumer> & Pick<ICustumer, 'id'>;

export type EntityResponseType = HttpResponse<ICustumer>;
export type EntityArrayResponseType = HttpResponse<ICustumer[]>;

@Injectable({ providedIn: 'root' })
export class CustumerService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/custumers');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(custumer: NewCustumer): Observable<EntityResponseType> {
    return this.http.post<ICustumer>(this.resourceUrl, custumer, { observe: 'response' });
  }

  update(custumer: ICustumer): Observable<EntityResponseType> {
    return this.http.put<ICustumer>(`${this.resourceUrl}/${this.getCustumerIdentifier(custumer)}`, custumer, { observe: 'response' });
  }

  partialUpdate(custumer: PartialUpdateCustumer): Observable<EntityResponseType> {
    return this.http.patch<ICustumer>(`${this.resourceUrl}/${this.getCustumerIdentifier(custumer)}`, custumer, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICustumer>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICustumer[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCustumerIdentifier(custumer: Pick<ICustumer, 'id'>): number {
    return custumer.id;
  }

  compareCustumer(o1: Pick<ICustumer, 'id'> | null, o2: Pick<ICustumer, 'id'> | null): boolean {
    return o1 && o2 ? this.getCustumerIdentifier(o1) === this.getCustumerIdentifier(o2) : o1 === o2;
  }

  addCustumerToCollectionIfMissing<Type extends Pick<ICustumer, 'id'>>(
    custumerCollection: Type[],
    ...custumersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const custumers: Type[] = custumersToCheck.filter(isPresent);
    if (custumers.length > 0) {
      const custumerCollectionIdentifiers = custumerCollection.map(custumerItem => this.getCustumerIdentifier(custumerItem)!);
      const custumersToAdd = custumers.filter(custumerItem => {
        const custumerIdentifier = this.getCustumerIdentifier(custumerItem);
        if (custumerCollectionIdentifiers.includes(custumerIdentifier)) {
          return false;
        }
        custumerCollectionIdentifiers.push(custumerIdentifier);
        return true;
      });
      return [...custumersToAdd, ...custumerCollection];
    }
    return custumerCollection;
  }
}
