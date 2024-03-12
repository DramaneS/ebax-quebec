import { IHydroQuebecRate, NewHydroQuebecRate } from './hydro-quebec-rate.model';

export const sampleWithRequiredData: IHydroQuebecRate = {
  id: 25804,
};

export const sampleWithPartialData: IHydroQuebecRate = {
  id: 3788,
  name: 'cependant',
};

export const sampleWithFullData: IHydroQuebecRate = {
  id: 8104,
  name: 'suffisamment touriste',
};

export const sampleWithNewData: NewHydroQuebecRate = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
