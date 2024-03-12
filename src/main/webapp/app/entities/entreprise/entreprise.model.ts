export interface IEntreprise {
  id: number;
  companyName?: string | null;
  companyNeqNumber?: string | null;
  isThistaxExemptOrganization?: boolean | null;
  resourcePerson?: string | null;
  address?: string | null;
  email?: string | null;
  phone?: string | null;
}

export type NewEntreprise = Omit<IEntreprise, 'id'> & { id: null };
