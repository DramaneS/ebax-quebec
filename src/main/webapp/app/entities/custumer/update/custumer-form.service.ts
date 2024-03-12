import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { ICustumer, NewCustumer } from '../custumer.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICustumer for edit and NewCustumerFormGroupInput for create.
 */
type CustumerFormGroupInput = ICustumer | PartialWithRequiredKeyOf<NewCustumer>;

type CustumerFormDefaults = Pick<NewCustumer, 'id'>;

type CustumerFormGroupContent = {
  id: FormControl<ICustumer['id'] | NewCustumer['id']>;
  nameCustumer: FormControl<ICustumer['nameCustumer']>;
  adress: FormControl<ICustumer['adress']>;
  phone: FormControl<ICustumer['phone']>;
  email: FormControl<ICustumer['email']>;
  service: FormControl<ICustumer['service']>;
};

export type CustumerFormGroup = FormGroup<CustumerFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CustumerFormService {
  createCustumerFormGroup(custumer: CustumerFormGroupInput = { id: null }): CustumerFormGroup {
    const custumerRawValue = {
      ...this.getFormDefaults(),
      ...custumer,
    };
    return new FormGroup<CustumerFormGroupContent>({
      id: new FormControl(
        { value: custumerRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nameCustumer: new FormControl(custumerRawValue.nameCustumer),
      adress: new FormControl(custumerRawValue.adress),
      phone: new FormControl(custumerRawValue.phone),
      email: new FormControl(custumerRawValue.email),
      service: new FormControl(custumerRawValue.service),
    });
  }

  getCustumer(form: CustumerFormGroup): ICustumer | NewCustumer {
    return form.getRawValue() as ICustumer | NewCustumer;
  }

  resetForm(form: CustumerFormGroup, custumer: CustumerFormGroupInput): void {
    const custumerRawValue = { ...this.getFormDefaults(), ...custumer };
    form.reset(
      {
        ...custumerRawValue,
        id: { value: custumerRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CustumerFormDefaults {
    return {
      id: null,
    };
  }
}
