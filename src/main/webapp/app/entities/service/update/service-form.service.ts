import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IService, NewService } from '../service.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IService for edit and NewServiceFormGroupInput for create.
 */
type ServiceFormGroupInput = IService | PartialWithRequiredKeyOf<NewService>;

type ServiceFormDefaults = Pick<
  NewService,
  | 'id'
  | 'plansAllFloorsVentilationPlans'
  | 'energySimulationReport'
  | 'windowsTechnicalSheetAndUFactor'
  | 'completeWallSection'
  | 'brandModelVentilationDevices'
  | 'brandModelVeaters'
  | 'brandModelHotWaterTanks'
  | 'brandModelHeatPumpAirConditioningUnits'
  | 'typeLighting'
  | 'quantityEachModelAndDLCProductID'
  | 'checksProvideTechnicalDataSheets'
  | 'brandModelPlumbingFixtures'
  | 'otherRelevantDevicesCertification'
  | 'heatRecoveryGrayWaterSolarOther'
  | 'projects'
>;

type ServiceFormGroupContent = {
  id: FormControl<IService['id'] | NewService['id']>;
  nameService: FormControl<IService['nameService']>;
  companyName: FormControl<IService['companyName']>;
  phone: FormControl<IService['phone']>;
  email: FormControl<IService['email']>;
  plansAllFloorsVentilationPlans: FormControl<IService['plansAllFloorsVentilationPlans']>;
  energySimulationReport: FormControl<IService['energySimulationReport']>;
  windowsTechnicalSheetAndUFactor: FormControl<IService['windowsTechnicalSheetAndUFactor']>;
  completeWallSection: FormControl<IService['completeWallSection']>;
  brandModelVentilationDevices: FormControl<IService['brandModelVentilationDevices']>;
  brandModelVeaters: FormControl<IService['brandModelVeaters']>;
  brandModelHotWaterTanks: FormControl<IService['brandModelHotWaterTanks']>;
  brandModelHeatPumpAirConditioningUnits: FormControl<IService['brandModelHeatPumpAirConditioningUnits']>;
  typeLighting: FormControl<IService['typeLighting']>;
  quantityEachModelAndDLCProductID: FormControl<IService['quantityEachModelAndDLCProductID']>;
  checksProvideTechnicalDataSheets: FormControl<IService['checksProvideTechnicalDataSheets']>;
  brandModelPlumbingFixtures: FormControl<IService['brandModelPlumbingFixtures']>;
  otherRelevantDevicesCertification: FormControl<IService['otherRelevantDevicesCertification']>;
  heatRecoveryGrayWaterSolarOther: FormControl<IService['heatRecoveryGrayWaterSolarOther']>;
  entreprise: FormControl<IService['entreprise']>;
  projects: FormControl<IService['projects']>;
};

export type ServiceFormGroup = FormGroup<ServiceFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ServiceFormService {
  createServiceFormGroup(service: ServiceFormGroupInput = { id: null }): ServiceFormGroup {
    const serviceRawValue = {
      ...this.getFormDefaults(),
      ...service,
    };
    return new FormGroup<ServiceFormGroupContent>({
      id: new FormControl(
        { value: serviceRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nameService: new FormControl(serviceRawValue.nameService),
      companyName: new FormControl(serviceRawValue.companyName),
      phone: new FormControl(serviceRawValue.phone),
      email: new FormControl(serviceRawValue.email),
      plansAllFloorsVentilationPlans: new FormControl(serviceRawValue.plansAllFloorsVentilationPlans),
      energySimulationReport: new FormControl(serviceRawValue.energySimulationReport),
      windowsTechnicalSheetAndUFactor: new FormControl(serviceRawValue.windowsTechnicalSheetAndUFactor),
      completeWallSection: new FormControl(serviceRawValue.completeWallSection),
      brandModelVentilationDevices: new FormControl(serviceRawValue.brandModelVentilationDevices),
      brandModelVeaters: new FormControl(serviceRawValue.brandModelVeaters),
      brandModelHotWaterTanks: new FormControl(serviceRawValue.brandModelHotWaterTanks),
      brandModelHeatPumpAirConditioningUnits: new FormControl(serviceRawValue.brandModelHeatPumpAirConditioningUnits),
      typeLighting: new FormControl(serviceRawValue.typeLighting),
      quantityEachModelAndDLCProductID: new FormControl(serviceRawValue.quantityEachModelAndDLCProductID),
      checksProvideTechnicalDataSheets: new FormControl(serviceRawValue.checksProvideTechnicalDataSheets),
      brandModelPlumbingFixtures: new FormControl(serviceRawValue.brandModelPlumbingFixtures),
      otherRelevantDevicesCertification: new FormControl(serviceRawValue.otherRelevantDevicesCertification),
      heatRecoveryGrayWaterSolarOther: new FormControl(serviceRawValue.heatRecoveryGrayWaterSolarOther),
      entreprise: new FormControl(serviceRawValue.entreprise),
      projects: new FormControl(serviceRawValue.projects ?? []),
    });
  }

  getService(form: ServiceFormGroup): IService | NewService {
    return form.getRawValue() as IService | NewService;
  }

  resetForm(form: ServiceFormGroup, service: ServiceFormGroupInput): void {
    const serviceRawValue = { ...this.getFormDefaults(), ...service };
    form.reset(
      {
        ...serviceRawValue,
        id: { value: serviceRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ServiceFormDefaults {
    return {
      id: null,
      plansAllFloorsVentilationPlans: false,
      energySimulationReport: false,
      windowsTechnicalSheetAndUFactor: false,
      completeWallSection: false,
      brandModelVentilationDevices: false,
      brandModelVeaters: false,
      brandModelHotWaterTanks: false,
      brandModelHeatPumpAirConditioningUnits: false,
      typeLighting: false,
      quantityEachModelAndDLCProductID: false,
      checksProvideTechnicalDataSheets: false,
      brandModelPlumbingFixtures: false,
      otherRelevantDevicesCertification: false,
      heatRecoveryGrayWaterSolarOther: false,
      projects: [],
    };
  }
}
