import { Component, Input } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IHydroQuebecRate } from '../hydro-quebec-rate.model';

@Component({
  standalone: true,
  selector: 'jhi-hydro-quebec-rate-detail',
  templateUrl: './hydro-quebec-rate-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class HydroQuebecRateDetailComponent {
  @Input() hydroQuebecRate: IHydroQuebecRate | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  previousState(): void {
    window.history.back();
  }
}
