import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import SharedModule from 'app/shared/shared.module';
import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
import { ICustumer } from '../custumer.model';
import { CustumerService } from '../service/custumer.service';

@Component({
  standalone: true,
  templateUrl: './custumer-delete-dialog.component.html',
  imports: [SharedModule, FormsModule],
})
export class CustumerDeleteDialogComponent {
  custumer?: ICustumer;

  constructor(
    protected custumerService: CustumerService,
    protected activeModal: NgbActiveModal,
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.custumerService.delete(id).subscribe(() => {
      this.activeModal.close(ITEM_DELETED_EVENT);
    });
  }
}
