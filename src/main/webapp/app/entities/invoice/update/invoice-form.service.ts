import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IInvoice, NewInvoice } from '../invoice.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IInvoice for edit and NewInvoiceFormGroupInput for create.
 */
type InvoiceFormGroupInput = IInvoice | PartialWithRequiredKeyOf<NewInvoice>;

type InvoiceFormDefaults = Pick<NewInvoice, 'id'>;

type InvoiceFormGroupContent = {
  id: FormControl<IInvoice['id'] | NewInvoice['id']>;
  nameCustumer: FormControl<IInvoice['nameCustumer']>;
  adress: FormControl<IInvoice['adress']>;
  phone: FormControl<IInvoice['phone']>;
  email: FormControl<IInvoice['email']>;
  service: FormControl<IInvoice['service']>;
};

export type InvoiceFormGroup = FormGroup<InvoiceFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class InvoiceFormService {
  createInvoiceFormGroup(invoice: InvoiceFormGroupInput = { id: null }): InvoiceFormGroup {
    const invoiceRawValue = {
      ...this.getFormDefaults(),
      ...invoice,
    };
    return new FormGroup<InvoiceFormGroupContent>({
      id: new FormControl(
        { value: invoiceRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nameCustumer: new FormControl(invoiceRawValue.nameCustumer),
      adress: new FormControl(invoiceRawValue.adress),
      phone: new FormControl(invoiceRawValue.phone),
      email: new FormControl(invoiceRawValue.email),
      service: new FormControl(invoiceRawValue.service),
    });
  }

  getInvoice(form: InvoiceFormGroup): IInvoice | NewInvoice {
    return form.getRawValue() as IInvoice | NewInvoice;
  }

  resetForm(form: InvoiceFormGroup, invoice: InvoiceFormGroupInput): void {
    const invoiceRawValue = { ...this.getFormDefaults(), ...invoice };
    form.reset(
      {
        ...invoiceRawValue,
        id: { value: invoiceRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): InvoiceFormDefaults {
    return {
      id: null,
    };
  }
}
