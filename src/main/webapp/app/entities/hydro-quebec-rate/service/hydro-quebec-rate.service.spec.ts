import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IHydroQuebecRate } from '../hydro-quebec-rate.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../hydro-quebec-rate.test-samples';

import { HydroQuebecRateService } from './hydro-quebec-rate.service';

const requireRestSample: IHydroQuebecRate = {
  ...sampleWithRequiredData,
};

describe('HydroQuebecRate Service', () => {
  let service: HydroQuebecRateService;
  let httpMock: HttpTestingController;
  let expectedResult: IHydroQuebecRate | IHydroQuebecRate[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(HydroQuebecRateService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a HydroQuebecRate', () => {
      const hydroQuebecRate = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(hydroQuebecRate).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a HydroQuebecRate', () => {
      const hydroQuebecRate = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(hydroQuebecRate).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a HydroQuebecRate', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of HydroQuebecRate', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a HydroQuebecRate', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addHydroQuebecRateToCollectionIfMissing', () => {
      it('should add a HydroQuebecRate to an empty array', () => {
        const hydroQuebecRate: IHydroQuebecRate = sampleWithRequiredData;
        expectedResult = service.addHydroQuebecRateToCollectionIfMissing([], hydroQuebecRate);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(hydroQuebecRate);
      });

      it('should not add a HydroQuebecRate to an array that contains it', () => {
        const hydroQuebecRate: IHydroQuebecRate = sampleWithRequiredData;
        const hydroQuebecRateCollection: IHydroQuebecRate[] = [
          {
            ...hydroQuebecRate,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addHydroQuebecRateToCollectionIfMissing(hydroQuebecRateCollection, hydroQuebecRate);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a HydroQuebecRate to an array that doesn't contain it", () => {
        const hydroQuebecRate: IHydroQuebecRate = sampleWithRequiredData;
        const hydroQuebecRateCollection: IHydroQuebecRate[] = [sampleWithPartialData];
        expectedResult = service.addHydroQuebecRateToCollectionIfMissing(hydroQuebecRateCollection, hydroQuebecRate);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(hydroQuebecRate);
      });

      it('should add only unique HydroQuebecRate to an array', () => {
        const hydroQuebecRateArray: IHydroQuebecRate[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const hydroQuebecRateCollection: IHydroQuebecRate[] = [sampleWithRequiredData];
        expectedResult = service.addHydroQuebecRateToCollectionIfMissing(hydroQuebecRateCollection, ...hydroQuebecRateArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const hydroQuebecRate: IHydroQuebecRate = sampleWithRequiredData;
        const hydroQuebecRate2: IHydroQuebecRate = sampleWithPartialData;
        expectedResult = service.addHydroQuebecRateToCollectionIfMissing([], hydroQuebecRate, hydroQuebecRate2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(hydroQuebecRate);
        expect(expectedResult).toContain(hydroQuebecRate2);
      });

      it('should accept null and undefined values', () => {
        const hydroQuebecRate: IHydroQuebecRate = sampleWithRequiredData;
        expectedResult = service.addHydroQuebecRateToCollectionIfMissing([], null, hydroQuebecRate, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(hydroQuebecRate);
      });

      it('should return initial array if no HydroQuebecRate is added', () => {
        const hydroQuebecRateCollection: IHydroQuebecRate[] = [sampleWithRequiredData];
        expectedResult = service.addHydroQuebecRateToCollectionIfMissing(hydroQuebecRateCollection, undefined, null);
        expect(expectedResult).toEqual(hydroQuebecRateCollection);
      });
    });

    describe('compareHydroQuebecRate', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareHydroQuebecRate(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareHydroQuebecRate(entity1, entity2);
        const compareResult2 = service.compareHydroQuebecRate(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareHydroQuebecRate(entity1, entity2);
        const compareResult2 = service.compareHydroQuebecRate(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareHydroQuebecRate(entity1, entity2);
        const compareResult2 = service.compareHydroQuebecRate(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
