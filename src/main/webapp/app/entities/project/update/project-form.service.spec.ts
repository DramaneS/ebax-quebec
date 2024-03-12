import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../project.test-samples';

import { ProjectFormService } from './project-form.service';

describe('Project Form Service', () => {
  let service: ProjectFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ProjectFormService);
  });

  describe('Service methods', () => {
    describe('createProjectFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createProjectFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nameBuilding: expect.any(Object),
            fullAddressBuilding: expect.any(Object),
            numberHousingUnits: expect.any(Object),
            electricitySupplier: expect.any(Object),
            hydroQuebecContractNumber: expect.any(Object),
            hydroQuebecMeterNumber: expect.any(Object),
            ifSeveralMeters: expect.any(Object),
            specifyMeteIntended: expect.any(Object),
            hydroQuebecAccountNumber: expect.any(Object),
            fileNumber: expect.any(Object),
            typeBuillding: expect.any(Object),
            natureProject: expect.any(Object),
            startDateWork: expect.any(Object),
            endDateWork: expect.any(Object),
            hydroQuebecRate: expect.any(Object),
          }),
        );
      });

      it('passing IProject should create a new form with FormGroup', () => {
        const formGroup = service.createProjectFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            nameBuilding: expect.any(Object),
            fullAddressBuilding: expect.any(Object),
            numberHousingUnits: expect.any(Object),
            electricitySupplier: expect.any(Object),
            hydroQuebecContractNumber: expect.any(Object),
            hydroQuebecMeterNumber: expect.any(Object),
            ifSeveralMeters: expect.any(Object),
            specifyMeteIntended: expect.any(Object),
            hydroQuebecAccountNumber: expect.any(Object),
            fileNumber: expect.any(Object),
            typeBuillding: expect.any(Object),
            natureProject: expect.any(Object),
            startDateWork: expect.any(Object),
            endDateWork: expect.any(Object),
            hydroQuebecRate: expect.any(Object),
          }),
        );
      });
    });

    describe('getProject', () => {
      it('should return NewProject for default Project initial value', () => {
        const formGroup = service.createProjectFormGroup(sampleWithNewData);

        const project = service.getProject(formGroup) as any;

        expect(project).toMatchObject(sampleWithNewData);
      });

      it('should return NewProject for empty Project initial value', () => {
        const formGroup = service.createProjectFormGroup();

        const project = service.getProject(formGroup) as any;

        expect(project).toMatchObject({});
      });

      it('should return IProject', () => {
        const formGroup = service.createProjectFormGroup(sampleWithRequiredData);

        const project = service.getProject(formGroup) as any;

        expect(project).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IProject should not enable id FormControl', () => {
        const formGroup = service.createProjectFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewProject should disable id FormControl', () => {
        const formGroup = service.createProjectFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
