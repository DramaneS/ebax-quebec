export interface IHydroQuebecRate {
  id: number;
  name?: string | null;
}

export type NewHydroQuebecRate = Omit<IHydroQuebecRate, 'id'> & { id: null };
