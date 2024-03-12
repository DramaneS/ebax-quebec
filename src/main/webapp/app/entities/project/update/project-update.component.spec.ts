import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IHydroQuebecRate } from 'app/entities/hydro-quebec-rate/hydro-quebec-rate.model';
import { HydroQuebecRateService } from 'app/entities/hydro-quebec-rate/service/hydro-quebec-rate.service';
import { ProjectService } from '../service/project.service';
import { IProject } from '../project.model';
import { ProjectFormService } from './project-form.service';

import { ProjectUpdateComponent } from './project-update.component';

describe('Project Management Update Component', () => {
  let comp: ProjectUpdateComponent;
  let fixture: ComponentFixture<ProjectUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let projectFormService: ProjectFormService;
  let projectService: ProjectService;
  let hydroQuebecRateService: HydroQuebecRateService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), ProjectUpdateComponent],
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
      .overrideTemplate(ProjectUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ProjectUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    projectFormService = TestBed.inject(ProjectFormService);
    projectService = TestBed.inject(ProjectService);
    hydroQuebecRateService = TestBed.inject(HydroQuebecRateService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call HydroQuebecRate query and add missing value', () => {
      const project: IProject = { id: 456 };
      const hydroQuebecRate: IHydroQuebecRate = { id: 21246 };
      project.hydroQuebecRate = hydroQuebecRate;

      const hydroQuebecRateCollection: IHydroQuebecRate[] = [{ id: 30559 }];
      jest.spyOn(hydroQuebecRateService, 'query').mockReturnValue(of(new HttpResponse({ body: hydroQuebecRateCollection })));
      const additionalHydroQuebecRates = [hydroQuebecRate];
      const expectedCollection: IHydroQuebecRate[] = [...additionalHydroQuebecRates, ...hydroQuebecRateCollection];
      jest.spyOn(hydroQuebecRateService, 'addHydroQuebecRateToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ project });
      comp.ngOnInit();

      expect(hydroQuebecRateService.query).toHaveBeenCalled();
      expect(hydroQuebecRateService.addHydroQuebecRateToCollectionIfMissing).toHaveBeenCalledWith(
        hydroQuebecRateCollection,
        ...additionalHydroQuebecRates.map(expect.objectContaining),
      );
      expect(comp.hydroQuebecRatesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const project: IProject = { id: 456 };
      const hydroQuebecRate: IHydroQuebecRate = { id: 30486 };
      project.hydroQuebecRate = hydroQuebecRate;

      activatedRoute.data = of({ project });
      comp.ngOnInit();

      expect(comp.hydroQuebecRatesSharedCollection).toContain(hydroQuebecRate);
      expect(comp.project).toEqual(project);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProject>>();
      const project = { id: 123 };
      jest.spyOn(projectFormService, 'getProject').mockReturnValue(project);
      jest.spyOn(projectService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ project });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: project }));
      saveSubject.complete();

      // THEN
      expect(projectFormService.getProject).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(projectService.update).toHaveBeenCalledWith(expect.objectContaining(project));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProject>>();
      const project = { id: 123 };
      jest.spyOn(projectFormService, 'getProject').mockReturnValue({ id: null });
      jest.spyOn(projectService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ project: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: project }));
      saveSubject.complete();

      // THEN
      expect(projectFormService.getProject).toHaveBeenCalled();
      expect(projectService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IProject>>();
      const project = { id: 123 };
      jest.spyOn(projectService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ project });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(projectService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareHydroQuebecRate', () => {
      it('Should forward to hydroQuebecRateService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(hydroQuebecRateService, 'compareHydroQuebecRate');
        comp.compareHydroQuebecRate(entity, entity2);
        expect(hydroQuebecRateService.compareHydroQuebecRate).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
