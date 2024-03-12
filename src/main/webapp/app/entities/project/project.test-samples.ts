import dayjs from 'dayjs/esm';

import { IProject, NewProject } from './project.model';

export const sampleWithRequiredData: IProject = {
  id: 16424,
};

export const sampleWithPartialData: IProject = {
  id: 32620,
  nameBuilding: 'broum',
  numberHousingUnits: 'sauvegarder',
  electricitySupplier: 'absolument crac',
  hydroQuebecMeterNumber: 'moyennant agréable au-dedans de',
  typeBuillding: 'secouriste',
  startDateWork: dayjs('2024-03-12T14:07'),
  endDateWork: dayjs('2024-03-12T04:55'),
};

export const sampleWithFullData: IProject = {
  id: 1596,
  nameBuilding: 'étant donné que',
  fullAddressBuilding: 'retourner',
  numberHousingUnits: 'insolite à la merci',
  electricitySupplier: 'timide',
  hydroQuebecContractNumber: 'coac coac assez',
  hydroQuebecMeterNumber: 'vlan comprendre',
  ifSeveralMeters: false,
  specifyMeteIntended: 'plutôt',
  hydroQuebecAccountNumber: 'jeune enfant',
  fileNumber: 'quand ? du moment que',
  typeBuillding: 'totalement trop peu membre à vie',
  natureProject: 'que alors que',
  startDateWork: dayjs('2024-03-11T19:25'),
  endDateWork: dayjs('2024-03-12T09:27'),
};

export const sampleWithNewData: NewProject = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
