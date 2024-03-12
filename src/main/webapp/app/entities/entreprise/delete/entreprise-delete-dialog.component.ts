import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { IEntreprise } from '../entreprise.model';
import { EntrepriseService } from '../service/entreprise.service';

@Component({
  standalone: true,
  templateUrl: './entreprise-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class EntrepriseDeleteDialogComponent {
  entreprise?: IEntreprise;

  constructor(
    protected entrepriseService: EntrepriseService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.entrepriseService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
