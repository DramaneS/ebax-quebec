import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../custumer.test-samples';

import { CustumerFormService } from './custumer-form.service';

describe('Custumer Form Service', () => {
  let service: CustumerFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(CustumerFormService);
  });

  describe('Service methods', () => {
    describe('createCustumerFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createCustumerFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nameCustumer: expect.any(Object),
            adress: expect.any(Object),
            phone: expect.any(Object),
            email: expect.any(Object),
            service: expect.any(Object),
          }),
        );
      });

      it('passing ICustumer should create a new form with FormGroup', () => {
        const formGroup = service.createCustumerFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nameCustumer: expect.any(Object),
            adress: expect.any(Object),
            phone: expect.any(Object),
            email: expect.any(Object),
            service: expect.any(Object),
          }),
        );
      });
    });

    describe('getCustumer', () => {
      it('should return NewCustumer for default Custumer initial value', () => {
        const formGroup = service.createCustumerFormGroup(sampleWithNewData);

        const custumer = service.getCustumer(formGroup) as any;

        expect(custumer).toMatchObject(sampleWithNewData);
      });

      it('should return NewCustumer for empty Custumer initial value', () => {
        const formGroup = service.createCustumerFormGroup();

        const custumer = service.getCustumer(formGroup) as any;

        expect(custumer).toMatchObject({});
      });

      it('should return ICustumer', () => {
        const formGroup = service.createCustumerFormGroup(sampleWithRequiredData);

        const custumer = service.getCustumer(formGroup) as any;

        expect(custumer).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ICustumer should not enable id FormControl', () => {
        const formGroup = service.createCustumerFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewCustumer should disable id FormControl', () => {
        const formGroup = service.createCustumerFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
