import { IInvoice, NewInvoice } from './invoice.model';

export const sampleWithRequiredData: IInvoice = {
  id: 14900,
};

export const sampleWithPartialData: IInvoice = {
  id: 9120,
  phone: '0604389795',
};

export const sampleWithFullData: IInvoice = {
  id: 8257,
  nameCustumer: 'outre',
  adress: 'renvoyer',
  phone: '+33 767543254',
  email: 'Vianney2@yahoo.fr',
};

export const sampleWithNewData: NewInvoice = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
