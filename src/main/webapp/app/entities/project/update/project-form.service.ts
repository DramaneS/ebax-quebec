import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IProject, NewProject } from '../project.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IProject for edit and NewProjectFormGroupInput for create.
 */
type ProjectFormGroupInput = IProject | PartialWithRequiredKeyOf<NewProject>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IProject | NewProject> = Omit<T, 'startDateWork' | 'endDateWork'> & {
  startDateWork?: string | null;
  endDateWork?: string | null;
};

type ProjectFormRawValue = FormValueOf<IProject>;

type NewProjectFormRawValue = FormValueOf<NewProject>;

type ProjectFormDefaults = Pick<NewProject, 'id' | 'ifSeveralMeters' | 'startDateWork' | 'endDateWork'>;

type ProjectFormGroupContent = {
  id: FormControl<ProjectFormRawValue['id'] | NewProject['id']>;
  nameBuilding: FormControl<ProjectFormRawValue['nameBuilding']>;
  fullAddressBuilding: FormControl<ProjectFormRawValue['fullAddressBuilding']>;
  numberHousingUnits: FormControl<ProjectFormRawValue['numberHousingUnits']>;
  electricitySupplier: FormControl<ProjectFormRawValue['electricitySupplier']>;
  hydroQuebecContractNumber: FormControl<ProjectFormRawValue['hydroQuebecContractNumber']>;
  hydroQuebecMeterNumber: FormControl<ProjectFormRawValue['hydroQuebecMeterNumber']>;
  ifSeveralMeters: FormControl<ProjectFormRawValue['ifSeveralMeters']>;
  specifyMeteIntended: FormControl<ProjectFormRawValue['specifyMeteIntended']>;
  hydroQuebecAccountNumber: FormControl<ProjectFormRawValue['hydroQuebecAccountNumber']>;
  fileNumber: FormControl<ProjectFormRawValue['fileNumber']>;
  typeBuillding: FormControl<ProjectFormRawValue['typeBuillding']>;
  natureProject: FormControl<ProjectFormRawValue['natureProject']>;
  startDateWork: FormControl<ProjectFormRawValue['startDateWork']>;
  endDateWork: FormControl<ProjectFormRawValue['endDateWork']>;
  hydroQuebecRate: FormControl<ProjectFormRawValue['hydroQuebecRate']>;
};

export type ProjectFormGroup = FormGroup<ProjectFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class ProjectFormService {
  createProjectFormGroup(project: ProjectFormGroupInput = { id: null }): ProjectFormGroup {
    const projectRawValue = this.convertProjectToProjectRawValue({
      ...this.getFormDefaults(),
      ...project,
    });
    return new FormGroup<ProjectFormGroupContent>({
      id: new FormControl(
        { value: projectRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nameBuilding: new FormControl(projectRawValue.nameBuilding),
      fullAddressBuilding: new FormControl(projectRawValue.fullAddressBuilding),
      numberHousingUnits: new FormControl(projectRawValue.numberHousingUnits),
      electricitySupplier: new FormControl(projectRawValue.electricitySupplier),
      hydroQuebecContractNumber: new FormControl(projectRawValue.hydroQuebecContractNumber),
      hydroQuebecMeterNumber: new FormControl(projectRawValue.hydroQuebecMeterNumber),
      ifSeveralMeters: new FormControl(projectRawValue.ifSeveralMeters),
      specifyMeteIntended: new FormControl(projectRawValue.specifyMeteIntended),
      hydroQuebecAccountNumber: new FormControl(projectRawValue.hydroQuebecAccountNumber),
      fileNumber: new FormControl(projectRawValue.fileNumber),
      typeBuillding: new FormControl(projectRawValue.typeBuillding),
      natureProject: new FormControl(projectRawValue.natureProject),
      startDateWork: new FormControl(projectRawValue.startDateWork),
      endDateWork: new FormControl(projectRawValue.endDateWork),
      hydroQuebecRate: new FormControl(projectRawValue.hydroQuebecRate),
    });
  }

  getProject(form: ProjectFormGroup): IProject | NewProject {
    return this.convertProjectRawValueToProject(form.getRawValue() as ProjectFormRawValue | NewProjectFormRawValue);
  }

  resetForm(form: ProjectFormGroup, project: ProjectFormGroupInput): void {
    const projectRawValue = this.convertProjectToProjectRawValue({ ...this.getFormDefaults(), ...project });
    form.reset(
      {
        ...projectRawValue,
        id: { value: projectRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): ProjectFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      ifSeveralMeters: false,
      startDateWork: currentTime,
      endDateWork: currentTime,
    };
  }

  private convertProjectRawValueToProject(rawProject: ProjectFormRawValue | NewProjectFormRawValue): IProject | NewProject {
    return {
      ...rawProject,
      startDateWork: dayjs(rawProject.startDateWork, DATE_TIME_FORMAT),
      endDateWork: dayjs(rawProject.endDateWork, DATE_TIME_FORMAT),
    };
  }

  private convertProjectToProjectRawValue(
    project: IProject | (Partial<NewProject> & ProjectFormDefaults),
  ): ProjectFormRawValue | PartialWithRequiredKeyOf<NewProjectFormRawValue> {
    return {
      ...project,
      startDateWork: project.startDateWork ? project.startDateWork.format(DATE_TIME_FORMAT) : undefined,
      endDateWork: project.endDateWork ? project.endDateWork.format(DATE_TIME_FORMAT) : undefined,
    };
  }
}
