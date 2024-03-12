import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IHydroQuebecRate, NewHydroQuebecRate } from '../hydro-quebec-rate.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IHydroQuebecRate for edit and NewHydroQuebecRateFormGroupInput for create.
 */
type HydroQuebecRateFormGroupInput = IHydroQuebecRate | PartialWithRequiredKeyOf<NewHydroQuebecRate>;

type HydroQuebecRateFormDefaults = Pick<NewHydroQuebecRate, 'id'>;

type HydroQuebecRateFormGroupContent = {
  id: FormControl<IHydroQuebecRate['id'] | NewHydroQuebecRate['id']>;
  name: FormControl<IHydroQuebecRate['name']>;
};

export type HydroQuebecRateFormGroup = FormGroup<HydroQuebecRateFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class HydroQuebecRateFormService {
  createHydroQuebecRateFormGroup(hydroQuebecRate: HydroQuebecRateFormGroupInput = { id: null }): HydroQuebecRateFormGroup {
    const hydroQuebecRateRawValue = {
      ...this.getFormDefaults(),
      ...hydroQuebecRate,
    };
    return new FormGroup<HydroQuebecRateFormGroupContent>({
      id: new FormControl(
        { value: hydroQuebecRateRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      name: new FormControl(hydroQuebecRateRawValue.name),
    });
  }

  getHydroQuebecRate(form: HydroQuebecRateFormGroup): IHydroQuebecRate | NewHydroQuebecRate {
    return form.getRawValue() as IHydroQuebecRate | NewHydroQuebecRate;
  }

  resetForm(form: HydroQuebecRateFormGroup, hydroQuebecRate: HydroQuebecRateFormGroupInput): void {
    const hydroQuebecRateRawValue = { ...this.getFormDefaults(), ...hydroQuebecRate };
    form.reset(
      {
        ...hydroQuebecRateRawValue,
        id: { value: hydroQuebecRateRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): HydroQuebecRateFormDefaults {
    return {
      id: null,
    };
  }
}
