import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IHydroQuebecRate } from 'app/entities/hydro-quebec-rate/hydro-quebec-rate.model';
import { HydroQuebecRateService } from 'app/entities/hydro-quebec-rate/service/hydro-quebec-rate.service';
import { IProject } from '../project.model';
import { ProjectService } from '../service/project.service';
import { ProjectFormService, ProjectFormGroup } from './project-form.service';

@Component({
  standalone: true,
  selector: 'jhi-project-update',
  templateUrl: './project-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ProjectUpdateComponent implements OnInit {
  isSaving = false;
  project: IProject | null = null;

  hydroQuebecRatesSharedCollection: IHydroQuebecRate[] = [];

  editForm: ProjectFormGroup = this.projectFormService.createProjectFormGroup();

  constructor(
    protected projectService: ProjectService,
    protected projectFormService: ProjectFormService,
    protected hydroQuebecRateService: HydroQuebecRateService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareHydroQuebecRate = (o1: IHydroQuebecRate | null, o2: IHydroQuebecRate | null): boolean =>
    this.hydroQuebecRateService.compareHydroQuebecRate(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ project }) => {
      this.project = project;
      if (project) {
        this.updateForm(project);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const project = this.projectFormService.getProject(this.editForm);
    if (project.id !== null) {
      this.subscribeToSaveResponse(this.projectService.update(project));
    } else {
      this.subscribeToSaveResponse(this.projectService.create(project));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProject>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(project: IProject): void {
    this.project = project;
    this.projectFormService.resetForm(this.editForm, project);

    this.hydroQuebecRatesSharedCollection = this.hydroQuebecRateService.addHydroQuebecRateToCollectionIfMissing<IHydroQuebecRate>(
      this.hydroQuebecRatesSharedCollection,
      project.hydroQuebecRate,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.hydroQuebecRateService
      .query()
      .pipe(map((res: HttpResponse<IHydroQuebecRate[]>) => res.body ?? []))
      .pipe(
        map((hydroQuebecRates: IHydroQuebecRate[]) =>
          this.hydroQuebecRateService.addHydroQuebecRateToCollectionIfMissing<IHydroQuebecRate>(
            hydroQuebecRates,
            this.project?.hydroQuebecRate,
          ),
        ),
      )
      .subscribe((hydroQuebecRates: IHydroQuebecRate[]) => (this.hydroQuebecRatesSharedCollection = hydroQuebecRates));
  }
}
