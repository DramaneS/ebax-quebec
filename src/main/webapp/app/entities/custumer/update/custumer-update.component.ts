import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IService } from 'app/entities/service/service.model';
import { ServiceService } from 'app/entities/service/service/service.service';
import { ICustumer } from '../custumer.model';
import { CustumerService } from '../service/custumer.service';
import { CustumerFormService, CustumerFormGroup } from './custumer-form.service';

@Component({
  standalone: true,
  selector: 'jhi-custumer-update',
  templateUrl: './custumer-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class CustumerUpdateComponent implements OnInit {
  isSaving = false;
  custumer: ICustumer | null = null;

  servicesSharedCollection: IService[] = [];

  editForm: CustumerFormGroup = this.custumerFormService.createCustumerFormGroup();

  constructor(
    protected custumerService: CustumerService,
    protected custumerFormService: CustumerFormService,
    protected serviceService: ServiceService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  compareService = (o1: IService | null, o2: IService | null): boolean => this.serviceService.compareService(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ custumer }) => {
      this.custumer = custumer;
      if (custumer) {
        this.updateForm(custumer);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const custumer = this.custumerFormService.getCustumer(this.editForm);
    if (custumer.id !== null) {
      this.subscribeToSaveResponse(this.custumerService.update(custumer));
    } else {
      this.subscribeToSaveResponse(this.custumerService.create(custumer));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICustumer>>): void {
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

  protected updateForm(custumer: ICustumer): void {
    this.custumer = custumer;
    this.custumerFormService.resetForm(this.editForm, custumer);

    this.servicesSharedCollection = this.serviceService.addServiceToCollectionIfMissing<IService>(
      this.servicesSharedCollection,
      custumer.service,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.serviceService
      .query()
      .pipe(map((res: HttpResponse<IService[]>) => res.body ?? []))
      .pipe(map((services: IService[]) => this.serviceService.addServiceToCollectionIfMissing<IService>(services, this.custumer?.service)))
      .subscribe((services: IService[]) => (this.servicesSharedCollection = services));
  }
}
