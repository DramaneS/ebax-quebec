import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { CustumerDetailComponent } from './custumer-detail.component';

describe('Custumer Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CustumerDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: CustumerDetailComponent,
              resolve: { custumer: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(CustumerDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load custumer on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', CustumerDetailComponent);

      // THEN
      expect(instance.custumer).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
