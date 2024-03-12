import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEntreprise } from 'app/entities/entreprise/entreprise.model';
import { EntrepriseService } from 'app/entities/entreprise/service/entreprise.service';
import { IProject } from 'app/entities/project/project.model';
import { ProjectService } from 'app/entities/project/service/project.service';
import { ServiceService } from '../service/service.service';
import { IService } from '../service.model';
import { ServiceFormService, ServiceFormGroup } from './service-form.service';

@Component({
  standalone: true,
  selector: 'jhi-service-update',
  templateUrl: './service-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class ServiceUpdateComponent implements OnInit {
  isSaving = false;
  service: IService | null = null;

  entreprisesSharedCollection: IEntreprise[] = [];
  projectsSharedCollection: IProject[] = [];

  editForm: ServiceFormGroup = this.serviceFormService.createServiceFormGroup();

  constructor(
    protected serviceService: ServiceService,
    protected serviceFormService: ServiceFormService,
    protected entrepriseService: EntrepriseService,
    protected projectService: ProjectService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareEntreprise = (o1: IEntreprise | null, o2: IEntreprise | null): boolean => this.entrepriseService.compareEntreprise(o1, o2);

  compareProject = (o1: IProject | null, o2: IProject | null): boolean => this.projectService.compareProject(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ service }) => {
      this.service = service;
      if (service) {
        this.updateForm(service);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const service = this.serviceFormService.getService(this.editForm);
    if (service.id !== null) {
      this.subscribeToSaveResponse(this.serviceService.update(service));
    } else {
      this.subscribeToSaveResponse(this.serviceService.create(service));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IService>>): void {
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

  protected updateForm(service: IService): void {
    this.service = service;
    this.serviceFormService.resetForm(this.editForm, service);

    this.entreprisesSharedCollection = this.entrepriseService.addEntrepriseToCollectionIfMissing<IEntreprise>(
      this.entreprisesSharedCollection,
      service.entreprise,
    );
    this.projectsSharedCollection = this.projectService.addProjectToCollectionIfMissing<IProject>(
      this.projectsSharedCollection,
      ...(service.projects ?? []),
    );
  }

  protected loadRelationshipsOptions(): void {
    this.entrepriseService
      .query()
      .pipe(map((res: HttpResponse<IEntreprise[]>) => res.body ?? []))
      .pipe(
        map((entreprises: IEntreprise[]) =>
          this.entrepriseService.addEntrepriseToCollectionIfMissing<IEntreprise>(entreprises, this.service?.entreprise),
        ),
      )
      .subscribe((entreprises: IEntreprise[]) => (this.entreprisesSharedCollection = entreprises));

    this.projectService
      .query()
      .pipe(map((res: HttpResponse<IProject[]>) => res.body ?? []))
      .pipe(
        map((projects: IProject[]) =>
          this.projectService.addProjectToCollectionIfMissing<IProject>(projects, ...(this.service?.projects ?? [])),
        ),
      )
      .subscribe((projects: IProject[]) => (this.projectsSharedCollection = projects));
  }
}
