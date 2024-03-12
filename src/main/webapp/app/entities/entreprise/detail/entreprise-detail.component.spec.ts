import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { EntrepriseDetailComponent } from './entreprise-detail.component';

describe('Entreprise Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EntrepriseDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: EntrepriseDetailComponent,
              resolve: { entreprise: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(EntrepriseDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load entreprise on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', EntrepriseDetailComponent);

      // THEN
      expect(instance.entreprise).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
