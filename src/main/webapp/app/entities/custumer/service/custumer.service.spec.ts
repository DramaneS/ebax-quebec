import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICustumer } from '../custumer.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../custumer.test-samples';

import { CustumerService } from './custumer.service';

const requireRestSample: ICustumer = {
  ...sampleWithRequiredData,
};

describe('Custumer Service', () => {
  let service: CustumerService;
  let httpMock: HttpTestingController;
  let expectedResult: ICustumer | ICustumer[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(CustumerService);
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

    it('should create a Custumer', () => {
      const custumer = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(custumer).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Custumer', () => {
      const custumer = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(custumer).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Custumer', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Custumer', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Custumer', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addCustumerToCollectionIfMissing', () => {
      it('should add a Custumer to an empty array', () => {
        const custumer: ICustumer = sampleWithRequiredData;
        expectedResult = service.addCustumerToCollectionIfMissing([], custumer);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(custumer);
      });

      it('should not add a Custumer to an array that contains it', () => {
        const custumer: ICustumer = sampleWithRequiredData;
        const custumerCollection: ICustumer[] = [
          {
            ...custumer,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addCustumerToCollectionIfMissing(custumerCollection, custumer);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Custumer to an array that doesn't contain it", () => {
        const custumer: ICustumer = sampleWithRequiredData;
        const custumerCollection: ICustumer[] = [sampleWithPartialData];
        expectedResult = service.addCustumerToCollectionIfMissing(custumerCollection, custumer);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(custumer);
      });

      it('should add only unique Custumer to an array', () => {
        const custumerArray: ICustumer[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const custumerCollection: ICustumer[] = [sampleWithRequiredData];
        expectedResult = service.addCustumerToCollectionIfMissing(custumerCollection, ...custumerArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const custumer: ICustumer = sampleWithRequiredData;
        const custumer2: ICustumer = sampleWithPartialData;
        expectedResult = service.addCustumerToCollectionIfMissing([], custumer, custumer2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(custumer);
        expect(expectedResult).toContain(custumer2);
      });

      it('should accept null and undefined values', () => {
        const custumer: ICustumer = sampleWithRequiredData;
        expectedResult = service.addCustumerToCollectionIfMissing([], null, custumer, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(custumer);
      });

      it('should return initial array if no Custumer is added', () => {
        const custumerCollection: ICustumer[] = [sampleWithRequiredData];
        expectedResult = service.addCustumerToCollectionIfMissing(custumerCollection, undefined, null);
        expect(expectedResult).toEqual(custumerCollection);
      });
    });

    describe('compareCustumer', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareCustumer(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareCustumer(entity1, entity2);
        const compareResult2 = service.compareCustumer(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareCustumer(entity1, entity2);
        const compareResult2 = service.compareCustumer(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareCustumer(entity1, entity2);
        const compareResult2 = service.compareCustumer(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
