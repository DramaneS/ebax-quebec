import { IService } from 'app/entities/service/service.model';

export interface ICustumer {
  id: number;
  nameCustumer?: string | null;
  adress?: string | null;
  phone?: string | null;
  email?: string | null;
  service?: IService | null;
}

export type NewCustumer = Omit<ICustumer, 'id'> & { id: null };
