import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { HydroQuebecRateService } from '../service/hydro-quebec-rate.service';
import { IHydroQuebecRate } from '../hydro-quebec-rate.model';
import { HydroQuebecRateFormService } from './hydro-quebec-rate-form.service';

import { HydroQuebecRateUpdateComponent } from './hydro-quebec-rate-update.component';

describe('HydroQuebecRate Management Update Component', () => {
  let comp: HydroQuebecRateUpdateComponent;
  let fixture: ComponentFixture<HydroQuebecRateUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let hydroQuebecRateFormService: HydroQuebecRateFormService;
  let hydroQuebecRateService: HydroQuebecRateService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), HydroQuebecRateUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(HydroQuebecRateUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(HydroQuebecRateUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    hydroQuebecRateFormService = TestBed.inject(HydroQuebecRateFormService);
    hydroQuebecRateService = TestBed.inject(HydroQuebecRateService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const hydroQuebecRate: IHydroQuebecRate = { id: 456 };

      activatedRoute.data = of({ hydroQuebecRate });
      comp.ngOnInit();

      expect(comp.hydroQuebecRate).toEqual(hydroQuebecRate);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHydroQuebecRate>>();
      const hydroQuebecRate = { id: 123 };
      jest.spyOn(hydroQuebecRateFormService, 'getHydroQuebecRate').mockReturnValue(hydroQuebecRate);
      jest.spyOn(hydroQuebecRateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hydroQuebecRate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: hydroQuebecRate }));
      saveSubject.complete();

      // THEN
      expect(hydroQuebecRateFormService.getHydroQuebecRate).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(hydroQuebecRateService.update).toHaveBeenCalledWith(expect.objectContaining(hydroQuebecRate));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHydroQuebecRate>>();
      const hydroQuebecRate = { id: 123 };
      jest.spyOn(hydroQuebecRateFormService, 'getHydroQuebecRate').mockReturnValue({ id: null });
      jest.spyOn(hydroQuebecRateService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hydroQuebecRate: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: hydroQuebecRate }));
      saveSubject.complete();

      // THEN
      expect(hydroQuebecRateFormService.getHydroQuebecRate).toHaveBeenCalled();
      expect(hydroQuebecRateService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHydroQuebecRate>>();
      const hydroQuebecRate = { id: 123 };
      jest.spyOn(hydroQuebecRateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hydroQuebecRate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(hydroQuebecRateService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
