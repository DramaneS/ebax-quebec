import { ICustumer, NewCustumer } from './custumer.model';

export const sampleWithRequiredData: ICustumer = {
  id: 30525,
};

export const sampleWithPartialData: ICustumer = {
  id: 21216,
  nameCustumer: 'racheter membre titulaire de par',
  phone: '0324048416',
  email: 'Eudes31@yahoo.fr',
};

export const sampleWithFullData: ICustumer = {
  id: 9131,
  nameCustumer: 'corps enseignant conseil municipal',
  adress: 'neutre de peur que de peur que',
  phone: '+33 703611628',
  email: 'Mence.Faure@gmail.com',
};

export const sampleWithNewData: NewCustumer = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
