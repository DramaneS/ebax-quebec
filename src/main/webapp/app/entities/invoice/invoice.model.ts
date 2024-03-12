import { IService } from 'app/entities/service/service.model';

export interface IInvoice {
  id: number;
  nameCustumer?: string | null;
  adress?: string | null;
  phone?: string | null;
  email?: string | null;
  service?: IService | null;
}

export type NewInvoice = Omit<IInvoice, 'id'> & { id: null };
