import { IService, NewService } from './service.model';

export const sampleWithRequiredData: IService = {
  id: 6281,
};

export const sampleWithPartialData: IService = {
  id: 24215,
  email: 'Irene_Lefevre11@yahoo.fr',
  energySimulationReport: false,
  windowsTechnicalSheetAndUFactor: false,
  completeWallSection: true,
  brandModelVentilationDevices: false,
  brandModelHeatPumpAirConditioningUnits: false,
  typeLighting: true,
  quantityEachModelAndDLCProductID: false,
  brandModelPlumbingFixtures: true,
  otherRelevantDevicesCertification: false,
};

export const sampleWithFullData: IService = {
  id: 5312,
  nameService: "diplomate d'entre",
  companyName: 'prout retracer',
  phone: '+33 720634505',
  email: 'Sibylle_Riviere28@hotmail.fr',
  plansAllFloorsVentilationPlans: true,
  energySimulationReport: false,
  windowsTechnicalSheetAndUFactor: false,
  completeWallSection: true,
  brandModelVentilationDevices: true,
  brandModelVeaters: true,
  brandModelHotWaterTanks: true,
  brandModelHeatPumpAirConditioningUnits: true,
  typeLighting: false,
  quantityEachModelAndDLCProductID: true,
  checksProvideTechnicalDataSheets: false,
  brandModelPlumbingFixtures: true,
  otherRelevantDevicesCertification: false,
  heatRecoveryGrayWaterSolarOther: false,
};

export const sampleWithNewData: NewService = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
