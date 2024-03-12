import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEntreprise, NewEntreprise } from '../entreprise.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEntreprise for edit and NewEntrepriseFormGroupInput for create.
 */
type EntrepriseFormGroupInput = IEntreprise | PartialWithRequiredKeyOf<NewEntreprise>;

type EntrepriseFormDefaults = Pick<NewEntreprise, 'id' | 'isThistaxExemptOrganization'>;

type EntrepriseFormGroupContent = {
  id: FormControl<IEntreprise['id'] | NewEntreprise['id']>;
  companyName: FormControl<IEntreprise['companyName']>;
  companyNeqNumber: FormControl<IEntreprise['companyNeqNumber']>;
  isThistaxExemptOrganization: FormControl<IEntreprise['isThistaxExemptOrganization']>;
  resourcePerson: FormControl<IEntreprise['resourcePerson']>;
  address: FormControl<IEntreprise['address']>;
  email: FormControl<IEntreprise['email']>;
  phone: FormControl<IEntreprise['phone']>;
};

export type EntrepriseFormGroup = FormGroup<EntrepriseFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EntrepriseFormService {
  createEntrepriseFormGroup(entreprise: EntrepriseFormGroupInput = { id: null }): EntrepriseFormGroup {
    const entrepriseRawValue = {
      ...this.getFormDefaults(),
      ...entreprise,
    };
    return new FormGroup<EntrepriseFormGroupContent>({
      id: new FormControl(
        { value: entrepriseRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      companyName: new FormControl(entrepriseRawValue.companyName),
      companyNeqNumber: new FormControl(entrepriseRawValue.companyNeqNumber),
      isThistaxExemptOrganization: new FormControl(entrepriseRawValue.isThistaxExemptOrganization),
      resourcePerson: new FormControl(entrepriseRawValue.resourcePerson),
      address: new FormControl(entrepriseRawValue.address),
      email: new FormControl(entrepriseRawValue.email),
      phone: new FormControl(entrepriseRawValue.phone),
    });
  }

  getEntreprise(form: EntrepriseFormGroup): IEntreprise | NewEntreprise {
    return form.getRawValue() as IEntreprise | NewEntreprise;
  }

  resetForm(form: EntrepriseFormGroup, entreprise: EntrepriseFormGroupInput): void {
    const entrepriseRawValue = { ...this.getFormDefaults(), ...entreprise };
    form.reset(
      {
        ...entrepriseRawValue,
        id: { value: entrepriseRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EntrepriseFormDefaults {
    return {
      id: null,
      isThistaxExemptOrganization: false,
    };
  }
}
