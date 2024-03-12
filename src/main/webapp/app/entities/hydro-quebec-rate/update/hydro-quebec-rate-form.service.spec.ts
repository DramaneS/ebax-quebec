import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../hydro-quebec-rate.test-samples';

import { HydroQuebecRateFormService } from './hydro-quebec-rate-form.service';

describe('HydroQuebecRate Form Service', () => {
  let service: HydroQuebecRateFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HydroQuebecRateFormService);
  });

  describe('Service methods', () => {
    describe('createHydroQuebecRateFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createHydroQuebecRateFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
          }),
        );
      });

      it('passing IHydroQuebecRate should create a new form with FormGroup', () => {
        const formGroup = service.createHydroQuebecRateFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            name: expect.any(Object),
          }),
        );
      });
    });

    describe('getHydroQuebecRate', () => {
      it('should return NewHydroQuebecRate for default HydroQuebecRate initial value', () => {
        const formGroup = service.createHydroQuebecRateFormGroup(sampleWithNewData);

        const hydroQuebecRate = service.getHydroQuebecRate(formGroup) as any;

        expect(hydroQuebecRate).toMatchObject(sampleWithNewData);
      });

      it('should return NewHydroQuebecRate for empty HydroQuebecRate initial value', () => {
        const formGroup = service.createHydroQuebecRateFormGroup();

        const hydroQuebecRate = service.getHydroQuebecRate(formGroup) as any;

        expect(hydroQuebecRate).toMatchObject({});
      });

      it('should return IHydroQuebecRate', () => {
        const formGroup = service.createHydroQuebecRateFormGroup(sampleWithRequiredData);

        const hydroQuebecRate = service.getHydroQuebecRate(formGroup) as any;

        expect(hydroQuebecRate).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IHydroQuebecRate should not enable id FormControl', () => {
        const formGroup = service.createHydroQuebecRateFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewHydroQuebecRate should disable id FormControl', () => {
        const formGroup = service.createHydroQuebecRateFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
