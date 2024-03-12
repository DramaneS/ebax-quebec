import dayjs from 'dayjs/esm';
import { IHydroQuebecRate } from 'app/entities/hydro-quebec-rate/hydro-quebec-rate.model';
import { IService } from 'app/entities/service/service.model';

export interface IProject {
  id: number;
  nameBuilding?: string | null;
  fullAddressBuilding?: string | null;
  numberHousingUnits?: string | null;
  electricitySupplier?: string | null;
  hydroQuebecContractNumber?: string | null;
  hydroQuebecMeterNumber?: string | null;
  ifSeveralMeters?: boolean | null;
  specifyMeteIntended?: string | null;
  hydroQuebecAccountNumber?: string | null;
  fileNumber?: string | null;
  typeBuillding?: string | null;
  natureProject?: string | null;
  startDateWork?: dayjs.Dayjs | null;
  endDateWork?: dayjs.Dayjs | null;
  hydroQuebecRate?: IHydroQuebecRate | null;
  services?: IService[] | null;
}

export type NewProject = Omit<IProject, 'id'> & { id: null };
