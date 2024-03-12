import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IHydroQuebecRate } from '../hydro-quebec-rate.model';
import { HydroQuebecRateService } from '../service/hydro-quebec-rate.service';

@Component({
  standalone: true,
  templateUrl: './hydro-quebec-rate-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class HydroQuebecRateDeleteDialogComponent {
  hydroQuebecRate?: IHydroQuebecRate;

  constructor(
    protected hydroQuebecRateService: HydroQuebecRateService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.hydroQuebecRateService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
