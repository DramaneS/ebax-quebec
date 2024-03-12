import { IEntreprise, NewEntreprise } from './entreprise.model';

export const sampleWithRequiredData: IEntreprise = {
  id: 11587,
};

export const sampleWithPartialData: IEntreprise = {
  id: 20795,
  resourcePerson: 'auprès de',
  address: 'ha ha triangulaire',
};

export const sampleWithFullData: IEntreprise = {
  id: 26234,
  companyName: 'redescendre tchou tchouu avant de',
  companyNeqNumber: 'toc-toc',
  isThistaxExemptOrganization: true,
  resourcePerson: "aujourd'hui par rapport à deçà",
  address: 'ouille sitôt que de par',
  email: 'Leonne86@hotmail.fr',
  phone: '0567226934',
};

export const sampleWithNewData: NewEntreprise = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
