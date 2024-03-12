import { IEntreprise } from 'app/entities/entreprise/entreprise.model';
import { IProject } from 'app/entities/project/project.model';
import { IInvoice } from 'app/entities/invoice/invoice.model';

export interface IService {
  id: number;
  nameService?: string | null;
  companyName?: string | null;
  phone?: string | null;
  email?: string | null;
  plansAllFloorsVentilationPlans?: boolean | null;
  energySimulationReport?: boolean | null;
  windowsTechnicalSheetAndUFactor?: boolean | null;
  completeWallSection?: boolean | null;
  brandModelVentilationDevices?: boolean | null;
  brandModelVeaters?: boolean | null;
  brandModelHotWaterTanks?: boolean | null;
  brandModelHeatPumpAirConditioningUnits?: boolean | null;
  typeLighting?: boolean | null;
  quantityEachModelAndDLCProductID?: boolean | null;
  checksProvideTechnicalDataSheets?: boolean | null;
  brandModelPlumbingFixtures?: boolean | null;
  otherRelevantDevicesCertification?: boolean | null;
  heatRecoveryGrayWaterSolarOther?: boolean | null;
  entreprise?: IEntreprise | null;
  projects?: IProject[] | null;
  invoice?: IInvoice | null;
}

export type NewService = Omit<IService, 'id'> & { id: null };
