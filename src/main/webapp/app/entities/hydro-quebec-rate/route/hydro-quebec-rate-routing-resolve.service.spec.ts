import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, ActivatedRoute, Router, convertToParamMap } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { IHydroQuebecRate } from '../hydro-quebec-rate.model';
import { HydroQuebecRateService } from '../service/hydro-quebec-rate.service';

import hydroQuebecRateResolve from './hydro-quebec-rate-routing-resolve.service';

describe('HydroQuebecRate routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: HydroQuebecRateService;
  let resultHydroQuebecRate: IHydroQuebecRate | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    service = TestBed.inject(HydroQuebecRateService);
    resultHydroQuebecRate = undefined;
  });

  describe('resolve', () => {
    it('should return IHydroQuebecRate returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        hydroQuebecRateResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultHydroQuebecRate = result;
          },
        });
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultHydroQuebecRate).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        hydroQuebecRateResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultHydroQuebecRate = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultHydroQuebecRate).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IHydroQuebecRate>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        hydroQuebecRateResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultHydroQuebecRate = result;
          },
        });
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultHydroQuebecRate).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
