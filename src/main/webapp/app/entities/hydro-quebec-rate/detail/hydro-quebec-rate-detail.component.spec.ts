import { TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness, RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs';

import { HydroQuebecRateDetailComponent } from './hydro-quebec-rate-detail.component';

describe('HydroQuebecRate Management Detail Component', () => {
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HydroQuebecRateDetailComponent, RouterTestingModule.withRoutes([], { bindToComponentInputs: true })],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              component: HydroQuebecRateDetailComponent,
              resolve: { hydroQuebecRate: () => of({ id: 123 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(HydroQuebecRateDetailComponent, '')
      .compileComponents();
  });

  describe('OnInit', () => {
    it('Should load hydroQuebecRate on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', HydroQuebecRateDetailComponent);

      // THEN
      expect(instance.hydroQuebecRate).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
