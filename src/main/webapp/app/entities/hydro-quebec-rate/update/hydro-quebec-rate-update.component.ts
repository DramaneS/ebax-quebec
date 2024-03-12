import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IHydroQuebecRate } from '../hydro-quebec-rate.model';
import { HydroQuebecRateService } from '../service/hydro-quebec-rate.service';
import { HydroQuebecRateFormService, HydroQuebecRateFormGroup } from './hydro-quebec-rate-form.service';

@Component({
  standalone: true,
  selector: 'jhi-hydro-quebec-rate-update',
  templateUrl: './hydro-quebec-rate-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class HydroQuebecRateUpdateComponent implements OnInit {
  isSaving = false;
  hydroQuebecRate: IHydroQuebecRate | null = null;

  editForm: HydroQuebecRateFormGroup = this.hydroQuebecRateFormService.createHydroQuebecRateFormGroup();

  constructor(
    protected hydroQuebecRateService: HydroQuebecRateService,
    protected hydroQuebecRateFormService: HydroQuebecRateFormService,
    protected activatedRoute: ActivatedRoute,
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hydroQuebecRate }) => {
      this.hydroQuebecRate = hydroQuebecRate;
      if (hydroQuebecRate) {
        this.updateForm(hydroQuebecRate);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const hydroQuebecRate = this.hydroQuebecRateFormService.getHydroQuebecRate(this.editForm);
    if (hydroQuebecRate.id !== null) {
      this.subscribeToSaveResponse(this.hydroQuebecRateService.update(hydroQuebecRate));
    } else {
      this.subscribeToSaveResponse(this.hydroQuebecRateService.create(hydroQuebecRate));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHydroQuebecRate>>): void {
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

  protected updateForm(hydroQuebecRate: IHydroQuebecRate): void {
    this.hydroQuebecRate = hydroQuebecRate;
    this.hydroQuebecRateFormService.resetForm(this.editForm, hydroQuebecRate);
  }
}
